package net.wandroid.avslappen;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ChapterInfoActivity extends AppCompatActivity {

    public static final String PATH_CHAP1_MP3 = "/media/audio/chap1.mp3";
    private ImageView mPlayIcon;
    private MediaPlayer mMediaPlayer;

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

    }

    private void startAudio(File audioFile) {
        Uri uri = Uri.fromFile(audioFile);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener((MediaPlayer mp) -> mp.start());
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

    @Override
    protected void onStop() {
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onStop();
    }
}
