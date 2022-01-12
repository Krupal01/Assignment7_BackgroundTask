package com.example.asynctaskassignment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookmarkViewHolder> {

    ArrayList<String> Cities = new ArrayList<>();

    public void setCities(ArrayList<String> cities) {
        Cities = cities;
    }

    interface ClickListner{
        void ClickListner(String str);
    }
    private ClickListner listner;

    public BookMarkAdapter(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public BookMarkAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bookmark_row_item,parent,false);
        BookmarkViewHolder viewHolder = new BookmarkViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkAdapter.BookmarkViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCity.setText(Cities.get(position));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(view.getContext());
                db.deleteByName(Cities.get(position));
                Cities.remove(Cities.get(position));
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.ClickListner(Cities.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return Cities.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        Button btnDelete;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
