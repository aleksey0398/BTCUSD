package com.bitfenix.btcusd;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private final String LOG_ARGS = getClass().getName();

    LineChart chartBTCUSD;
    MyWebSocketConnection connection;

    private int x = 0;
    ArrayList<Entry> entries = new ArrayList<>();
    Observer<? super Object> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (MyNetwork.isWorking(getApplicationContext()))
            openConnection();
        else
            showTroubleDialog("Проверьте интернет соединение");


    }

    private void initView() {
        chartBTCUSD = findViewById(R.id.chart1);
        chartBTCUSD.setDragEnabled(true);
        chartBTCUSD.setScaleEnabled(true);
    }

    private void refreshChart(Float value) {

        entries.add(new Entry(++x, value));

        LineDataSet dataSet = new LineDataSet(entries, "Стоимость биткоина к доллару");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);

        chartBTCUSD.setData(lineData);

        chartBTCUSD.invalidate();

    }


    private void openConnection() {
        observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                log("onSubscribe", d.isDisposed());
            }

            @Override
            public void onNext(Object s) {
                log("onNext", s);
                refreshChart(((ResponseObject) s).getLastPrice());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                connection.closeConnection();
                showTroubleDialog(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                log("onComplete");

            }
        };

        connection = new MyWebSocketConnection(observer);
    }

    private void showTroubleDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Ошибочка :(")
                .setMessage(msg)
                .setPositiveButton("Обновить", (dialog, which) -> {
                    recreate();
                })
                .show();
    }


    private void log(String method, Object message) {
        Log.i(LOG_ARGS, method + "(): " + String.valueOf(message));
    }

    private void log(String method) {
        Log.i(LOG_ARGS, method + "()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null)
            connection.closeConnection();
        log("onDestroy");
    }
}