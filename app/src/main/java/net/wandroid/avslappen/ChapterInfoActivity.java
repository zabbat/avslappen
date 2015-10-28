package net.wandroid.avslappen;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ChapterInfoActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String PATH_CHAP1_MP3 = "/media/audio/chap1.mp3";
    public static final int UPDATE_PROGRESS = 1;
    public static final int MINUTE_MS = 1000 * 60;
    public static final int SECOND_MS = 1000;
    private ImageView mPlayIcon;
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBar;
    private TextView mProgressTimeText;
    private int mAudioLength;
    private long mStartTimeStamp;
    private long mProgressTime;


    private Handler mMediahandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPlayIcon = (ImageView) findViewById(R.id.play_icon);
        mPlayIcon.setOnClickListener((View v) -> {
            String external = Environment.getExternalStorageDirectory().toString();
            File mp3Path = new File(external + PATH_CHAP1_MP3);
            if (mp3Path.exists()) {
                startAudio(mp3Path);
            } else {
                //TODO: Currently you need to push chap1.mp3 to a folder in sdcard/media/audio
                Toast.makeText(ChapterInfoActivity.this, "Could not find: " + mp3Path.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mSeekBar = (SeekBar) findViewById(R.id.media_seekbar);
        mProgressTimeText = (TextView) findViewById(R.id.progress_time_text);

    }

    private void startAudio(File audioFile) {
        Uri uri = Uri.fromFile(audioFile);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(
                (MediaPlayer mp) -> {
                    mp.start();
                    mStartTimeStamp = SystemClock.uptimeMillis();
                    mAudioLength = mp.getDuration();
                    mSeekBar.setOnSeekBarChangeListener(this);
                    Message msg = mMediahandler.obtainMessage(UPDATE_PROGRESS);
                    mMediahandler.sendMessage(msg);
                });
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), uri);
            mMediaPlayer.prepareAsync();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(ChapterInfoActivity.this, "failed to start audio",
                    Toast.LENGTH_SHORT).show();
            mMediaPlayer.release();
        }


    }

    private void updateProgress() {
        mProgressTime += SystemClock.uptimeMillis() - mStartTimeStamp;
        mStartTimeStamp = SystemClock.uptimeMillis();
        if (mProgressTime < mAudioLength) {
            updateProgressTime(mProgressTime);
            updateProgressBar(mProgressTime);
            Message msg = mMediahandler.obtainMessage(UPDATE_PROGRESS);
            mMediahandler.sendMessageDelayed(msg, SECOND_MS);
        }
    }

    private void updateProgressTime(long timeMs) {
        long min = timeMs / MINUTE_MS;
        long sec = (timeMs % (MINUTE_MS)) / SECOND_MS;
        if (sec < 10) {
            mProgressTimeText.setText(String.format("%d:0%d", min, sec));
        } else {
            mProgressTimeText.setText(String.format("%d:%d", min, sec));
        }
    }

    private void updateProgressBar(long timeMs) {
        float relativeTime = 100 * timeMs / (float) mAudioLength;
        mSeekBar.setProgress((int) relativeTime);
    }

    @Override
    protected void onStop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mSeekBar.setOnSeekBarChangeListener(null);
        super.onStop();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser && mMediaPlayer.isPlaying()) {
            // calculate new relative time
            mProgressTime = (progress * mAudioLength) / 100;
            try {
                mMediaPlayer.seekTo((int) mProgressTime);
                seekBar.setProgress(progress);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
