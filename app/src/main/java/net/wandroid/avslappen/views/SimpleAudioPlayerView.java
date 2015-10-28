package net.wandroid.avslappen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import net.wandroid.avslappen.R;

/**
 * Created by zabbat on 10/28/15.
 */
public class SimpleAudioPlayerView extends LinearLayout {
    private View mView;

    public SimpleAudioPlayerView(Context context) {
        this(context, null);
    }

    public SimpleAudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        mView = View.inflate(getContext(), R.layout.play_bar,this);
    }
}
