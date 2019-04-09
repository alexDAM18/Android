package com.example_alex.rhymin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mInflater;
    private ArrayList<ListItem> list;
    private Context context;
    private View.OnClickListener listener;

    public ListAdapter(Context context, ArrayList<ListItem> list) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;

    }

    public ListItem getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/{
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.textGetFiles);
        }

        public void bindTitular(ListItem item) {
            text.setText(item.getName());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_files, viewGroup, false);
        itemView.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    //Cargar path padre y luego item
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        final ListItem item = list.get(pos);
        viewHolder.bindTitular(item);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WriteFileActivity.class);
                intent.putExtra("filePath", item.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}