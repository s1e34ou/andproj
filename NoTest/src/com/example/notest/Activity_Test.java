package com.example.notest;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_Test extends Activity {
	int a;
	TextView t1;
	SeekBar sb1,sb2 ;
	Boolean ntos=true;
	View nsSelect;
	View snSelect;
	
	SeekBar seekbar;
	EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.activity_activity__test);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner4);
		
		nsSelect = findViewById(R.id.nsSelect);
		snSelect = findViewById(R.id.snSelect);
		
		seekbar = (SeekBar) findViewById(R.id.nsSeekbar);
		text = (EditText) findViewById(R.id.nsSeektext);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				a=progress;
				text.setText(String.valueOf(progress));
			}
		});
		
		ArrayAdapter adapter =
				ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_item);
		
		spinner.setAdapter(adapter);
		spinner1.setAdapter(adapter);
		spinner2.setAdapter(adapter);
		spinner3.setAdapter(adapter);

		
		
	}
	

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.stbt: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			serviceIntent.putExtra("noise", seekbar.getProgress());
			serviceIntent.putExtra("ntos", ntos);
			System.out.println("click:"+a);
			startService(serviceIntent);
			break;
			}

		case R.id.ebt: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			stopService(serviceIntent);
			break;
			}
		// 3. noise->silent
		case R.id.ns:{
			snSelect.setVisibility(8);
			nsSelect.setVisibility(0);
			ntos=true;
			break;
			}
		// 3. silent->noise
		case R.id.sn:{
			snSelect.setVisibility(0);
			nsSelect.setVisibility(8);
			ntos=false;
			break;
			}

		}
		
	}

}
