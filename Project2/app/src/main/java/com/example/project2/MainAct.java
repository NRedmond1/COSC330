package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainAct extends AppCompatActivity {

        private ArrayList<Model> productList;
        int count = 0;
        Chronometer simple;
        long elapsedTime;
        Thread thread;
        String[] symbols = {"AAPL", "MSFT", "AMZN", "FB", "GOOGL", "IBM", "ACM", "ACN", "ADI", "SNAP"};
        String[] symbolName = {"Apple", "Microsoft", "Amazon", "Facebook", "Google", "IBM", "ACM", "ACN", "ADI", "Snapchat"};
        Model[] item = new Model[10];
        AlphaVantage[] stock = new AlphaVantage[10];
        listviewAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mainlist);

            for(int i=0; i<10; i++)
                stock[i] = new AlphaVantage();

            productList = new ArrayList<Model>();
            ListView lview = (ListView) findViewById(R.id.listview);
            adapter = new listviewAdapter(this, productList);
            lview.setAdapter(adapter);

            simple = new Chronometer(this);

            simple.setBase(SystemClock.elapsedRealtime());
            simple.start();
            elapsedTime = (SystemClock.elapsedRealtime() - simple.getBase())/1000000;
            populateList();

            thread = new Thread(){
                @Override
                public void run(){
                    try {
                        while (!thread.isInterrupted()){
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("Time", "Elapsed" + elapsedTime);
                                    if( elapsedTime > 90 ){
                                        if(count == 0)
                                            count = 5;
                                        else
                                            count = 0;
                                        populateList();
                                        adapter.notifyDataSetChanged();
                                        simple.setBase(SystemClock.elapsedRealtime());
                                        simple.start();
                                    }
                                    elapsedTime = (SystemClock.elapsedRealtime() - simple.getBase())/1000;
                                }
                            });
                        }
                    }
                    catch (InterruptedException e){

                    }
                }
            };
            thread.start();


            adapter.notifyDataSetChanged();

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Log.d("MSG", "Clicked List");
                }
            });
        }
        boolean firstTime = true;
        @SuppressLint("DefaultLocale")
        private void populateList() {
            try {
                for (int x = count; x < count + 5; x++) {
                    stock[x].executeFunc1(symbols[x]);
                    stock[x].executeFunc2(symbols[x]);
                }
            } catch (Exception e) {
                //do nothing
            }

            Float temp, temp2, temp3, temp4;
            String t, t2, t3, t4;
            for (int y = count; y < count + 5; y++) {
                temp = Float.valueOf(stock[y].data[4]);
                temp2 = Float.valueOf(stock[y].data[5]);
                temp3 = Float.valueOf(stock[y].data[6]);
                temp4 = Float.valueOf(stock[y].data[7]);
                t = String.format("%.2f", temp);
                t2 = String.format("%.2f", temp2);
                t3 = String.format("%.2f", temp3);
                t4 = String.format("%.2f", temp4);

                item[y] = new Model(stock[y].data[0], symbolName[y], stock[y].data[1], stock[y].data[2], stock[y].data[3], t+" / "+t2, t3 + " / " + t4, stock[y].data[8]);
                productList.add(y, item[y]);
                if( firstTime == false )
                    productList.remove(y+1);
                //productList.add(item[y]);
            }
            if(count == 5)
                firstTime = false;

        }
    }
