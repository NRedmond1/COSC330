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
        stock1.executeFunc1(symbols[0]);
        stock1.executeFunc2(symbols[0]);
        Log.d("stock2---: ", "> " + stock1.data[0]);
        try {
            updateTable();
        } catch (Exception e) {
            Log.d("ERROR2: ", "> Func");
        }
    }
/*
    public void main(String args[]) throws Exception {
        stock1.executeFunc1(symbols[0]);
        stock1.executeFunc2(symbols[0]);
        TimeUnit.SECONDS.sleep(5);
        updateTable();
    }
*/
    public void updateTable() throws Exception {
        String[] temp = stock1.getStringArray();
        symb1.setText(stock1.data[0]);
        Log.d("stock3---: ", "> " + stock1.data[0]);
        Log.d("Stock4---: ", "> " + temp[0]);
        /*
        name1.setText(symbolName[0]);

        last1.setText(stock1.data[1]);

        date1.setText(stock1.data[2]);

        time1.setText(stock1.data[3]);

        oc1.setText(stock1.data[4] );

        oc1.append("/" + stock1.data[5]);

        hl1.setText(stock1.data[6]);

        hl1.append("/" + stock1.data[7]);

        Vol1.setText(stock1.data[8]);

        pre1.setText(stock1.data[9]);
         */

    }
/*      //TEST FUNCTION
    public void goToTest(View view){
        Intent intent = new Intent(this, AlphaVantage.class);
        startActivity(intent);
    }
 */
}
