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
	int[] ac; // ���ú� ����
	int sum, avg;
	AudioManager am;
	int noise;
	boolean ntos;
	String spin, spin2;

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
				.setContentTitle("NoTest").setContentText("������")
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
				.build();

		// 2. ���׶��� ���� ���� (������ �� �ִ� ���񽺰� �ȴ�)
		// ====================================================================
		startForeground(1234, noti);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.i("superdroid", "onStartCommand()");
		noise = intent.getIntExtra("noise", 0);

		ntos = intent.getBooleanExtra("ntos", true);
		if (ntos == true) {
			spin = intent.getStringExtra("spin");
		} else {
			spin2 = intent.getStringExtra("spin2");
		}
		System.out.println("sc:" + noise);
		System.out.println("spin:" + spin);
		System.out.println("spin2:" + spin2);
		audioReader.startReader(sampleRate, inputBlockSize * sampleDecimate,
				new AudioReader.Listener() {
					@Override
					public final void onReadComplete(int dB) {
						ac[0] = dB + 73; // ���ú��� -73~70 �ż� �����Ƿ� ������
						hd.post(new Runnable() {

							@Override
							public void run() {

								if (ac[0] > 0) {
									Intent act = new Intent("com.example.notest.act");
									act.putExtra("act", ac[0]);
									act.putExtra("avg", avg);
									sendBroadcast(act);
								}
								// text.setText(ac[0] + " dB"); // �ۿ� ���ú� ǥ��
								// ���ʸ��� ���ú� ����
								for (int j = ac.length - 1; j > 0; j--) {
									ac[j] = ac[j - 1];
								}
								sum = 0;

								// ���ú� ���
								for (int i = 0; i < ac.length; i++) {
									if (ac[i] > 0) {
										sum += ac[i];
									}
								}
								avg = sum / interval;
								for (int i = 0; i < 6; i++) {
									System.out.println(ac[i * 10] + " avg : "
											+ avg);
								}

								// ���º�ȭ
								if (ntos == true) {
									switch (spin) {
									case "Bell":
										if (avg < noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
										}
										break;
									case "NoSound":
										if (avg < noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
										}
										break;
									case "Manner":
										if (avg < noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
										}
										break;


									}
								} else {
									switch (spin2) {
									case "Bell":
										if (avg > noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
										}
										break;
									case "NoSound":
										if (avg > noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
										}
										break;
									case "Manner":
										if (avg > noise) {
											am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
										}
										break;
									}

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
