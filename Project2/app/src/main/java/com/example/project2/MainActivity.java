package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] symbols = {"AAPL", "MSFT", "AMZN", "FB", "GOOGL"};
    String[] symbolName = {"Apple", "Microsoft", "Amazon", "Facebook", "Google"};
    TextView symb1, symb2, symb3, symb4, symb5;
    TextView name1, name2, name3, name4, name5;
    TextView last1, last2, last3, last4, last5;
    TextView date1, date2, date3, date4, date5;
    TextView time1, time2, time3, time4, time5;
    TextView oc1, oc2, oc3, oc4, oc5;
    TextView hl1, hl2, hl3, hl4, hl5;
    TextView Vol1, Vol2, Vol3, Vol4, Vol5;
    TextView pre1, pre2, pre3, pre4, pre5;

    AlphaVantage stock1;
    AlphaVantage stock2;
    AlphaVantage stock3;
    AlphaVantage stock4;
    AlphaVantage stock5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spin = (Spinner) findViewById(R.id.Strategies);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.strategy, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        symb1 = findViewById(R.id.symbol1); symb2 = findViewById(R.id.symbol2); symb3 = findViewById(R.id.symbol3); symb4 = findViewById(R.id.symbol4); symb5 = findViewById(R.id.symbol5);
        name1 = findViewById(R.id.name1); name2 = findViewById(R.id.name2); name3 = findViewById(R.id.name3); name4 = findViewById(R.id.name4); name5 = findViewById(R.id.name5);
        last1 = findViewById(R.id.last1); last2 = findViewById(R.id.last2); last3 = findViewById(R.id.last3); last4 = findViewById(R.id.last4); last5 = findViewById(R.id.last5);
        date1 = findViewById(R.id.date1); date2 = findViewById(R.id.date2); date3 = findViewById(R.id.date3); date4 = findViewById(R.id.date4); date5 = findViewById(R.id.date5);
        time1 = findViewById(R.id.time1); time2 = findViewById(R.id.time2); time3 = findViewById(R.id.time3); time4 = findViewById(R.id.time4); time5 = findViewById(R.id.time5);
        oc1 = findViewById(R.id.oc1); oc2 = findViewById(R.id.oc2); oc3 = findViewById(R.id.oc3); oc4 = findViewById(R.id.oc4); oc5 = findViewById(R.id.oc5);
        hl1 = findViewById(R.id.hl1); hl2 = findViewById(R.id.hl2); hl3 = findViewById(R.id.hl3); hl4 = findViewById(R.id.hl4); hl5 = findViewById(R.id.hl5);
        Vol1 = findViewById(R.id.vol1); Vol2 = findViewById(R.id.vol2); Vol3 = findViewById(R.id.vol3); Vol4 = findViewById(R.id.vol4); Vol5 = findViewById(R.id.vol5);
        pre1 = findViewById(R.id.pred1); pre2 = findViewById(R.id.pred2); pre3 = findViewById(R.id.pred3); pre4 = findViewById(R.id.pred4); pre5 = findViewById(R.id.pred5);

        stock1 = new AlphaVantage();
        stock2 = new AlphaVantage();
        stock3 = new AlphaVantage();
        stock4 = new AlphaVantage();
        stock5 = new AlphaVantage();

        try {
            //updateSymbols(symbols[0], symbols[1], symbols[2], symbols[3], symbols[4]);
        } catch (Exception e) {
            Log.d("ERROR", "> Function updateSymbol");
        }
    }

    public void updateSymbols(String s1, String s2, String s3, String s4, String s5) throws Exception {
        stock1.executeFunc1(s1);
        stock2.executeFunc1(s2);
        stock3.executeFunc1(s3);
        stock4.executeFunc1(s4);
        stock5.executeFunc1(s5);

        stock1.executeFunc2(s1);
        stock2.executeFunc2(s2);
        stock3.executeFunc2(s3);
        stock4.executeFunc2(s4);
        stock5.executeFunc2(s5);
        updateTable();
    }

    public void updateTable() throws Exception {
        symb1.setText(stock1.data[0]);
        name1.setText(symbolName[0]);
        last1.setText(stock1.data[1]);
        date1.setText(stock1.data[2]);
        time1.setText(stock1.data[3]);
        oc1.setText(stock1.data[4] );
        oc1.append("/\n" + stock1.data[5]);
        hl1.setText(stock1.data[6]);
        hl1.append("/\n" + stock1.data[7]);
        Vol1.setText(stock1.data[8]);
        pre1.setText(stock1.data[9]);

        symb2.setText(stock2.data[0]);
        name2.setText(symbolName[1]);
        last2.setText(stock2.data[1]);
        date2.setText(stock2.data[2]);
        time2.setText(stock2.data[3]);
        oc2.setText(stock2.data[4] );
        oc2.append("/\n" + stock2.data[5]);
        hl2.setText(stock2.data[6]);
        hl2.append("/\n" + stock2.data[7]);
        Vol2.setText(stock2.data[8]);
        pre2.setText(stock2.data[9]);

        symb3.setText(stock3.data[0]);
        name3.setText(symbolName[2]);
        last3.setText(stock3.data[1]);
        date3.setText(stock3.data[2]);
        time3.setText(stock3.data[3]);
        oc3.setText(stock3.data[4] );
        oc3.append("/\n" + stock3.data[5]);
        hl3.setText(stock3.data[6]);
        hl3.append("/\n" + stock3.data[7]);
        Vol3.setText(stock3.data[8]);
        pre3.setText(stock3.data[9]);

        symb4.setText(stock4.data[0]);
        name4.setText(symbolName[3]);
        last4.setText(stock4.data[1]);
        date4.setText(stock4.data[2]);
        time4.setText(stock4.data[3]);
        oc4.setText(stock4.data[4] );
        oc4.append("/\n" + stock4.data[5]);
        hl4.setText(stock4.data[6]);
        hl4.append("/\n" + stock4.data[7]);
        Vol4.setText(stock4.data[8]);
        pre4.setText(stock4.data[9]);

        symb5.setText(stock5.data[0]);
        name5.setText(symbolName[4]);
        last5.setText(stock5.data[1]);
        date5.setText(stock5.data[2]);
        time5.setText(stock5.data[3]);
        oc5.setText(stock5.data[4] );
        oc5.append("/" + stock5.data[5]);
        hl5.setText(stock5.data[6]);
        hl5.append("/" + stock5.data[7]);
        Vol5.setText(stock5.data[8]);
        pre5.setText(stock5.data[9]);

    }
      //TEST FUNCTION
    public void goToTest(View view){
        Intent intent = new Intent(this, MainAct.class);
        startActivity(intent);
    }

}
