package com.kaelthas.demo.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityBleBinding;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by KaelThas.Wang on 2017/9/18.
 * Email: KaelThas.Wang0919@gmail.com
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEActivity extends BaseActivity<ActivityBleBinding> {

    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 0x0001;
    private static final long SCAN_PERIOD = 10000;
    private Handler mHandler = new Handler();
    private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<>();
    private RecyclerView mRecyclerView;

    private BluetoothGatt mBluetoothGatt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_ble);

        mRecyclerView = mDataBinding.rvBle;

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(new bleAdapter());

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showToast("不支持低功耗蓝牙");
            finish();
        } else {
            init();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ble_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_stamp:
                stamp();
                break;
            case R.id.action_scan:
                mLeDevices.clear();
                scanLeDevice(true);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    class bleAdapter extends RecyclerView.Adapter<bleAdapter.bleHolder> {


        @Override
        public bleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bel, parent, false);
            return new bleHolder(view);
        }

        @Override
        public void onBindViewHolder(bleHolder holder, final int position) {
            holder.name.setText(mLeDevices.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connect(mLeDevices.get(position));

                }
            });
        }

        @Override
        public int getItemCount() {
            return mLeDevices.size();
        }

        class bleHolder extends RecyclerView.ViewHolder {

            private TextView name, state;

            public bleHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                state = (TextView) itemView.findViewById(R.id.tv_state);

            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void init() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

// Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            showToast("error_bluetooth_not_supported");
            finish();
            return;
        }


        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }


    /**
     * 开始扫描附近的蓝牙设备
     *
     * @param enable
     */
    @SuppressLint("NewApi")
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            //mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            // mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mLeDeviceListAdapter.addDevice(device);
                            //mLeDeviceListAdapter.notifyDataSetChanged();

                            if (!mLeDevices.contains(device) && device.getName() != null) {
                                mLeDevices.add(device);
                            }
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                }
            };

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mBluetoothGatt.discoverServices(); //执行到这里其实蓝牙已经连接成功了

                showToast("连接成功");
                Log.i(TAG, "Connected to GATT server.");
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                if(mBluetoothDevice != null){
//                    Log.i(TAG, "重新连接");
//                    //connect();
//                }else{
//                    Log.i(TAG, "Disconnected from GATT server.");
//                }
                showToast("连接失败,请重试");
            }
        }
    };


    /**
     * 连接到外围设备
     *
     * @param device 设备
     */
    private void connect(BluetoothDevice device) {
        showToast("正在连接...");
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
        mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
    }

    /**
     * 执行盖章操作
     */
    private void stamp() {
        if (mBluetoothGatt==null){
            showToast("未连接到盖章机,请连接盖章机");
            return;

        }
        BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb"));
        byte[] value = new byte[20];
        value[0] = (byte) 0x01;
        value[1] = (byte) 0x00;
        value[2] = (byte) 0x01;

        characteristic.setValue(value);
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (requestCode == RESULT_OK) {
                //蓝牙已经开启
                showToast("蓝牙已经开启");
            }
        }
    }

}
