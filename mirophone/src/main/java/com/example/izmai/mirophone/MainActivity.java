package com.example.izmai.mirophone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mStartRecordingButton;
    private Button mStopRecordingButton;
    private MediaRecorder mMediaRecorder;
    private File mAudioFile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaRecorder = new MediaRecorder();
        mStartRecordingButton = findViewById(R.id.btnStart);
        mStopRecordingButton = findViewById(R.id.btnStop);
        mStartRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    mStartRecordingButton.setEnabled(false);
                    mStopRecordingButton.setEnabled(true);
                    mStopRecordingButton.requestFocus();
                    startRecording();

                } catch (Exception e) {
                    Log.e(TAG, "Caught io exception " + e.getMessage());
                }
            }
        });
        mStopRecordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mStartRecordingButton.setEnabled(true);
                mStopRecordingButton.setEnabled(false);
                mStartRecordingButton.requestFocus();
                stopRecording();
            }
        });
        mStopRecordingButton.setEnabled(false);
        mStartRecordingButton.setEnabled(true);
    }

    private void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        processAudioFile();
    }

    protected void startRecording() throws IOException {
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        if (mAudioFile==null){
            File sampleDir = Environment.getExternalStorageDirectory();
            try {
                mAudioFile = File.createTempFile("audio",".3gp",sampleDir);
            }catch (IOException e){
                Log.e(TAG,"sdcard error");
                return;
            }
        }
        mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
        mMediaRecorder.prepare();
        mMediaRecorder.start();
    }

    private void processAudioFile() {
        ContentValues values = new ContentValues(3);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" +
                mAudioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current /
                1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA,
                mAudioFile.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                newUri));
    }
}