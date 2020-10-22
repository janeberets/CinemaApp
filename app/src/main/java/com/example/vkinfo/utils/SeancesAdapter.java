package com.example.vkinfo.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkinfo.R;
import com.example.vkinfo.activities.UpdateSeanceActivity;
import com.example.vkinfo.models.Seance;
import com.example.vkinfo.models.User;
import com.google.gson.Gson;

import java.util.List;

public class SeancesAdapter extends RecyclerView.Adapter<SeancesAdapter.SeanceViewHolder> {

    private int seanceItems;
    private List<Seance> seances;
    private Context parent;
    private User user;

    public SeancesAdapter(List<Seance> seances, User user, Context parent) {
        this.seances = seances;
        this.seanceItems = seances.size();
        this.parent = parent;
        this.user = user;
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

                    Context context = parent;
                    Intent seanceActivityIntent = new Intent(context, UpdateSeanceActivity.class);
                    seanceActivityIntent.putExtra("seance", new Gson().toJson(seances.get(positionIndex)));
                    seanceActivityIntent.putExtra("user", new Gson().toJson(user));
                    context.startActivity(seanceActivityIntent);
                }
            });
        }

        void bind(int listIndex) {
            listItemNumberView.setText(seances.get(listIndex).toString());
        }
    }
}
