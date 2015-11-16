package com.example.notest;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_Test extends Activity {
	
	View nsSelect;
	View snSelect;
	
	SeekBar seekbar;
	EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		// 1. 카운트 시작
		case R.id.stbt: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			startService(serviceIntent);
			break;
			}

		// 2. 카운트 종료
		case R.id.ebt: {
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			stopService(serviceIntent);
			break;
			}
		// 3. noise->silent
		case R.id.ns:{
			snSelect.setVisibility(8);
			nsSelect.setVisibility(0);
			break;
			}
		// 3. silent->noise
		case R.id.sn:{
			snSelect.setVisibility(0);
			nsSelect.setVisibility(8);
			break;
			}

		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
