package ru.mirea.azbukindu.mireaproject;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private ArrayList<Place> places = new ArrayList<>();

    private Context context;

    public PlacesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent, false);

        return new PlacesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();

        holder.titleCard.setText(places.get(pos).title);
        holder.descriptionCard.setText(places.get(pos).address);

        holder.buttonCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("title", places.get(pos).title);
            intent.putExtra("description", places.get(pos).description);
            intent.putExtra("address", places.get(pos).address);
            intent.putExtra("latitude", places.get(pos).lattitude);
            intent.putExtra("longitude", places.get(pos).longitude);
            Log.d("MYERROR", "INTENT START");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView card;
        private final TextView titleCard;
        private final TextView descriptionCard;
        private final Button buttonCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardWrapperPlace);
            titleCard = itemView.findViewById(R.id.titleCardPlace);
            descriptionCard = itemView.findViewById(R.id.descriptionCardPlace);
            buttonCard = itemView.findViewById(R.id.buttonCardPlace);
        }
    }
}