package com.healthbank;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        setmaterialDesign();
        back();
        setTitle("Height");

        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(100, 0));
        entries.add(new BarEntry(105, 1));
        entries.add(new BarEntry(110, 2));
        entries.add(new BarEntry(115, 3));
        entries.add(new BarEntry(120, 4));

        BarDataSet bardataset = new BarDataSet(entries, "Height");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
    }
}
