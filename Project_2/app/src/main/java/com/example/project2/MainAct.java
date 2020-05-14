package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class MainAct extends AppCompatActivity {

        private ArrayList<Model> productList;
        private ArrayList<String> symbol;
        int count = 0;
        Chronometer simple;
        EditText eText;
        long elapsedTime;
        int downCount;
        Thread thread;
        String[] symbols;
        String[] symbolName;
        Model[] item;
        AlphaVantage[] stock;
        listviewAdapter adapter;
        String message = "none";
        int remainder;
        int subremainder;


    private void ReadTextFile(MainAct view) throws IOException {
        //String string = "";
        //StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        InputStream is = this.getResources().openRawResource(R.raw.nasdaq);
        InputStream is2 = this.getResources().openRawResource(R.raw.nyse);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        String line = reader.readLine();
        String[] temp = line.split("\t");
        while (line != null) {
            //Log.d(">>>", temp[1]);
            temp = line.split("\t");
            for(int i=0; i<symbol.size(); i++){
                if(temp[0].equals(symbol.get(i))){
                    symbols[count] = symbol.get(i);
                    symbolName[count] = temp[1];
                    count++;
                }
            }
            line = reader.readLine();
        }
        String line2 = reader2.readLine();
        String[] temp2 = line2.split("\t");
        while (line2 != null) {
            //Log.d(">>>", temp2[0]);
            temp2 = line2.split("\t");
            for(int i=0; i<symbol.size(); i++){
                if(temp2[0].equals(symbol.get(i))){
                    symbols[count] = symbol.get(i);
                    symbolName[count] = temp2[1];
                    count++;
                }
            }
            line2 = reader2.readLine();
        }
        is.close();
        is2.close();
    }

    public void strategyradioClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    Toast.makeText(getApplicationContext(), getString(R.string.strategy1), Toast.LENGTH_LONG).show();
                    message = "strategy1";
                break;
            case R.id.radioButton2:
                if (checked)
                    Toast.makeText(getApplicationContext(), getString(R.string.strategy2), Toast.LENGTH_LONG).show();
                    message = "strategy2";
                break;
            case R.id.radioButton3:
                if (checked)
                    Toast.makeText(getApplicationContext(), getString(R.string.strategy3), Toast.LENGTH_LONG).show();
                    message = "strategy3";
                break;
            case R.id.radioButton4:
                if(checked)
                    Toast.makeText(getApplicationContext(), getString(R.string.strategy4), Toast.LENGTH_LONG).show();
                    message = "strategy4";
                break;
            default:
                message = "none";
                break;
        }

    }
        private void populate(){
            populateList();
            adapter.notifyDataSetChanged();
            simple.setBase(SystemClock.elapsedRealtime());
            simple.start();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mainlist);

            symbol=getIntent().getExtras().getStringArrayList("keyStocks");

            symbols = new String[symbol.size()];
            symbolName = new String[symbol.size()];
            item = new Model[symbol.size()];
            stock = new AlphaVantage[symbol.size()];

            try {
                ReadTextFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i=0; i<symbol.size(); i++)
                stock[i] = new AlphaVantage();

            productList = new ArrayList<Model>();
            ListView lview = (ListView) findViewById(R.id.listview);
            adapter = new listviewAdapter(this, productList);
            lview.setAdapter(adapter);

            simple = new Chronometer(this);
            eText = (EditText) findViewById(R.id.countdown);

            simple.setBase(SystemClock.elapsedRealtime());
            simple.start();
            elapsedTime = (SystemClock.elapsedRealtime() - simple.getBase())/1000000;
            populateList();

            remainder = symbol.size()%5;
            subremainder = 5-remainder;

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
                                    downCount = 65 - ((int)(elapsedTime));
                                    eText.setText(String.valueOf(downCount));
                                    if( elapsedTime > 65 ){
                                        if( symbol.size()%5 == 0){
                                            if(count+5 == symbol.size()){
                                                count = 0;
                                                populate();
                                            }
                                            else {
                                                count = count +5;
                                                populate();
                                            }
                                        }
                                        else {
                                            if (count+5 < (symbol.size() - remainder)) {
                                                count = count + 5;
                                                populate();
                                            }
                                            else if(count%5 != 0) {
                                                count = 0;
                                                populate();
                                            }
                                            else{
                                                if(symbol.size() < 5){
                                                    populate();
                                                }
                                                else {
                                                    count = count + remainder;
                                                    populate();
                                                }
                                            }
                                        }
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

                    String sym = ((TextView) view.findViewById(R.id.symbol)).getText().toString();
                    String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                    String last = ((TextView) view.findViewById(R.id.last)).getText().toString();
                    String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
                    String time = ((TextView) view.findViewById(R.id.times)).getText().toString();
                    String open = ((TextView) view.findViewById(R.id.open)).getText().toString();
                    String high = ((TextView) view.findViewById(R.id.high)).getText().toString();
                    String vol = ((TextView) view.findViewById(R.id.vol)).getText().toString();

                    Boolean timeSell = false;

                    String Devider = "/";
                    String [] temp_openclose = open.split(Devider);
                    String [] temp_highlow = high.split(Devider);

                    Float last_temp = Float.valueOf(last);
                    Float close_temp = Float.valueOf(temp_openclose[1]);
                    Float temp_res = (last_temp/close_temp);
                    Float temp_res1 = (close_temp / last_temp);
                    Float temp_high = Float.valueOf(temp_highlow[0]);
                    Float temp_low = Float.valueOf(temp_highlow[1]);
                    Float median = (temp_high+temp_low)/2;

                    String temp = String.valueOf(temp_res);
                    String temp1 = String.valueOf(temp_res1);
                    String temp_median = String.valueOf(median);

                    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                    Date one = new Date();
                    Date three = new Date();
                    Date current = new Date();
                    try {
                        one = parser.parse("13:00");
                        three = parser.parse("15:22");
                        current = parser.parse(time);
                    }
                    catch (Exception e){

                    }
                    if (current.after(one) && current.before(three)) {
                        //System.out.println("time is between.");
                        Log.d(">>", "time " + current + " is in between.");
                        timeSell = true;
                    }
                    else {
                        Log.d(">>", "time " + current + " is outside of");
                        //System.out.println("time is outside");
                        timeSell = false;
                    }




                    if(message.equals("strategy1")){
                             //Log.d("MSG", "Clicked Strategy 1");
                        if(temp_res >= 1.05){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "You should buy this stock!", Toast.LENGTH_LONG).show();

                         //   Log.d("Buy", "Buy stock NOW!!!!!!!");
                        }else if(temp_res1 >= 1.05){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "You should sell this stock!", Toast.LENGTH_LONG).show();
                           // Log.d("Sell", "Sell stock NOW!!!!!");
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "Hold this stock!", Toast.LENGTH_LONG).show();

                          //  Log.d("Hold Stock", "Don't Buy or Sell");
                        }
                    }
                    else if(message.equals("strategy2")){
                        //Log.d("MSG", "Clicked Strategy 1");
                        if(temp_res >= 1.05){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "You should sell this stock!", Toast.LENGTH_LONG).show();
                        }else if(temp_res1 >= 1.05){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "You should buy this stock!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Last : " + last + "\n"
                                            + "Close : " + temp_openclose[1] + "\n"
                                            + "Hold this stock!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(message.equals("strategy3")){
                        if(sym.length() == 4 && timeSell == true){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Symbol : " + sym + "\n"
                                            + "Hold this stock!", Toast.LENGTH_LONG).show();
                        }
                        else if( sym.length() == 4 ){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Symbol : " + sym + "\n"
                                            + "You should buy this stock!", Toast.LENGTH_LONG).show();
                        }
                        else if( timeSell == true ){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Symbol : " + sym + "\n"
                                            + "You should sell this stock!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "Symbol : " + sym + "\n"
                                            + "You should look away??", Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(message.equals("strategy4")){
                        if( last_temp < median){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "High/Low : " + high + "\n"
                                            + "Last :" + last + "\n"
                                            + "Median :" + temp_median + "\n"
                                            + "You should buy this stock!", Toast.LENGTH_LONG).show();
                        }
                        else if( last_temp > median){
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "High/Low : " + high + "\n"
                                            + "Last :" + last + "\n"
                                            + "Median :" + temp_median + "\n"
                                            + "You should sell this stock!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Name : " + name + "\n"
                                            + "High/Low : " + high + "\n"
                                            + "Last :" + last + "\n"
                                            + "Median :" + temp_median + "\n"
                                            + "Hold this stock!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.d("MSG", "Clicked List");
                        Toast.makeText(getApplicationContext(),
                            "Name : " + name + "\t$" + last + "\n"
                                    + "Strategy : No Strategy Selected.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        boolean firstTime = true;
        @SuppressLint("DefaultLocale")
        private void populateList() {
            if(count%5 == 0 && symbol.size() >= 5) {
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
                int sizehold;
                for (int y = count; y < count + 5; y++) {
                    temp = Float.valueOf(stock[y].data[4]);
                    temp2 = Float.valueOf(stock[y].data[5]);
                    temp3 = Float.valueOf(stock[y].data[6]);
                    temp4 = Float.valueOf(stock[y].data[7]);
                    t = String.format("%.2f", temp);
                    t2 = String.format("%.2f", temp2);
                    t3 = String.format("%.2f", temp3);
                    t4 = String.format("%.2f", temp4);

                    item[y] = new Model(stock[y].data[0], symbolName[y], stock[y].data[1], stock[y].data[2], stock[y].data[3], t + " / " + t2, t3 + " / " + t4, stock[y].data[8]);
                    //sizehold = productList.size();
                    productList.add(y, item[y]);
                    if (firstTime == false)
                        productList.remove(y + 1);
                    //productList.add(item[y]);
                }
                if (count+5 == symbol.size())
                    firstTime = false;
            }
            else if(symbol.size() < 5){
                try {
                    for (int x = count; x < symbol.size(); x++) {
                        stock[x].executeFunc1(symbols[x]);
                        stock[x].executeFunc2(symbols[x]);
                    }
                } catch (Exception e) {
                    //do nothing
                }

                Float temp, temp2, temp3, temp4;
                String t, t2, t3, t4;
                for (int y = count; y < symbol.size(); y++) {
                    temp = Float.valueOf(stock[y].data[4]);
                    temp2 = Float.valueOf(stock[y].data[5]);
                    temp3 = Float.valueOf(stock[y].data[6]);
                    temp4 = Float.valueOf(stock[y].data[7]);
                    t = String.format("%.2f", temp);
                    t2 = String.format("%.2f", temp2);
                    t3 = String.format("%.2f", temp3);
                    t4 = String.format("%.2f", temp4);

                    item[y] = new Model(stock[y].data[0], symbolName[y], stock[y].data[1], stock[y].data[2], stock[y].data[3], t + " / " + t2, t3 + " / " + t4, stock[y].data[8]);
                    productList.add(y, item[y]);
                    if (firstTime == false)
                        productList.remove(y + 1);
                }
                firstTime = false;
            }
            else{
                int some = count+subremainder;
                try {
                    for (int x = some; x < some + remainder; x++) {
                        stock[x].executeFunc1(symbols[x]);
                        stock[x].executeFunc2(symbols[x]);
                    }
                } catch (Exception e) {
                    //do nothing
                }

                Float temp, temp2, temp3, temp4;
                String t, t2, t3, t4;
                int sizehold;
                for (int y = some; y < some + remainder; y++) {
                    temp = Float.valueOf(stock[y].data[4]);
                    temp2 = Float.valueOf(stock[y].data[5]);
                    temp3 = Float.valueOf(stock[y].data[6]);
                    temp4 = Float.valueOf(stock[y].data[7]);
                    t = String.format("%.2f", temp);
                    t2 = String.format("%.2f", temp2);
                    t3 = String.format("%.2f", temp3);
                    t4 = String.format("%.2f", temp4);

                    item[y] = new Model(stock[y].data[0], symbolName[y], stock[y].data[1], stock[y].data[2], stock[y].data[3], t + " / " + t2, t3 + " / " + t4, stock[y].data[8]);
                    //sizehold = productList.size();
                    productList.add(y, item[y]);
                    if (firstTime == false)
                        productList.remove(y + 1);
                    //productList.add(item[y]);
                }
                firstTime = false;
            }
        }


    }
