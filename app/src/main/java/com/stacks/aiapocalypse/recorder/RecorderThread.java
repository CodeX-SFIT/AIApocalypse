package com.stacks.aiapocalypse.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import com.stacks.aiapocalypse.Puzzle6Activity;
import java.util.concurrent.TimeUnit;

/**
 * Thread for applications recorder of sound
 * <p/>
 * Created by Rudenko Ievgen
 */
public class RecorderThread extends Thread {


    /**
     * Handler to send a message about the progress of the operation
     */
    Handler myHandler;




    /**
     * Constructor of the class RecorderThread
     *
     * @param handler - handler to send a message about the progress of the operation
     */
    public RecorderThread(Handler handler) {
        this.myHandler = handler;
    }

    /**
     * operations performed when starting the thread
     */
    @Override
    public void run() {
        Message msg;

        try {

            ExtAudioRecorder recorder =
                    new ExtAudioRecorder(	true,
                            MediaRecorder.AudioSource.MIC, 44100,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_8BIT);
//            recorder.release();
            recorder.getInstanse();
            recorder.prepare();
            recorder.start();

            // information about recording - progressBar and TextView
            for (int i = 0; i < 11; i++) {
                TimeUnit.SECONDS.sleep(1);
                msg = myHandler.obtainMessage(Puzzle6Activity.STATUS_RECORDING, 10, i);
                myHandler.sendMessage(msg);
            }
            myHandler.sendEmptyMessage(Puzzle6Activity.STATUS_RECORDING_END);
            TimeUnit.MILLISECONDS.sleep(1000);
            myHandler.sendEmptyMessage(Puzzle6Activity.STATUS_NONE);
            recorder.stop();
            recorder.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}