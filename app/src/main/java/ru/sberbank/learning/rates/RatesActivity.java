package ru.sberbank.learning.rates;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ru.sberbank.learning.rates.networking.CurrenciesList;
import ru.sberbank.learning.rates.networking.Currency;

public class RatesActivity extends Activity {
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        myList = (ListView) findViewById(R.id.ListView);



        MyAdapter adapter = new MyAdapter(this);
        myList.setAdapter(adapter);

    }


    }

