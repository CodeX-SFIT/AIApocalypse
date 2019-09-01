package com.stacks.aiapocalypse.recorder;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.stacks.aiapocalypse.Puzzle5Fragment;
import com.stacks.aiapocalypse.Puzzle6Fragment;
import com.stacks.aiapocalypse.Puzzle6Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;


/**
 * Thread for applications player of sound
 * Created by Rudenko Ievgen.
 */
public class PlayerThread extends Thread {


    /**
     * Handler to send a message about the progress of the operation
     */
    Handler myHandler;

    /**
     * Create mediaplayer
     */
    MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * filePath of the recorded sound
     */
    private String fileName = Environment.getExternalStorageDirectory()
            + "/newRecord.wav";

    /**
     * filePath of the inverted sound
     */
    private String revFileName = Environment.getExternalStorageDirectory()
            + "/revRecord.wav";

    /**
     * Constructor of class PlayerThread
     *
     * @param myHandler - handler to send a message about the progress of the operation
     */
    public PlayerThread(Handler myHandler) {
        this.myHandler = myHandler;
    }

    /**
     * operations performed when starting the thread
     */
    @Override
    public void run() {
        Message msg;
        try {


            releasePlayer();
            reverseSound();


                mediaPlayer = new MediaPlayer();
//                mediaPlayer.setDataSource(revFileName);
            FileInputStream fis = new FileInputStream(revFileName);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.prepare();
                mediaPlayer.start();

                // information about playing - progressBar and TextView
                for (int i = 0; i < 11; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    msg = myHandler.obtainMessage(Puzzle6Fragment.STATUS_PLAYING, 10, i);
                    myHandler.sendMessage(msg);
                }
                myHandler.sendEmptyMessage(Puzzle6Fragment.STATUS_PLAYING_END);
                TimeUnit.MILLISECONDS.sleep(1000);
                myHandler.sendEmptyMessage(Puzzle6Fragment.STATUS_NONE);
                releasePlayer();


        } catch (Exception e) {
            e.printStackTrace();
            myHandler.sendEmptyMessage(Puzzle6Fragment.TOAST_ERROR);
        }

    }



    /**
     * Reverse recorded sound. Reading file to array bytes, reverse,
     * and writing in new file
     *
     */
    private void reverseSound() throws InterruptedException{
        try {

            //Reading file to main array  bytes 'music'
            InputStream in = new FileInputStream(fileName);
            byte[] music = new byte[in.available()];
            BufferedInputStream bis = new BufferedInputStream(in, 8000);
            DataInputStream dis = new DataInputStream(bis);
            int i = 0;
            while (dis.available() > 0) {
                music[i] = dis.readByte();
                i++;
            }

            //create buffer array with bytes without files header information
            //inverse bytes in array
            int len = music.length;
            byte[] buff = new byte[len];

            for (int y = 45; y < music.length - 1; y++) {
                buff[len - y - 1] = music[y];
            }

            //put inversed bytes in buffers array to main array 'music'
            for (int y = 45; y < music.length - 1; y++) {
                music[y] = buff[y];
            }
            dis.close();

            //write reversed sound to the new file
            OutputStream os = new FileOutputStream(revFileName);
            BufferedOutputStream bos = new BufferedOutputStream(os, 8000);
            DataOutputStream dos = new DataOutputStream(bos);
            dos.write(music, 0, music.length - 1);
            dos.flush();


        } catch (IOException ioe) {
            myHandler.sendEmptyMessage(Puzzle6Fragment.STATUS_NONE_PLAY);
        }
    }


    /**
     * Release player
     */
    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    /**
     * Stop player
     */
    public void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }


}