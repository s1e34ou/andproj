package com.example.notest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static TextView text;
	Intent i=new Intent();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		i.setAction("com.example.notest.re");
		text =(TextView) findViewById(R.id.textView1);

	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		// 1. 카운트 시작
		case R.id.start: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			startService(serviceIntent);
			
			
			break;
		}

		// 2. 카운트 종료
		case R.id.end: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			stopService(serviceIntent);
			break;
		}

		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
