package com.example.notest;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NoService extends Service {
	Handler hd = new Handler();
	private AudioReader audioReader;
	private int sampleRate = 8000;
	private int inputBlockSize = 256;
	private int sampleDecimate = 1;
	int interval = 60;
	int[] ac; // 데시벨 저장
	int sum, avg,count=1;
	AudioManager am;
	int noise1,noise2;
	boolean ntos;
	String spin, spin2,st;

	public void onCreate() {
		super.onCreate();
		Log.i("superdroid", "onCreate()");
		ac = new int[interval];
		for (int i = 0; i < ac.length; i++) {
			ac[i] = 30;
		}
		audioReader = new AudioReader();

		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		Intent intent = new Intent(this, Activity_Test.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		Notification noti = new Notification.Builder(this)
				.setContentTitle("NoTest").setContentText("실행중")
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
				.build();

		// 2. 포그라운드 서비스 설정 (지각할 수 있는 서비스가 된다)
		// ====================================================================
		startForeground(1234, noti);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.i("superdroid", "onStartCommand()");
		noise1 = intent.getIntExtra("noise1", 0);
		noise2 = intent.getIntExtra("noise2", 0);
		st=intent.getStringExtra("st");
			spin = intent.getStringExtra("spin");
			spin2 = intent.getStringExtra("spin2");
			
		audioReader.startReader(sampleRate, inputBlockSize * sampleDecimate,
				new AudioReader.Listener() {
					@Override
					public final void onReadComplete(int dB) {
						ac[0] = dB + 73; // 데시벨이 -73~70 돼서 나오므로 더해줌
						hd.post(new Runnable() {

							@Override
							public void run() {

								if (ac[0] > 0) {
									Intent act = new Intent("com.example.notest.act");
									act.putExtra("act", ac[0]);
									act.putExtra("avg", avg);
									act.putExtra("sspin", spin);
									act.putExtra("sspin2", spin2);
									act.putExtra("nnoise1", noise1);
									act.putExtra("nnoise2", noise2);
									act.putExtra("stt", st);
									sendBroadcast(act);
								}
								for (int j = ac.length - 1; j > 0; j--) {
									ac[j] = ac[j - 1];
								}
								sum = 0;

								// 데시벨 평균
								for (int i = 0; i < ac.length; i++) {
									if (ac[i] > 0) {
										sum += ac[i];
									}
								}
								avg = sum / interval;

								// 상태변화
									switch (spin) {
									case "Bell":
										if (avg < noise1 ) {
											am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
										}
										break;
									case "NoSound":
										if (avg < noise1 ) {
											am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
										}
										break;
									case "Manner":
										if (avg < noise1 ) {
											am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
										}
										break;
									}

									switch (spin2) {
									case "Bell":
										if (avg >= noise2) {
											am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
										}
										break;
									case "NoSound":
										if (avg >= noise2) {
											am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
										}
										break;
									case "Manner":
										if (avg >= noise2) {
											am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
										}
										break;

								}
							}
						});
					}

					@Override
					public void onReadError(int error) {
					}
				});

		return START_STICKY;
	}

	public void onDestroy() {
		Log.i("superdroid", "onDestroy()");
		audioReader.stopReader();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
