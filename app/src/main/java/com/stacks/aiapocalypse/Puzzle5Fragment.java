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
public class Puzzle5Fragment extends Fragment {

	CountDownTimer timer;

	MediaPlayer mediaPlayer;

	public Puzzle5Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_5, container, false);

		final TextView intro_text = view.findViewById(R.id.intro_text);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p5");
		intro_text.setText(set.optString("para"));

		if(((HolderActivity)getActivity()).isShouldPlay()) {

			switch (set_no) {
				case 4:
					mediaPlayer = MediaPlayer.create(getContext(), R.raw.p5set4);
					mediaPlayer.start();
					break;
				case 5:
					mediaPlayer = MediaPlayer.create(getContext(), R.raw.p5set5);
					mediaPlayer.start();
					break;
				case 6:
					mediaPlayer = MediaPlayer.create(getContext(), R.raw.p5set6);
					mediaPlayer.start();
					break;
			}
			((HolderActivity)getActivity()).played();
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
