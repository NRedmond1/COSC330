package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spin = (Spinner) findViewById(R.id.Strategies);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.strategy, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void goToTest(View view){
        Intent intent = new Intent(this, AlphaVantage.class);
        startActivity(intent);
    }
}
