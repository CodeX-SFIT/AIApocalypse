package com.stacks.aiapocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class Puzzle1Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_1);

		final TextView intro_text = findViewById(R.id.intro_text);
		final TextView hint_text = findViewById(R.id.hint_text);
//
//		CountDownTimer timer = new CountDownTimer(7000, 1000) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				if(millisUntilFinished >= 8000 && millisUntilFinished <= 9000){
//					intro_text.setVisibility(View.VISIBLE);
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				hint_text.setVisibility(View.VISIBLE);
//			}
//		};
//		timer.start();
//
//		// TODO: 28-08-2019 play audio first then show hint text

	}


}
