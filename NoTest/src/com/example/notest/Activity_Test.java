package com.example.notest;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
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

public class Activity_Test extends Activity {
	int a,a2;
	TextView t1;
	SeekBar sb1, sb2;
	Boolean ntos = true;
	View nsSelect;
	View snSelect;

	SeekBar seekbar,seekbar2;
	EditText text,text2;
	String spin, spin2;
	Spinner spinner, spinner2;
	
	
	Button ns;
	Button sn;
	Button start,end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_activity__test);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner3);

		
		ns = (Button) findViewById(R.id.ns);
		sn = (Button) findViewById(R.id.sn);
		ns.setEnabled(false);
		start= (Button)findViewById(R.id.stbt);
		end = (Button)findViewById(R.id.ebt);
		end.setEnabled(false);
		nsSelect = findViewById(R.id.nsSelect);
		snSelect = findViewById(R.id.snSelect);

		ntos = true;
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
			serviceIntent.putExtra("noise", seekbar.getProgress());
			serviceIntent.putExtra("ntos", ntos);
			if (ntos == true) {
				serviceIntent.putExtra("spin", spin);
			} else {
				serviceIntent.putExtra("spin2", spin2);
			}
			System.out.println("click:" + a);
			startService(serviceIntent);
			break;
		}

		case R.id.ebt: {
			start.setEnabled(true);
			end.setEnabled(false);
			Intent serviceIntent = new Intent("com.example.notest.NoService");
			stopService(serviceIntent);
			break;
		}
		// 3. noise->silent
		case R.id.ns: {
			snSelect.setVisibility(8);
			nsSelect.setVisibility(0);

			ns.setEnabled(false);
			sn.setEnabled(true);
			ntos=true;
			break;
		}
		// 3. silent->noise
		case R.id.sn: {
			snSelect.setVisibility(0);
			nsSelect.setVisibility(8);
			ns.setEnabled(true);
			sn.setEnabled(false);
			ntos=false;
			break;
		}
		case R.id.help:{
			
		}

		}

	}

}
