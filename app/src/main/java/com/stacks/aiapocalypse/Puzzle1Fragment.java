package com.stacks.aiapocalypse;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import static com.stacks.aiapocalypse.HolderActivity.set_no;

/**
 * A simple {@link Fragment} subclass.
 */
public class Puzzle1Fragment extends Fragment {

	CountDownTimer timer;
	MediaPlayer mediaPlayer;

	public Puzzle1Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_1, container, false);

		final TextView intro_text = view.findViewById(R.id.intro_text);
		final TextView hint_text = view.findViewById(R.id.hint_text);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p1");
		intro_text.setText(set.optString("intro"));
		hint_text.setText(set.optString("hint"));


		mediaPlayer = MediaPlayer.create(getContext(), R.raw.p1intro);
		mediaPlayer.start();
//		Integer set_no = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("SET", 0);
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				switch (set_no){
					case 1:
						mediaPlayer.release();
						mediaPlayer = MediaPlayer.create(getContext(), R.raw.p1set1);
						mediaPlayer.start();
						break;
					case 3:
						mediaPlayer.release();
						mediaPlayer = MediaPlayer.create(getContext(), R.raw.p1set3);
						mediaPlayer.start();
						break;
					case 6:
						mediaPlayer.release();
						mediaPlayer = MediaPlayer.create(getContext(), R.raw.p1set6);
						mediaPlayer.start();
						break;
				}
			}
		});




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
		if(timer!=null){
			timer.cancel();
		}
		if(mediaPlayer != null){
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onDetach();
	}
}
