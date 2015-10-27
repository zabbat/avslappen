package net.wandroid.avslappen.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.wandroid.avslappen.ChapterInfoActivity;
import net.wandroid.avslappen.R;

/**
 * Created by zabbat on 2015-10-18.
 */
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    public static final int NR_ITEMS = 15;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_list_item, parent, false);
        view.setOnClickListener((View v) ->
                    context.startActivity(new Intent(context, ChapterInfoActivity.class))
        );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText("position:" + position);
        holder.mInfo.setText("secondary line");
        holder.mThumbnail.setImageResource(R.drawable.tmp_thumb_flowered_riverbank);
    }


    @Override
    public int getItemCount() {
        return NR_ITEMS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mInfo;
        public ImageView mThumbnail;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) mView.findViewById(R.id.list_item_title);
            mInfo = (TextView) mView.findViewById(R.id.list_item_info_text);
            mThumbnail = (ImageView) mView.findViewById(R.id.list_item_thumbnail);
        }
    }

}
