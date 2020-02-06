package com.loftydevelopment.oneminutepaper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.loftydevelopment.oneminutepaper.model.Paper;

import java.util.List;

public class PaperAdapter extends ArrayAdapter<Paper> {

    private List<Paper> paperList;

    public PaperAdapter(@NonNull Context context, int resource, @NonNull List<Paper> list) {
        super(context, resource, list);
        this.paperList = list;
    }

    private static class ViewHolder {
        TextView tvTitle;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paper currentPaper = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            convertView.setTag(viewHolder);
            result = convertView;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        viewHolder.tvTitle.setText(currentPaper.getSubject());

        return result;
    }

}