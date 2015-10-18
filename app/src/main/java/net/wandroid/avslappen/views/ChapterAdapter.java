package net.wandroid.avslappen.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.wandroid.avslappen.R;

import java.util.zip.Inflater;

/**
 * Created by zabbat on 2015-10-18.
 */
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    public static final int NR_ITEMS = 2;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list_item,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        viewHolder.mTitle.setText("here");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText("position:"+position);
    }

    @Override
    public int getItemCount() {
        return NR_ITEMS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) mView.findViewById(R.id.list_item_title);
        }
    }

}
