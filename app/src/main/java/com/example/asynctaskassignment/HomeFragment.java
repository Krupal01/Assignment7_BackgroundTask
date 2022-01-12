package com.example.asynctaskassignment;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements BookMarkAdapter.ClickListner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = new Database(getContext());
        Cursor data = db.getData();
        if (data.getCount() != 0){
            while (data.moveToNext()){
                Cities.add(data.getString(1));
            }
        }

    }

    public ArrayList<String> Cities = new ArrayList<>();
    public BookMarkAdapter adapter;
    public RecyclerView recyclerView;
    public Database db;
//    SupportMapFragment supportMapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fMap);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng latLng) {
                            googleMap.clear();
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Cities.add(addresses.get(0).getLocality());
                            db.insertData(addresses.get(0).getLocality());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(),addresses.get(0).getLocality()+" added" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else {
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }
        recyclerView= view.findViewById(R.id.Bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookMarkAdapter(this);
        adapter.setCities(Cities);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void ClickListner(String str) {
        CityFragment fragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CityName",str);
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActivity,fragment)
                .addToBackStack(null)
                .commit();
    }
}