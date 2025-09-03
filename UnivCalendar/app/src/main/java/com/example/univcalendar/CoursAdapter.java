package com.example.univcalendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.CoursViewHolder> {

    private List<Cours> coursList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CoursAdapter(List<Cours> coursList) {
        this.coursList = coursList;
    }

    public void updateData(List<Cours> newList) {
        this.coursList = newList;
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged(); // Demande à RecyclerView de se redessiner
    }

    @Override
    public CoursViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cours, parent, false);
        return new CoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursViewHolder holder, int position) {
        Cours cours = coursList.get(position);

        holder.linearLayout.setBackground(null);

        holder.textName.setText(cours.getName());
        if(cours.getColor() != null){
            holder.cardView.setCardBackgroundColor(Color.parseColor(cours.getColor()));
        }else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFA726"));
        }
        if (position == selectedPosition) {
            holder.linearLayout.setBackground(ContextCompat.getDrawable(
                    holder.itemView.getContext(),
                    R.drawable.card_border_red
            ));
        } else {
            holder.linearLayout.setBackground(null); // pas de bordure si non sélectionné
        }

        // Quand on clique → on change la sélection
        holder.cardView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Rafraîchir l'ancien et le nouveau
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);
        });


        // Récupérer seulement l'heure
        String startOnlyHour = cours.getOnlyHour(cours.getStart());
        String endOnlyHour =  cours.getOnlyHour(cours.getEnd());

        holder.textDate.setText(startOnlyHour + " : "+ endOnlyHour);

        holder.textLocation.setText(cours.getLocation());
    }

    @Override
    public int getItemCount() {
        return coursList.size();
    }



    public static class CoursViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textDate, textLocation;
        CardView cardView;
        LinearLayout linearLayout;
        public CoursViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDate = itemView.findViewById(R.id.textDate);
            textLocation = itemView.findViewById(R.id.textLocation);
            cardView = itemView.findViewById(R.id.cardwiew);
            linearLayout= itemView.findViewById(R.id.linearInCard);
        }
    }
}
