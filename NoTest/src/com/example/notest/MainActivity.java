package com.example.notest;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	Handler hd = new Handler();
	private AudioReader audioReader;
	private int sampleRate = 8000;
	private int inputBlockSize = 256;
	private int sampleDecimate = 1;
	TextView text;
	int interval = 60;
	int[] ac; // 데시벨 저장
	int sum, avg;
	AudioManager am;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        
        
		audioReader = new AudioReader();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		text = (TextView) findViewById(R.id.textView1);
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

	}

	public void doStart(View v) {
		ac = new int[interval];
		audioReader.startReader(sampleRate, inputBlockSize * sampleDecimate,
				new AudioReader.Listener() {
					@Override
					public final void onReadComplete(int dB) {
						receiveDecibel(dB);
						ac[0] = dB + 73; // 데시벨이 -73~70 돼서 나오므로 더해줌
						hd.post(new Runnable() {

							@Override
							public void run() {
								if(ac[0]>0){
								text.setText(ac[0] + " dB"); // 앱에 데시벨 표시
								}
								// 매초마다 데시벨 저장
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

								for (int i = 0; i < 6; i++) {
									System.out.println(ac[i * 10] + " avg : "
											+ avg);
								}

								// 상태변화
								switch (am.getRingerMode()) {
								case AudioManager.RINGER_MODE_SILENT:
									Log.i("MyApp", "Silent mode");
									break;
								case AudioManager.RINGER_MODE_VIBRATE:
									Log.i("MyApp", "Vibrate mode");
									break;
								case AudioManager.RINGER_MODE_NORMAL:
									Log.i("MyApp", "Normal mode");
									if (avg < 30) {
										am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
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
	}

	public void amove() {

	}

	public void doEnd(View v) {
		audioReader.stopReader();
	}

	private void receiveDecibel(final int dB) {
		Log.e("###", dB + " dB"); // Logcat에 데시벨이 -70~ -73으로 출력이되어 73을더한다.
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		audioReader.stopReader();
		super.onDestroy();
	}


}
