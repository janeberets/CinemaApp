package com.example.vkinfo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkinfo.R;

import java.util.List;

public class SeancesAdapter extends RecyclerView.Adapter<SeancesAdapter.SeanceViewHolder> {

    private int seanceItems;
    private List<String> seances;
    private Context parent;

    public SeancesAdapter(List<String> seances, Context parent) {
        this.seances = seances;
        this.seanceItems = seances.size();
        this.parent = parent;
    }

    @NonNull
    @Override
    public SeanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.seances_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new SeanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeanceViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return seanceItems;
    }

    class SeanceViewHolder extends RecyclerView.ViewHolder {

        TextView listItemNumberView;
        TextView viewHolderIndex;

        public SeanceViewHolder(@NonNull View itemView) {
            super(itemView);

            listItemNumberView = itemView.findViewById(R.id.tv_number_item);
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition();
                    Toast toast = Toast.makeText(parent, "Item " + positionIndex
                            + " was clicked", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        void bind(int listIndex) {
            listItemNumberView.setText(seances.get(listIndex));
        }
    }
}
