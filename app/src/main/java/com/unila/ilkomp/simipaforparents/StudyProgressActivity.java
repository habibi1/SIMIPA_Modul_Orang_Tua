package com.unila.ilkomp.simipaforparents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.unila.ilkomp.simipaforparents.adapter.StudyProgressAdapter;
import com.unila.ilkomp.simipaforparents.model.StudyProgress;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class StudyProgressActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    RecyclerView recyclerView;
    private ArrayList<StudyProgress> list = new ArrayList<>();
    Toolbar toolbar;
    LineChart lineChartSKS, lineChartIPK;
    PieChart pieChartPerbandinganNilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_progress);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LineChartSKS();
        LineChartIPK();
        PieChartPerbandinganNilai();
    }

    private void LineChartSKS(){

        String[] strings = {"0", "1", "2", "2", "3", "4", "5", "6"};

        lineChartSKS = findViewById(R.id.line_chart_sks);
        lineChartSKS.setOnChartGestureListener(StudyProgressActivity.this);
        lineChartSKS.setOnChartValueSelectedListener(this);

        lineChartSKS.setDragEnabled(true);
        lineChartSKS.setScaleEnabled(false);

        LineDataSet lineDataSet1 = new LineDataSet(dataTotalSKS(), "SKS"); //sks
        lineDataSet1.setCircleColor(Color.RED);
        lineDataSet1.setCircleHoleColor(Color.RED);
        lineDataSet1.setLineWidth(2f);
        lineDataSet1.setColor(getResources().getColor(R.color.line_chart_1));
        lineDataSet1.setValueTextSize(10f);

        LineDataSet lineDataSet2 = new LineDataSet(dataSKSPerSemester(), "Total SKS"); //total sks
        lineDataSet2.setCircleColor(Color.RED);
        lineDataSet2.setCircleHoleColor(Color.RED);
        lineDataSet2.setLineWidth(2f);
        lineDataSet2.setColor(getResources().getColor(R.color.line_chart_2));
        lineDataSet2.setValueTextSize(10f);

        ArrayList<ILineDataSet> set = new ArrayList<>();
        set.add(lineDataSet1);
        set.add(lineDataSet2);

        LineData data = new LineData(set);

        lineChartSKS.setNoDataText("Data Kosong");
        lineChartSKS.setDrawGridBackground(true); // tampilkan grid
        //lineChart.setDrawBorders(true); //garis pinggir

        Description description = new Description();
        description.setEnabled(false);
        lineChartSKS.setDescription(description);

        XAxis xAxis = lineChartSKS.getXAxis();
        xAxis.setValueFormatter(new MyXAxisFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.removeAllLimitLines();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(lineDataSet1.getEntryCount()-1);
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = lineChartSKS.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setAxisMaximum(170f);
        yAxis.setAxisMinimum(0f);
        yAxis.setDrawLimitLinesBehindData(true);

        lineChartSKS.getAxisRight().setEnabled(false);
        lineChartSKS.setHighlightPerDragEnabled(true);
        lineChartSKS.setHighlightPerTapEnabled(true);
        lineChartSKS.setExtraBottomOffset(5f);

        lineChartSKS.animateY(2000, Easing.EaseInOutQuart);

        MyMarkerView mv = new MyMarkerView(this, R.layout.chart_market_custom);
        lineChartSKS.setMarker(mv);

        lineChartSKS.setData(data);
        lineChartSKS.invalidate();
    }

    private void LineChartIPK(){

        String[] strings = {"0", "1", "2", "2", "3", "4", "5", "6"};

        lineChartIPK = findViewById(R.id.line_chart_ipk);
        lineChartIPK.setOnChartGestureListener(StudyProgressActivity.this);
        lineChartIPK.setOnChartValueSelectedListener(this);

        lineChartIPK.setDragEnabled(true);
        lineChartIPK.setScaleEnabled(false);

        LineDataSet lineDataSet1 = new LineDataSet(dataIPS(), "IPS"); //sks total
        lineDataSet1.setCircleColor(Color.RED);
        lineDataSet1.setCircleHoleColor(Color.RED);
        lineDataSet1.setLineWidth(2f);
        lineDataSet1.setColor(getResources().getColor(R.color.line_chart_1));
        lineDataSet1.setValueTextSize(10f);

        LineDataSet lineDataSet2 = new LineDataSet(dataIPK(), "IPK"); //sks saat ini
        lineDataSet2.setCircleColor(Color.RED);
        lineDataSet2.setCircleHoleColor(Color.RED);
        lineDataSet2.setLineWidth(2f);
        lineDataSet2.setColor(getResources().getColor(R.color.line_chart_2));
        lineDataSet2.setValueTextSize(10f);

        ArrayList<ILineDataSet> set = new ArrayList<>();
        set.add(lineDataSet1);
        set.add(lineDataSet2);

        LineData data = new LineData(set);

        lineChartIPK.setNoDataText("Data Kosong");
        lineChartIPK.setDrawGridBackground(true); // tampilkan grid
        //lineChart.setDrawBorders(true); //garis pinggir

        Description description = new Description();
        description.setEnabled(false);
        lineChartIPK.setDescription(description);

        XAxis xAxis = lineChartIPK.getXAxis();
        xAxis.setValueFormatter(new MyXAxisFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.removeAllLimitLines();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(lineDataSet1.getEntryCount()-1);
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = lineChartIPK.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setAxisMaximum(4.5f);
        yAxis.setAxisMinimum(0f);
        yAxis.setDrawLimitLinesBehindData(true);

        lineChartIPK.getAxisRight().setEnabled(false);
        lineChartIPK.setHighlightPerDragEnabled(true);
        lineChartIPK.setHighlightPerTapEnabled(true);
        lineChartIPK.setExtraBottomOffset(5f);

        lineChartIPK.animateY(2000, Easing.EaseInOutQuart);

        MyMarkerView mv = new MyMarkerView(this, R.layout.chart_market_custom);
        lineChartIPK.setMarker(mv);

        lineChartIPK.setData(data);
        lineChartIPK.invalidate();
    }

    private void PieChartPerbandinganNilai(){

        pieChartPerbandinganNilai = findViewById(R.id.pie_chart_perbandingan_nilai);

        int[] PIE_COLORS = {
                rgb("#005005"),
                rgb("#2e7d32"),
                rgb("#49AE4F"),
                rgb("#A3C800"),
                rgb("#D3B810"),
                rgb("#EA8B00"),
                rgb("#DF0000")
        };

        pieChartPerbandinganNilai.setUsePercentValues(true);
        pieChartPerbandinganNilai.getDescription().setEnabled(false);
        pieChartPerbandinganNilai.setExtraOffsets(5,5,5,0);

        pieChartPerbandinganNilai.setDragDecelerationEnabled(true);
        pieChartPerbandinganNilai.setDrawHoleEnabled(true);
        pieChartPerbandinganNilai.setHoleColor(Color.WHITE);
        pieChartPerbandinganNilai.setTransparentCircleRadius(0f);

        pieChartPerbandinganNilai.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        pieChartPerbandinganNilai.animateY(2000, Easing.EaseInOutQuart);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(14f, "A"));
        yValues.add(new PieEntry(11f, "B+"));
        yValues.add(new PieEntry(10f, "B"));
        yValues.add(new PieEntry(3f, "C+"));
        yValues.add(new PieEntry(1f, "C"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(PIE_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyPercentFormatter(pieChartPerbandinganNilai));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChartPerbandinganNilai.setData(data);

    }

    private static class MyXAxisFormatter extends ValueFormatter{

        private String[] values;

        private MyXAxisFormatter(String[] values){
            this.values = values;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            try {
                return values[(int)value];
            } catch (Exception e){
                return "";
            }
        }
    }

    private static class MyPercentFormatter extends ValueFormatter{
        DecimalFormat decimalFormat;
        PieChart pieChart;

        public MyPercentFormatter() {
            decimalFormat = new DecimalFormat("###,###,##0.0");
        }

        // Can be used to remove percent signs if the chart isn't in percent mode
        public MyPercentFormatter(PieChart pieChart) {
            this();
            this.pieChart = pieChart;
        }

        @Override
        public String getFormattedValue(float value) {
            if (value == 0.0f)
                return "";
            else
                return decimalFormat.format(value) + " %";
        }

        @Override
        public String getPieLabel(float value, PieEntry pieEntry) {
            if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
                // Converted to percent
                return getFormattedValue(value);
            } else {
                // raw value, skip percent sign
                return decimalFormat.format(value);
            }
        }
    }

    @SuppressLint("ViewConstructor")
    public static class MyMarkerView extends MarkerView {
        private TextView tvContent;
        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            // find your layout components
            tvContent = (TextView) findViewById(R.id.tvContent);
        }
        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @SuppressLint("SetTextI18n")
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText("" + e.getY());
            // this will perform necessary layouting
            super.refreshContent(e, highlight);
        }
        private MPPointF mOffset;
        @Override
        public MPPointF getOffset() {
            if(mOffset == null) {
                // center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
            }
            return mOffset;
        }
    }

    private ArrayList<Entry> dataTotalSKS(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0f, 0f));
        dataVals.add(new Entry(1f, 36f));
        dataVals.add(new Entry(2f, 23f));
        dataVals.add(new Entry(3f, 2f));
        dataVals.add(new Entry(4f, 24f));
        dataVals.add(new Entry(5f, 24f));
        dataVals.add(new Entry(6f, 22f));
        dataVals.add(new Entry(7f, 22f));

        return dataVals;
    }

    private ArrayList<Entry> dataSKSPerSemester(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0f, 0f));
        dataVals.add(new Entry(1f, 36f));
        dataVals.add(new Entry(2f, 59f));
        dataVals.add(new Entry(3f, 61f));
        dataVals.add(new Entry(4f, 85f));
        dataVals.add(new Entry(5f, 109f));
        dataVals.add(new Entry(6f, 131f));
        dataVals.add(new Entry(7f, 153f));

        return dataVals;
    }

    private ArrayList<Entry> dataIPK(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0f, 0f));
        dataVals.add(new Entry(1f, 2.14f));
        dataVals.add(new Entry(2f, 2.58f));
        dataVals.add(new Entry(3f, 2.65f));
        dataVals.add(new Entry(4f, 2.93f));
        dataVals.add(new Entry(5f, 3.03f));
        dataVals.add(new Entry(6f, 3.11f));
        dataVals.add(new Entry(7f, 3.11f));

        return dataVals;
    }

    private ArrayList<Entry> dataIPS(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0f, 0f));
        dataVals.add(new Entry(1f, 2.14f));
        dataVals.add(new Entry(2f, 3.28f));
        dataVals.add(new Entry(3f, 4.00f));
        dataVals.add(new Entry(4f, 3.63f));
        dataVals.add(new Entry(5f, 3.35f));
        dataVals.add(new Entry(6f, 3.52f));
        dataVals.add(new Entry(7f, 0f));

        return dataVals;
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudyProgressAdapter studyProgressAdapter = new StudyProgressAdapter(list);
        recyclerView.setAdapter(studyProgressAdapter);

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
