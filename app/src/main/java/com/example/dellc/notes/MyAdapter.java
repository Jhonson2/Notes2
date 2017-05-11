package com.example.dellc.notes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dellc on 2017/5/11.
 */

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Note> mNotes;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_content;
        TextView tv_date;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            cardView= (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Note note=mNotes.get(position);
        holder.tv_title.setText(note.getTitle());
        holder.tv_content.setText(note.getContent());
        holder.tv_date.setText(note.getDate());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public MyAdapter(List<Note> noteList){
        mNotes=noteList;
    }
}
