package com.mobdeve.mco.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mobdeve.mco.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MetricsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MetricsFragment extends Fragment {

    BarChart weekChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MetricsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MetricsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MetricsFragment newInstance(String param1, String param2) {
        MetricsFragment fragment = new MetricsFragment();
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
        View v = inflater.inflate(R.layout.fragment_metrics, container, false);

//        weekChart = v.findViewById(R.id.chart1);
//
//        ArrayList<BarEntry> taskdata = new ArrayList<>();
//        taskdata.add(new BarEntry(0, 12));
//        taskdata.add(new BarEntry(1, 13));
//        taskdata.add(new BarEntry(2, 20));
//        taskdata.add(new BarEntry(3, 0));
//        taskdata.add(new BarEntry(4, 7));
//        taskdata.add(new BarEntry(5, 12));
//        taskdata.add(new BarEntry(6, 22));
//
//        BarDataSet taskdataset = new BarDataSet(taskdata, "Days most active");
//        BarData bardata = new BarData(taskdataset);
//
//        weekChart.setData(bardata);
//        weekChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white)); // left y-axis
//        weekChart.getXAxis().setTextColor(getResources().getColor(R.color.white));
//        weekChart.getLegend().setTextColor(getResources().getColor(R.color.white));
//        weekChart.getDescription().setTextColor(getResources().getColor(R.color.white));
//
//        weekChart.getAxisLeft().setDrawGridLines(false);
//        weekChart.getAxisRight().setDrawGridLines(false);
//        weekChart.getXAxis().setDrawGridLines(false);
//
//        weekChart.getDescription().setText("Days most active");
//
//        ArrayList<String> xLabel = new ArrayList<>();
//        xLabel.add("Mon");
//        xLabel.add("Tue");
//        xLabel.add("Wed");
//        xLabel.add("Thu");
//        xLabel.add("Fri");
//        xLabel.add("Sat");
//        xLabel.add("Sun");
//
//        XAxis xAxis = weekChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//
//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return xLabel.get((int) value);
//            }
//        };
//
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);

        return v;
    }
}