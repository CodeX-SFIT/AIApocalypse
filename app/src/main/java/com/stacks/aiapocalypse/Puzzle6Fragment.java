package com.stacks.aiapocalypse;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.stacks.aiapocalypse.recorder.PlayerThread;
import com.stacks.aiapocalypse.recorder.RecorderThread;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Puzzle6Fragment extends Fragment {


	public Puzzle6Fragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.puzzle_6, container, false);

		final TextView hint_text = view.findViewById(R.id.hint_text);

		JSONObject set = ((HolderActivity)getActivity()).getSet().optJSONObject("p6");
		hint_text.setText(set.optString("text"));

		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
				!= PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(getActivity(),
					new String[]{Manifest.permission.RECORD_AUDIO,
							Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE},
					100);
		}

		tvStatus = view.findViewById(R.id.tvStatus);
		pb = view.findViewById(R.id.pb);
		btnRecord = view.findViewById(R.id.btnRecord);
		btnPlay = view.findViewById(R.id.btnPlay);
		myHandler.sendEmptyMessage(STATUS_NONE);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					onClickPlay(v);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		btnRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					onClickRecord(v);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		return view;
	}



	final String LOG_TAG = "myLogs";

	/**
	 * Message for handler about main activities statement
	 */
	public static final int STATUS_NONE = 0;

	/**
	 * Message for handler about activities statement, when recording is in progress
	 */
	public static final int STATUS_RECORDING = 1;

	/**
	 * Message for handler about activities statement, when playing is in progress
	 */
	public static final int STATUS_PLAYING = 2;

	/**
	 * Message for handler about activities statement, when recording is finished
	 */
	public static final int STATUS_RECORDING_END = 3;

	/**
	 * Message for handler about activities statement, when playing is finished
	 */
	public static final int STATUS_PLAYING_END = 4;

	/**
	 * Message for handler about activities statement,
	 * when application has not file to playing
	 */
	public static final int STATUS_NONE_PLAY = 5;
	public static final int TOAST_ERROR =6;


	/**
	 * Show operations name and status
	 */
	private TextView tvStatus;

	/**
	 * Show operation progress
	 */
	private ProgressBar pb;

	/**
	 * Button to start record
	 */
	private CardView btnRecord;

	/**
	 * Button to start play
	 */
	private CardView btnPlay;

	/**
	 * RecorderThread object
	 */
	private RecorderThread recorderThread;

	/**
	 *  PlayerThread object
	 */
	private PlayerThread playerThread;


	/**
	 * Show the progress of operations and statement of activities element
	 */
	final Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case STATUS_NONE:
					btnRecord.setEnabled(true);
					btnPlay.setEnabled(true);
					tvStatus.setText("Reverser");
					pb.setVisibility(View.INVISIBLE);
					break;
				case STATUS_RECORDING:
					btnRecord.setEnabled(false);
					btnPlay.setEnabled(false);
					tvStatus.setText("Recording...");
					pb.setMax(msg.arg1);
					pb.setProgress(msg.arg2);
					pb.setVisibility(View.VISIBLE);

					break;
				case STATUS_PLAYING:
					btnRecord.setEnabled(false);
					btnPlay.setEnabled(false);
					tvStatus.setText("Playing...");
					pb.setMax(msg.arg1);
					pb.setProgress(msg.arg2);
					pb.setVisibility(View.VISIBLE);
					break;
				case STATUS_RECORDING_END:
					tvStatus.setText("Recording complete!");
					break;
				case STATUS_PLAYING_END:
					tvStatus.setText("Playing complete!");
					playerThread.playStop();
					break;
				case STATUS_NONE_PLAY:
					Toast.makeText(getActivity(), "File doesn't exist", Toast.LENGTH_SHORT).show();
					break;
				case TOAST_ERROR:
					Toast.makeText(getActivity(), "Error occured while replaying audio. Please use minimize this app and use ReverseTalk App", Toast.LENGTH_LONG).show();

			}
		}

		;
	};


	/**
	 * Starting thread to record sound
	 * @param v - building block for interface components
	 * @throws InterruptedException
	 */
	public void onClickRecord(View v) throws InterruptedException {
		if(recorderThread != null && recorderThread.isAlive()) {
			recorderThread.interrupt();
		} else {
			recorderThread = new RecorderThread(myHandler);
			recorderThread.start();
		}

	}

	/**
	 * Starting thread to play recorded sound
	 * @param v - building block for interface components
	 * @throws InterruptedException
	 */
	public void onClickPlay(View v) throws InterruptedException {
		if(playerThread != null && playerThread.isAlive()) {
			playerThread.interrupt();
		} else {
			playerThread = new PlayerThread(myHandler);
			playerThread.start();
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case 100: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				} else {
					getActivity().finish();
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request.
		}
	}

}
