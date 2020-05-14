package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    Button btnAdd;
    EditText editText;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list2);
        btnAdd = (Button) findViewById(R.id.btn_add);
        editText = (EditText) findViewById(R.id.et_stock);
        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String stocks = editText.getText().toString();
                list.add(stocks);
                arrayAdapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });

        listView.setAdapter(arrayAdapter);
    }

      //TEST FUNCTION
    public void goToTest(View view){
        Intent intent = new Intent(this, MainAct.class);
        intent.putStringArrayListExtra("keyStocks", list);
        startActivity(intent);
    }

}
