package com.ravisharma.mydiary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisharma.mydiary.Model.Entry;
import com.ravisharma.mydiary.R;
import com.ravisharma.mydiary.ViewEntryActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyVH> {

    private Context c;
    private List<Entry> entryList;

    public RecyclerAdapter(Context c, List<Entry> entryList) {
        this.c = c;
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.adapter_layout, parent, false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position) {
        Entry entry = entryList.get(position);
        holder.setData(entry, position);
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    class MyVH extends RecyclerView.ViewHolder {

        TextView textDate, textEntry;
        LinearLayout layout;
        int position;

        public MyVH(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textEntry = itemView.findViewById(R.id.textEntry);
            layout = itemView.findViewById(R.id.layout);
        }

        public void setData(final Entry entry, int position) {
            this.position = position;
            textDate.setText(entry.getDate());
            textEntry.setText(entry.getEntry());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c, ViewEntryActivity.class);
                    i.putExtra("time", entry.getDate());
                    i.putExtra("entry", entry.getEntry());
                    c.startActivity(i);
                }
            });
        }
    }
}
