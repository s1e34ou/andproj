package com.example.notest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Test extends Activity {
	int a, a2, act,avg;
	TextView t1,t2;
	SeekBar sb1, sb2;
	View nsSelect;
	View snSelect;

	SeekBar seekbar, seekbar2;
	EditText text, text2;
	String spin, spin2;
	Spinner spinner, spinner2;

	Button ns;
	Button sn;
	Button start, end;

	@Override
	protected void onDestroy() {
		unregisterReceiver(mRecevier);
		super.onDestroy();
	}

	private BroadcastReceiver mRecevier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_activity__test);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.notest.act");
		mRecevier = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("com.example.notest.act")) {
					act = intent.getIntExtra("act", 0);
					avg = intent.getIntExtra("avg", 0);
					System.out.println("act:" + act);
						t1.setText("현재 dB : "+act);	
						t2.setText("평균 dB : "+avg);

				}

			}

		};
		registerReceiver(mRecevier, intentFilter);

		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner3);
		ns = (Button) findViewById(R.id.ns);
		sn = (Button) findViewById(R.id.sn);
		start = (Button) findViewById(R.id.stbt);
		end = (Button) findViewById(R.id.ebt);
		nsSelect = findViewById(R.id.nsSelect);
		snSelect = findViewById(R.id.snSelect);
		t1 = (TextView) findViewById(R.id.textView2);
		t2=(TextView) findViewById(R.id.TextView01);
		seekbar = (SeekBar) findViewById(R.id.nsSeekbar);
		text = (EditText) findViewById(R.id.nsSeektext);
		text.setText(String.valueOf(seekbar.getProgress()));

		sb2 = (SeekBar) findViewById(R.id.sb2);
		text2 = (EditText) findViewById(R.id.editText1);
		text2.setText(String.valueOf(sb2.getProgress()));

		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				a = progress;
				text.setText(String.valueOf(progress));
				if(a>a2){
					a2=a+1;
				}
			}
		});

		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable a) {
				try {
					String filtered_str = a.toString();
					if (Integer.parseInt(filtered_str) >= 0
							&& Integer.parseInt(filtered_str) <= 100) {
						seekbar.setProgress(Integer.parseInt(filtered_str));
						Selection.setSelection(a, a.length());
					}
				} catch (Exception e) {
				}
			}
		});

		sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				a2 = progress;
				text2.setText(String.valueOf(progress));
				if(a>a2){
					a=a2-1;
				}
			}
		});

		text2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable a) {
				try {
					String filtered_str = a.toString();
					if (Integer.parseInt(filtered_str) >= 0
							&& Integer.parseInt(filtered_str) <= 100) {
						sb2.setProgress(Integer.parseInt(filtered_str));
						Selection.setSelection(a, a.length());
					}
				} catch (Exception e) {
				}
			}
		});
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.test, android.R.layout.simple_spinner_item);

		spinner.setAdapter(adapter);
		spinner2.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spin = (String) spinner.getSelectedItem();
				System.out.println("sdf:" + spin);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spin2 = (String) spinner2.getSelectedItem();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.stbt: {
			start.setEnabled(false);
			end.setEnabled(true);
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			serviceIntent.putExtra("noise1", seekbar.getProgress());
			serviceIntent.putExtra("noise2", sb2.getProgress());
			serviceIntent.putExtra("spin", spin);
			serviceIntent.putExtra("spin2", spin2);
			System.out.println("click:" + a);
			if(a>a2){
				Toast.makeText(this, "Noise Db가 Silent Db보다 작을수 없습니다.", Toast.LENGTH_SHORT).show();
			}else{
				startService(serviceIntent);
				Toast.makeText(this,"최저"+text.getText()+"최고"+text2.getText()+"시작되었습니다", Toast.LENGTH_SHORT).show();
			}
			break;
		}

		case R.id.ebt: {
			start.setEnabled(true);
			end.setEnabled(false);
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			stopService(serviceIntent);
			Toast.makeText(this, "종료되었습니다.", Toast.LENGTH_SHORT).show();
			break;
		}
		// 3. noise->silent
		case R.id.ns: {
			snSelect.setVisibility(8);
			nsSelect.setVisibility(0);

			ns.setEnabled(false);
			sn.setEnabled(true);
			break;
		}
		// 3. silent->noise
		case R.id.sn: {
			snSelect.setVisibility(0);
			nsSelect.setVisibility(8);
			ns.setEnabled(true);
			sn.setEnabled(false);
			break;
		}
		case R.id.help: {

			Intent intent = new Intent();

			ComponentName componentName = new ComponentName(
					"com.example.notest", "com.example.notest.HelpTest");
			intent.setComponent(componentName);

			startActivity(intent);
		}

		}

	}

}
