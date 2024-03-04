package ru.mirea.azbukindu.mireaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MapFragment extends Fragment {
    public MapFragment() {
        // Required empty public constructor
    }
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Place> places = new ArrayList<>();

        places.add(new Place("МИРЭА", "РТУ МИРЭА - Российский технологический университет",
                "Москва, Проспект Вернадкского 78/4", 55.670005f, 37.479894f));
        places.add(new Place("Дом", "Дом милый дом!",
                "Москва, ул. Малое Понизовье, д. 8, с. 1", 55.6028f, 37.4156f));
        places.add(new Place("Звенигород", "Место, где прожил большую часть жизни!",
                "Звенигорд, мкр. Пронино, д. 7", 55.7249f, 36.8671f));
        places.add(new Place("ИЗИ ПАБ", "Вкусные, сочные бургеры, Лучше бургеров нигде нет!",
                "Москва, ул. Новопередлкино, 934", 55.6046f, 37.3568f));

        PlacesAdapter adapter = new PlacesAdapter(getContext());
        adapter.setPlaces(places);
        recycler_view.setAdapter(adapter);
    }
}
class Place{
    public String title;
    public String description;
    public String address;
    public float lattitude;
    public float longitude;

    public Place(String title, String description, String address, float lattitude, float longitude) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }
}