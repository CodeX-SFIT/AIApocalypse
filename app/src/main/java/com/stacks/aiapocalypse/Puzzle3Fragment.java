package com.stacks.aiapocalypse;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Puzzle3Fragment extends Fragment {

	CountDownTimer timer;

	MediaPlayer mediaPlayer;


	public Puzzle3Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_3, container, false);
		final TextView intro_text = view.findViewById(R.id.intro_text);
		final ImageView imageView = view.findViewById(R.id.symbol);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p3");
		intro_text.setText(set.optString("intro"));
		Glide.with(getContext())
				.load(Uri.parse("file:///android_asset/" + set.optString("symbolA")))
				.into(imageView);

		if(((HolderActivity)getActivity()).isShouldPlay()) {

			mediaPlayer = MediaPlayer.create(getContext(), R.raw.p3intro);
			mediaPlayer.start();
			((HolderActivity)getActivity()).played();
		}
//
//		hint_text.setVisibility(View.GONE);
//
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
