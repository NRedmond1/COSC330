package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.*;
import java.io.*;


public class AlphaVantage extends AppCompatActivity {

    public TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_vantage);

        String triedFunction = "ERROR fuction";
        try {
            showInfo();
        } catch (Exception e) {
            text.setText(triedFunction);
        }
    }

    public void showInfo() throws Exception{
        text = findViewById(R.id.testBox);
        String urlName = "https://financialmodelingprep.com/api/v3/stock/real-time-price/AAPL";
        URL url = null;
        url = new URL(urlName);

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            text.setText(line);
        }

        reader.close();
    }

}
