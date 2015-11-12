package com.example.notest;

import android.app.Activity;
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
	int ac;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		audioReader = new AudioReader();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		text = (TextView) findViewById(R.id.textView1);

	}

	public void doStart(View v) {
		audioReader.startReader(sampleRate, inputBlockSize * sampleDecimate,
				new AudioReader.Listener() {
					@Override
					public final void onReadComplete(int dB) {
						receiveDecibel(dB);
						ac = dB;
						hd.post(new Runnable() {

							@Override
							public void run() {
								text.setText(String.format(ac + 73 + "dB", ac));
								text.setText(ac + 73 + " dB");

							}
						});
						// text.setText(String.format(dB+73 + "dB", dB)); 1번
						// text.setText(dB+73 +" dB"); 2번
					}

					@Override
					public void onReadError(int error) {
					}
				});
	}

	public void doEnd(View v) {
		audioReader.stopReader();
	}


	private void receiveDecibel(final int dB) {
		Log.e("###", dB + 73 + " dB"); // Logcat에 데시벨이 -70~ -73으로 출력이되어 73을더한다.
		// text.setText(String.format(dB+73 + "dB", dB)); 3번
		// text.setText(dB+73 +" dB"); 4번
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		audioReader.stopReader();
	}

}
