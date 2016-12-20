package com.app.hrms.utils.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.hrms.MainActivity;
import com.app.hrms.R;
import com.app.hrms.SplashActivity;
import com.app.hrms.helper.AppData;
import com.app.hrms.model.AppCookie;
import com.app.hrms.utils.Urls;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;

public class NotificationService extends Service {
    private static String TAG = "HRMS-NOTICE";

    private NoticeClient noticeClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //----------------------------------------------------------------------------------------------
    //                                       On Create
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate Notification Service " + AppData.getMemberID());
        startClient();
    }

    //----------------------------------------------------------------------------------------------
    //                                 On Start Command
    //----------------------------------------------------------------------------------------------
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        if(noticeClient != null){
            noticeClient.sendMessage(AppData.getMemberID());
        }

        return START_STICKY;
    }
    //----------------------------------------------------------------------------------------------
    //                                      On Destory
    //----------------------------------------------------------------------------------------------
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");

    }
    //----------------------------------------------------------------------------------------------
    //                                      Socket Client Class
    //----------------------------------------------------------------------------------------------
    private class NoticeClient extends Thread{
        private Socket socket;
        BufferedReader br = null;
        OutputStream os = null;
        NoticeClient(){

        }
        public void sendMessage(String msg){
            try{
                os.write((msg+ "\r\n").getBytes("utf-8"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        @Override
        public void run() {
            try {
                socket = new Socket(Urls.PUSH_SERVER, Urls.PUSH_PORT);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = socket.getOutputStream();

                //login to socket server
                String userId = AppData.getMemberID();
                sendMessage(userId);

                String content = null;
                while ((content = br.readLine()) != null) {
                    onMessage(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{ Thread.sleep(5000); }catch (Exception ex){}
            startClient();
        }
    }
    //----------------------------------------------------------------------------------------------
    //                                      Start Socket Client Thread
    //----------------------------------------------------------------------------------------------
    private void startClient(){
        noticeClient = new NoticeClient();
        noticeClient.start();
    }
    //----------------------------------------------------------------------------------------------
    //                                      On Message Handler
    //----------------------------------------------------------------------------------------------
    private void onMessage(String msg){
        System.out.println(msg);
        try{
            sendNotification(new Intent(this, SplashActivity.class), msg);
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    private void sendNotification(Intent intent, String message) throws Exception{
        JSONObject jsonMsg = new JSONObject(message);
        String title = jsonMsg.getString("title");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        largeIcon = Bitmap.createScaledBitmap(largeIcon, (int) (largeIcon.getWidth() * 1.5),
                (int) (largeIcon.getHeight() * 1.5), false);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle("CHESS")
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOngoing(false)
                .setWhen(System.currentTimeMillis())
                .setTicker("CHESS")
                .setContentText(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        System.out.println("PushSound:" + AppData.isPushSoundEnabled());
        System.out.println("PushVibration:" + AppData.isPushVibrationEnabled());

        if(AppData.isPushSoundEnabled()){
            notificationBuilder.setSound(defaultSoundUri);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        if(AppData.isPushVibrationEnabled()){
            try{
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(500);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

}
