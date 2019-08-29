package com.stacks.aiapocalypse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button start = findViewById(R.id.start);
		start.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Password");
				final EditText password = new EditText(MainActivity.this);
				password.setHint("For developers only");
				builder.setView(password);
				builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(password.getText().toString().equals("vegas")){
							setSet();
						}else{
							Toast.makeText(MainActivity.this, "INCORRECT", Toast.LENGTH_SHORT).show();
						}
					}
				});
				builder.create().show();
				return false;
			}
		});

		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, IntroActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}


	void setSet(){
		AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
		final Spinner spinner = new Spinner(MainActivity.this);
		spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,
				new String[]{"1", "2", "3", "4", "5", "6"}));
		adb.setView(spinner);
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d("Set changed", "onClick: " + spinner.getSelectedItem());
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
				sp.edit().putInt("SET", Integer.parseInt((String)spinner.getSelectedItem())).apply();
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		adb.create().show();
	}
}
