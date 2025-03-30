package com.example.fintechapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fintechapp.helpers.TextAnalyzer;

public class WordCounterActivity extends AppCompatActivity {

    private EditText editText;
    private Spinner spinner;
    private Button countButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_word_counter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        spinner = findViewById(R.id.spinner);
        countButton = findViewById(R.id.countButton);
        resultTextView = findViewById(R.id.resultTextView);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();
                if (inputText.isEmpty()) {
                    Toast.makeText(WordCounterActivity.this, R.string.empty_input_warning, Toast.LENGTH_SHORT).show();
                    return;
                }

                String selection = spinner.getSelectedItem().toString();
                int count;
                if (selection.equals(getString(R.string.words_option))) {
                    count = TextAnalyzer.countWords(inputText);
                } else {
                    count = TextAnalyzer.countCharacters(inputText);
                }

                resultTextView.setText(getString(R.string.result_text, count));
            }
        });
    }
}
