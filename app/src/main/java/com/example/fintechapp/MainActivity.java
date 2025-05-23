package com.example.fintechapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void changeTextButtonClicked(View view) {
        TextView tvMain = findViewById(R.id.tvMain);
        tvMain.setText("Button clicked!");
    }

    public void changeColorButtonClicked(View view) {
        TextView tvMain = findViewById(R.id.tvMain);
        tvMain.setTextColor(ContextCompat.getColor(this, R.color.green));
    }

    public void openWordCounterActivity(View view) {
        Intent intent = new Intent(this, WordCounterActivity.class);
        startActivity(intent);
    }

    public void openCurrencyRatesActivity(View view) {
        Intent intent = new Intent(this, CurrencyRatesActivity.class);
        startActivity(intent);
    }

}