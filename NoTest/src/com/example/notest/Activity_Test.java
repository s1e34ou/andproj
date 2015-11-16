package com.example.notest;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		sb1=(SeekBar)findViewById(R.id.sb1);
		
		sb2=(SeekBar)findViewById(R.id.sb2);
		setContentView(R.layout.activity_activity__test);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		t1=(TextView)findViewById(R.id.textView2);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner4);
		
		nsSelect = findViewById(R.id.nsSelect);
		snSelect = findViewById(R.id.snSelect);
		
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
