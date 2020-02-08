package com.loftydevelopment.oneminutepaper.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loftydevelopment.oneminutepaper.R;
import com.loftydevelopment.oneminutepaper.model.Paper;

import java.util.ArrayList;
import java.util.List;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder>  {

    private static final String TAG = "PaperAdapter";
    private List<Paper> paperList = new ArrayList<>();
    private OnPaperListener onPaperListener;

    public PaperAdapter(OnPaperListener onPaperListener) {
        this.onPaperListener = onPaperListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reyclerview_paper, parent, false);
        return new ViewHolder(view, onPaperListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            ((ViewHolder)holder).title.setText(paperList.get(position).getSubject());
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return paperList.size();
    }

    public Paper getPaper(int position){
        if(paperList.size() > 0){
            return paperList.get(position);
        }
        return null;
    }

    public void removePaper(Paper note){
        paperList.remove(note);
        notifyDataSetChanged();
    }

    public void setPapers(List<Paper> notes){
        this.paperList = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        OnPaperListener mOnPaperListener;

        public ViewHolder(View itemView, OnPaperListener onPaperListener) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_paper_title);
            mOnPaperListener = onPaperListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnPaperListener.onPaperClick(getPaper(getAdapterPosition()));
        }

    }

    public interface OnPaperListener{
        void onPaperClick(Paper paper);
    }
}