package com.example.fintechapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyRatesActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyRatesActivity";
    private EditText etCurrencyCode;
    private TextView tvCurrencyResult;
    private Button btnFetchRates;
    private ProgressBar progressBar;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates);

        // Initialize UI components
        etCurrencyCode = findViewById(R.id.etCurrencyCode);
        tvCurrencyResult = findViewById(R.id.tvCurrencyResult);
        btnFetchRates = findViewById(R.id.btnFetchRates);
        progressBar = findViewById(R.id.progressBar);

        // Initialize executor service
        executorService = Executors.newSingleThreadExecutor();

        btnFetchRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currencyCode = etCurrencyCode.getText().toString().trim().toUpperCase();
                if (!currencyCode.isEmpty()) {
                    fetchExchangeRate(currencyCode);
                } else {
                    tvCurrencyResult.setText("Please enter a currency code.");
                }
            }
        });
    }

    private void fetchExchangeRate(String currencyCode) {
        // Show progress
        progressBar.setVisibility(View.VISIBLE);
        btnFetchRates.setEnabled(false);
        tvCurrencyResult.setText("Fetching rate for " + currencyCode + "...");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result = getRateFromECB(currencyCode);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCurrencyResult.setText(result);
                        progressBar.setVisibility(View.GONE);
                        btnFetchRates.setEnabled(true);
                    }
                });
            }
        });
    }

    private String getRateFromECB(String currencyCode) {
        String apiUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            String xmlData = response.toString();

            // If it's EUR, return 1:1 rate
            if ("EUR".equals(currencyCode)) {
                // Find date
                Pattern datePattern = Pattern.compile("time='([^']+)'");
                Matcher dateMatcher = datePattern.matcher(xmlData);
                String date = dateMatcher.find() ? dateMatcher.group(1) : "today";

                return "1 EUR = 1.0000 EUR (Base currency)\nDate: " + date;
            }

            // Extract date
            Pattern datePattern = Pattern.compile("time='([^']+)'");
            Matcher dateMatcher = datePattern.matcher(xmlData);
            String date = dateMatcher.find() ? dateMatcher.group(1) : "today";

            // Find the rate for the requested currency
            Pattern ratePattern = Pattern.compile("currency='" + currencyCode + "' rate='([^']+)'");
            Matcher rateMatcher = ratePattern.matcher(xmlData);

            if (rateMatcher.find()) {
                String rate = rateMatcher.group(1);
                return "1 EUR = " + rate + " " + currencyCode + "\nDate: " + date;
            } else {
                return "Currency code '" + currencyCode + "' not found.\nTry USD, JPY, GBP, etc.";
            }

        } catch (IOException e) {
            return "Network error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            // Close connections
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Ignore close errors
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (executorService != null) {
            executorService.shutdown();
        }
        super.onDestroy();
    }
}