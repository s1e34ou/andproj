package com.example.notest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Activity_Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__test);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner4);
		
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_item);
		
		spinner.setAdapter(adapter);
		spinner1.setAdapter(adapter);
		spinner2.setAdapter(adapter);
		spinner3.setAdapter(adapter);
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
