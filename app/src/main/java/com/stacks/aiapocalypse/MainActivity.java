package com.stacks.aiapocalypse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			}
		});
		adb.create().show();
	}


}
