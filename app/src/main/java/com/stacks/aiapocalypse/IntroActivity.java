package com.stacks.aiapocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);

		TextView intro = findViewById(R.id.intro_text);
		Button begin = findViewById(R.id.begin);
//
//		intro.setCharacterDelay(150);
//		intro.animateText(getString(R.string.intro_text));

//		MediaPlayer player = MediaPlayer.create(this, R.raw.introduction);
//		player.start();

		begin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroActivity.this, HolderActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}
}
