package com.app.hrms.helper;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.MemberModel;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.app.hrms.R;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;

public class ImageHelper {

    public static DisplayImageOptions avartar_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.ic_empty)
            .showImageOnFail(R.mipmap.ic_error)
            .resetViewBeforeLoading(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            //.displayer(new FadeInBitmapDisplayer(300))
            .build();

    private static ImageHelper instance = null;

    public static DisplayImageOptions avatar_options =
            new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(true)
                    .showImageOnLoading(R.mipmap.user_placeholder)
                    .showImageOnFail(R.mipmap.user_placeholder)
                    .showImageForEmptyUri(R.mipmap.user_placeholder)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .build();

    public static ImageHelper getInstance() {
        if (instance == null) {
            instance = new ImageHelper();
        }
        return instance;
    }

    public void uploadPhoto(Context context, String file, final PhotoUploadCallback callback) {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("PERNR", AppCookie.getInstance().getCurrentUser().getPernr());
            params.put("image", new File(file));

            client.post(context, Urls.BASE_URL + Urls.UPLOAD_PHOTO, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int status, Header[] headers, byte[] bytes) {
                    try {
                        JSONObject resultJson = new JSONObject(new String(bytes));
                        int retcode = resultJson.getInt("success");
                        if (retcode == 1) {
                            callback.onSuccess();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed(-1);
                    }
                }

                @Override
                public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                    callback.onFailed(-1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PhotoUploadCallback {
        public void onSuccess();
        public void onFailed(int error);
    }
}
