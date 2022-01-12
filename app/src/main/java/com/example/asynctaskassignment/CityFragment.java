package com.example.asynctaskassignment;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(String param1, String param2) {
        CityFragment fragment = new CityFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    TextView tvCityName;
    static TextView tvTemp;
    static TextView tvHumidity;
    static TextView tvWind;
    static TextView tvRainChances;
    static TextView tvBattery;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("City Weather");

        Bundle bundle = getArguments();
        String CityName = bundle.getString("CityName");

        requireActivity().registerReceiver(new BatteryStateReceiver(),new IntentFilter("android.intent.action.BATTERY_CHANGED"));

        tvCityName= view.findViewById(R.id.tvCityName);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvWind = view.findViewById(R.id.tvWind);
        tvRainChances = view.findViewById(R.id.tvRainChances);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvBattery = view.findViewById(R.id.tvBattery);

        tvCityName.setText(CityName);

        new forecastTask().execute(CityName);

    }
}