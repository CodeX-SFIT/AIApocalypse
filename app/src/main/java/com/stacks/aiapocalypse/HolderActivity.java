package com.stacks.aiapocalypse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HolderActivity extends AppCompatActivity {


	String[] ps = new String[]{"p1", "p2", "p3", "p4", "p5", "p6"};

	private long current = 0;

	List<Class> fragments = new ArrayList<>();

	private List<String> present_ps;

	ImageView left, right;
	private JSONObject set;

	public static int set_no = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holder);
		hideSystemUI();
		set_no = PreferenceManager.getDefaultSharedPreferences(this).getInt("SET", 1);

		left = findViewById(R.id.left);
		right = findViewById(R.id.right);

		JSONObject puzzles = null;
		try {
			puzzles = new JSONObject(AssetJSONFile("puzzles.json"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//		Integer set_no = sp.getInt("SET", 1);
//		Integer set_no = 3;
		set = puzzles.optJSONArray("set").optJSONObject(set_no-1);


		present_ps = new ArrayList<>();
		for(String temp: ps){
			if(set.has(temp)){
				present_ps.add(temp);
				Log.d("Puzzles", "onCreate: " + temp);
			}
		}

		for(String temp: present_ps){
			switch (temp){
				case "p1": fragments.add(Puzzle1Fragment.class);
				case "p2": fragments.add(Puzzle2Fragment.class);
				case "p3": fragments.add(Puzzle3Fragment.class);
				case "p4": fragments.add(Puzzle4Fragment.class);
				case "p5": fragments.add(Puzzle5Fragment.class);
				case "p6": fragments.add(Puzzle6Fragment.class);
			}
		}

		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				current--;
				loadFragment();
			}
		});
		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				current++;
				loadFragment();
			}
		});

		right.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(current != 0) return false;
				AlertDialog.Builder builder = new AlertDialog.Builder(HolderActivity.this);
				builder.setTitle("Password");
				final EditText password = new EditText(HolderActivity.this);
				password.setHint("For developers only");
				builder.setView(password);
				builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(password.getText().toString().equals("vegas")){
							setSet();
						}else{
							Toast.makeText(HolderActivity.this, "INCORRECT", Toast.LENGTH_SHORT).show();
						}
					}
				});
				builder.create().show();
				return false;
			}
		});
		current=0;
		loadFragment();
	}

	void loadFragment() {
//		getSupportActionBar().setTitle(fraglist[(int) current]);

		Fragment fragment = getSelectedFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
		fragmentTransaction.replace(R.id.placeholder, fragment, fragments.get((int)current).getName());
		fragmentTransaction.commitAllowingStateLoss();

		if(current == 0){
			left.setVisibility(View.GONE);
			right.setVisibility(View.VISIBLE);
		}else if(current == 1){
			left.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
		}else{
			left.setVisibility(View.VISIBLE);
			right.setVisibility(View.GONE);
		}
		hideSystemUI();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		hideSystemUI();
	}

	Fragment getSelectedFragment() {
		switch (present_ps.get((int) current)){
			case "p1": return new Puzzle1Fragment();
			case "p2": return new Puzzle2Fragment();
			case "p3": return new Puzzle3Fragment();
			case "p4": return new Puzzle4Fragment();
			case "p5": return new Puzzle5Fragment();
			case "p6": return new Puzzle6Fragment();
		}
		return null;
//		return fragments.get((int) current)
//		switch ((int) current) {
//			case 0:
//				return fragments.get(());
//			case 2:
//				return TimetableFragment.getInstance();
//			default:
//				return NoticesFragment.getInstance();
//		}
	}

	public String AssetJSONFile (String filename){
		AssetManager manager = getAssets();
		byte[] formArray = new byte[0];
		try {
			InputStream file = manager.open(filename);
			formArray = new byte[file.available()];
			file.read(formArray);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(formArray);
	}

	public JSONObject getSet() {
		return set;
	}

	void setSet(){
		AlertDialog.Builder adb = new AlertDialog.Builder(HolderActivity.this);
		final Spinner spinner = new Spinner(HolderActivity.this);
		spinner.setAdapter(new ArrayAdapter<String>(HolderActivity.this, android.R.layout.simple_spinner_dropdown_item,
				new String[]{"1", "2", "3", "4", "5", "6"}));
		adb.setView(spinner);
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d("Set changed", "onClick: " + spinner.getSelectedItem());
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HolderActivity.this);
				sp.edit().putInt("SET", Integer.parseInt((String)spinner.getSelectedItem())).apply();
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		adb.create().show();
	}

	private void hideSystemUI() {
		// Enables regular immersive mode.
		// For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
		// Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_IMMERSIVE
						// Set the content to appear under the system bars so that the
						// content doesn't resize when the system bars hide and show.
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						// Hide the nav bar and status bar
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
		);
	}

}
