package com.kaelthas.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityUmengSocialBinding;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;


public class UmengSocialActivity extends BaseActivity implements View.OnClickListener {

    private ActivityUmengSocialBinding mBinding;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_umeng_social);
        this.savedInstanceState = savedInstanceState;

        mBinding.btnLoginQq.setOnClickListener(this);
        mBinding.btnShareQq.setOnClickListener(this);
        mBinding.btnShareQqZone.setOnClickListener(this);
        mBinding.btnShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_qq:


//                UMShareAPI.get(mContext).doOauthVerify(UmengSocialActivity.this,SHARE_MEDIA.QQ,umAuthListener);

                /**
                 *  友盟两种登录方式
                 *
                 *  1.doOauthVerify
                 *  开发者调用授权接口，可以获取accesstoken相关的信息，但是没有用户资料（姓名，性别，头像等），
                 *  需要用户根据token自己去请求用户资料，对于保密性要求较高的用户可以使用
                 *
                 *  2.getPlatformInfo
                 *  获取用户资料，开发者调用获取用户资料接口，可以获取该三方平台返回的所有资料（包括姓名，性别，头像等）
                 */

                UMShareAPI.get(mContext).getPlatformInfo(UmengSocialActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;

            /**
             * qq不支持单纯分享文字
             *
             * 各个具体分享内容查看官方文档
             * http://dev.umeng.com/social/android/share-detail
             */
            case R.id.btn_share_qq:


                new ShareAction(UmengSocialActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("hello")
                        .withMedia(new UMImage(mContext, "http://wangyou.pcgames.com.cn/zhuanti/lol/hero/Karthus/LOL_Karthus.jpg"))
                        .setCallback(umShareListener)
                        .share();
                break;

            //直接分享到qq空间
            case R.id.btn_share_qqZone:
                new ShareAction(UmengSocialActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withText("hello")
                        .withMedia(new UMImage(mContext, "http://wangyou.pcgames.com.cn/zhuanti/lol/hero/Karthus/LOL_Karthus.jpg"))
                        .setCallback(umShareListener)
                        .share();
                break;

            case R.id.btn_share:
                UMImage image = new UMImage(mContext, "http://wangyou.pcgames.com.cn/zhuanti/lol/hero/Karthus/LOL_Karthus.jpg");
                UMWeb web = new UMWeb("http://www.baidu.com");
                web.setTitle("This is music title");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("my description");//描述

                new ShareAction(UmengSocialActivity.this)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)   //从底部弹出的popupwindow中的分享列表
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .open();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);

    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(UmengSocialActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(UmengSocialActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(UmengSocialActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
