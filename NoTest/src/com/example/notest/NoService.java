package com.example.notest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NoService extends Service{
    public void onCreate()
    {
        super.onCreate();
        Log.i("superdroid", "onCreate()");
        
        Intent intent = new Intent(this, MainActivity.class );
        PendingIntent pIntent = PendingIntent.getActivity( this, 0, intent, 
                                            PendingIntent.FLAG_UPDATE_CURRENT);
        Notification noti = new Notification.Builder(this)
                                      .setContentTitle("NoTest")
                                      .setContentText("Running NoTest")
                                      .setSmallIcon( R.drawable.ic_launcher )
                                      .setContentIntent( pIntent )
                                      .build();
        
        // 2. 포그라운드 서비스 설정 (지각할 수 있는 서비스가 된다)
        // ====================================================================
        startForeground( 1234, noti );
    }

    public int onStartCommand( Intent intent, int flags, int startId )
    {
        super.onStartCommand( intent, flags, startId );
        Log.i("superdroid", "onStartCommand()");
        return START_STICKY; 
    }

    public void onDestroy()
    {
        Log.i("superdroid", "onDestroy()");
        super.onDestroy();
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
