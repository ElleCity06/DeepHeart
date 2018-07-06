package com.deepheart.ellecity06.deepheart.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.bean.MyUser;
import com.deepheart.ellecity06.deepheart.ui.activity.user.BindPhoneActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.SocializeUtils;

import org.json.JSONObject;

import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author ellecity06
 * @time 2018/6/4 17:57
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.utils
 * @des TODO
 */

public class UMUtils {
    public static void loginForThirdParty(SHARE_MEDIA share_media, final Activity activity, final Dialog dialog, final String snstype) {
        UMShareAPI.get(activity.getApplication()).getPlatformInfo(activity, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                SocializeUtils.safeShowDialog(dialog);
            }

            //授权完成的回调
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                Log.d("dawwwwwwwwwasd", map.toString());

                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snstype, map.get("accessToken"), map.get("expires_in"), map.get("openid"));


                BmobUser.loginWithAuthData(DeepHeartApplication.getAppContext(), authInfo, new OtherLoginListener() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), "成功啦啦11121121");
                        MyUser myUser = new MyUser();
                        final BmobUser currentUser = BmobUser.getCurrentUser(DeepHeartApplication.getAppContext());
                        myUser.setUsername(map.get("screen_name"));
                        myUser.update(DeepHeartApplication.getAppContext(), currentUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                //判断用户是否绑定手机，没有就去绑定，
                                if (currentUser.getMobilePhoneNumber() != null) { //已经绑定
                                    activity.finish();
                                    SocializeUtils.safeCloseDialog(dialog);
                                } else { // 没有绑定
                                    Intent intent = new Intent(activity, BindPhoneActivity.class);
                                    intent.putExtra(Constants.BIND_PHONE_TYPE, 1);
                                    activity.startActivity(intent);
                                    SocializeUtils.safeCloseDialog(dialog);
                                }

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                SocializeUtils.safeCloseDialog(dialog);
                                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "网络好像是出了一点小问题，请稍候重试");
                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), "登录失败了，请重试");
                    }
                });


            }

            // 授权错误
            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                SocializeUtils.safeCloseDialog(dialog);
            }

            // 授权取消
            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                SocializeUtils.safeCloseDialog(dialog);
            }
        });

    }


    /**
     * @param activity
     * @param text
     * @param share_media 分享纯文本
     */
    public static void shareText(Activity activity, String text, SHARE_MEDIA share_media) {
        new ShareAction(activity).withText(text)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * @param activity
     * @param bitmap
     * @param share_media 分享本地图片
     */
    public static void shareImageLocal(Activity activity, Bitmap bitmap, SHARE_MEDIA share_media) {
        UMImage imagelocal = new UMImage(activity, bitmap);
        imagelocal.setThumb(new UMImage(activity, bitmap));
        new ShareAction(activity).withMedia(imagelocal)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * @param activity
     * @param imgUrl
     * @param share_media 分享网络图片
     */
    public static void shareImageNet(Activity activity, String imgUrl, SHARE_MEDIA share_media) {
        UMImage imageurl = new UMImage(activity, imgUrl);
        imageurl.setThumb(new UMImage(activity, imgUrl));
        new ShareAction(activity).withMedia(imageurl)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * @param activity
     * @param shareUrl
     * @param share_media
     * @param title
     * @param des
     * @param imgUrl      分享链接 ，可以传入标题，图片，描述
     */
    public static void shareUrl(Activity activity, String shareUrl, SHARE_MEDIA share_media, String title, String des, String imgUrl) {
        UMWeb web = new UMWeb(shareUrl);
        UMImage umImage;
        if (StringUtils.isEmpty(imgUrl)) {
            umImage = new UMImage(activity, R.drawable.share_qq);
        } else {
            umImage = new UMImage(activity, imgUrl);
        }
        web.setTitle(title);
        web.setThumb(umImage);
        web.setDescription(des);
        new ShareAction(activity).withMedia(web)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * @param activity
     * @param musicurl
     * @param share_media
     * @param title
     * @param des
     * @param imgUrl      分享音乐
     */
    public static void shareMusic(Activity activity, String musicurl, SHARE_MEDIA share_media, String title, String des, String imgUrl) {
        UMusic music = new UMusic(musicurl);
        UMImage umImage;
        if (StringUtils.isEmpty(imgUrl)) {
            umImage = new UMImage(activity, R.drawable.share_qq);
        } else {
            umImage = new UMImage(activity, imgUrl);
        }
        music.setTitle(title);
        music.setThumb(umImage);
        music.setDescription(des);
        music.setmTargetUrl(musicurl);
        new ShareAction(activity).withMedia(music)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * @param activity
     * @param videourl
     * @param share_media
     * @param title
     * @param des
     * @param imgUrl      分享视频
     */
    public static void shareVideo(Activity activity, String videourl, SHARE_MEDIA share_media, String title, String des, String imgUrl) {
        UMVideo video = new UMVideo(videourl);
        UMImage umImage;
        if (StringUtils.isEmpty(imgUrl)) {
            umImage = new UMImage(activity, R.drawable.share_qq);
        } else {
            umImage = new UMImage(activity, imgUrl);
        }
        video.setThumb(umImage);
        video.setTitle(title);
        video.setDescription(des);
        new ShareAction(activity).withMedia(video)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    /**
     * 分享回调
     */
    private static UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showToast(DeepHeartApplication.getAppContext(), "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //错误码：2008 错误信息：没有安装应用 点击查看错误：https://at.umeng.com/ve4Pbm?cid=476
            String str = "分享失败";
            try {
                str = t.getMessage();
                int index1 = str.indexOf("错误信息：");
                int index2 = str.indexOf("点击查看错误：");
                str = str.substring(index1, index2);
                str = str.replace("错误信息：", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ToastUtils.showToast(DeepHeartApplication.getAppContext(), str);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtils.showToast(DeepHeartApplication.getAppContext(), "分享取消了");
        }
    };

    //    private static void initMedia(Activity activity, String extra_share_url, String extra_share_title, String extra_share_img_url, String extra_share_des) {
    //
    //    }
}
