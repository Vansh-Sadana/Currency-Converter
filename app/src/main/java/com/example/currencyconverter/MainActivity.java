package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText currencytobeconvertedET;
    TextView currencyconvertedTV;
    Spinner convertedfrom,convertedto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencytobeconvertedET = findViewById(R.id.currencytobeconertedET);
        currencyconvertedTV = findViewById(R.id.currencyconvertedTV);
        convertedfrom = findViewById(R.id.convertedfrom);
        convertedto = findViewById(R.id.convertedto);
        btn = findViewById(R.id.btn);

        String[] dropdownlist = {"USD","INR","EUR","NZD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dropdownlist);
        convertedfrom.setAdapter(adapter);
        convertedto.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeChange(convertedfrom.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");
                        double currency = Double.valueOf(currencytobeconvertedET.getText().toString());
                        double multiplier = Double.valueOf(rates.get(convertedto.getSelectedItem().toString()).toString());
                        double result = currency * multiplier;
                        currencyconvertedTV.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });
    }
}