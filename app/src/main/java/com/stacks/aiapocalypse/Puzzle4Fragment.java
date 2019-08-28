package com.stacks.aiapocalypse;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import static com.stacks.aiapocalypse.HolderActivity.set_no;

/**
 * A simple {@link Fragment} subclass.
 */
public class Puzzle4Fragment extends Fragment {

	CountDownTimer timer;

	MediaPlayer mediaPlayer;


	public Puzzle4Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_4, container, false);
		final TextView intro_text = view.findViewById(R.id.intro_text);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p4");
		intro_text.setText(set.optString("text"));


		switch (set_no){
			case 2:
				mediaPlayer = MediaPlayer.create(getContext(), R.raw.p4set2);
				mediaPlayer.start();
				break;
			case 3:
				mediaPlayer = MediaPlayer.create(getContext(), R.raw.p4set3);
				mediaPlayer.start();
				break;
			case 4:
				mediaPlayer = MediaPlayer.create(getContext(), R.raw.p4set4);
				mediaPlayer.start();
				break;
		}
//		hint_text.setText(set.optString("hint"));

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
