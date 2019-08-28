package com.stacks.aiapocalypse;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Puzzle2Fragment extends Fragment {

//	CountDownTimer timer;

	MediaPlayer mediaPlayer;

	public Puzzle2Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_2, container, false);

		final TextView intro_text = view.findViewById(R.id.intro_text);
		final TextView hint_text = view.findViewById(R.id.hint_text);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p2");
		intro_text.setText(set.optString("intro"));
		hint_text.setText(set.optString("hint"));
		hint_text.setTextColor(Color.parseColor(set.optString("color")));

		mediaPlayer = MediaPlayer.create(getContext(), R.raw.p2intro);
		mediaPlayer.start();
//		hint_text.setVisibility(View.GONE);

//		timer = new CountDownTimer(2000, 2000) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//
//			}
//
//			@Override
//			public void onFinish() {
//				hint_text.setVisibility(View.VISIBLE);
//			}
//		};
//		timer.start();

		return view;
	}

	@Override
	public void onDetach() {
		if(mediaPlayer != null){
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onDetach();
	}
}
