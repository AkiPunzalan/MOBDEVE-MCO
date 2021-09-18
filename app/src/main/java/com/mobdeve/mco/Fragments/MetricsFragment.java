package com.mobdeve.mco.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.github.mikephil.charting.charts.*;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mobdeve.mco.R;

import java.util.ArrayList;

//cut feature, currently not in use
public class MetricsFragment extends Fragment {

    //BarChart weekChart;

    public MetricsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_metrics, container, false);

        /*
        weekChart = v.findViewById(R.id.chart1);

        ArrayList<BarEntry> taskdata = new ArrayList<>();
        taskdata.add(new BarEntry(0, 12));
        taskdata.add(new BarEntry(1, 13));
        taskdata.add(new BarEntry(2, 20));
        taskdata.add(new BarEntry(3, 0));
        taskdata.add(new BarEntry(4, 7));
        taskdata.add(new BarEntry(5, 12));
        taskdata.add(new BarEntry(6, 22));

        BarDataSet taskdataset = new BarDataSet(taskdata, "Days most active");
        BarData bardata = new BarData(taskdataset);

        weekChart.setData(bardata);
        weekChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white)); // left y-axis
        weekChart.getXAxis().setTextColor(getResources().getColor(R.color.white));
        weekChart.getLegend().setTextColor(getResources().getColor(R.color.white));
        weekChart.getDescription().setTextColor(getResources().getColor(R.color.white));

        weekChart.getAxisLeft().setDrawGridLines(false);
        weekChart.getAxisRight().setDrawGridLines(false);
        weekChart.getXAxis().setDrawGridLines(false);

        weekChart.getDescription().setText("Days most active");

        ArrayList<String> xLabel = new ArrayList<>();
        xLabel.add("Mon");
        xLabel.add("Tue");
        xLabel.add("Wed");
        xLabel.add("Thu");
        xLabel.add("Fri");
        xLabel.add("Sat");
        xLabel.add("Sun");

        XAxis xAxis = weekChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabel.get((int) value);
            }
        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);*/

        return v;
    }
}