package ru.sberbank.learning.rates;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import ru.sberbank.learning.rates.storage.CurrenciesStorage;

public class RatesActivity extends Activity {
    private ListView myList;
    CurrenciesStorage currenciesStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        myList = (ListView) findViewById(R.id.ListView);
        currenciesStorage = ((ActivitySampleApplication)getApplication()).getStorage();
//        MyAdapter adapter = new MyAdapter(this);
//           myList.setAdapter(adapter);
        if(currenciesStorage.isReady()){
            MyAdapterWithCurrencies adapter = new MyAdapterWithCurrencies(currenciesStorage, this);
            myList.setAdapter(adapter);}else {
            InfoLoader loader = new InfoLoader(this);
            loader.execute();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nominal = (TextView) view.findViewById(R.id.nominal);
                TextView value = (TextView) view.findViewById(R.id.Value);
                TextView charCode = (TextView) view.findViewById(R.id.CharCode);

                Intent intent = new Intent(getApplicationContext(), ActivityCalculyator.class);
                intent.putExtra("nominal", nominal.getText());
                intent.putExtra("value", value.getText());
                intent.putExtra("charCode", charCode.getText());
                startActivity(intent);

            }
        });

    }

    private void update(){
        MyAdapterWithCurrencies adapter = new MyAdapterWithCurrencies(currenciesStorage, this);
        myList.setAdapter(adapter);

    }



    private class InfoLoader extends AsyncTask<Void, Void, InfoLoader.InfoBundle> {
        WeakReference<Activity> Activity;
        CurrenciesStorage currenciesStorage;
        RatesActivity activity;

        public InfoLoader(Context context) {
            this.activity = (RatesActivity) context;
           this.currenciesStorage = ((ActivitySampleApplication)getApplication()).getStorage();
            this.Activity = new WeakReference<Activity>(this.activity);

        }

        @Override
        protected InfoBundle doInBackground(Void... params) {
            InfoLoader.InfoBundle bundle = new InfoLoader.InfoBundle();
            bundle.currenciesList = internetConnection();
            if (bundle == null) {
                return null;
            }
            return bundle;
        }

        @Override
        protected void onPostExecute(InfoBundle bundle) {
            CurrenciesStorage mStorage = currenciesStorage;
            RatesActivity calledActivity = (RatesActivity) Activity.get();
            if (bundle == null||mStorage==null||calledActivity==null){
                return;
            }
            Activity.clear();
            mStorage.setLoadedList(bundle.currenciesList);
            calledActivity.update();


        }

        public class InfoBundle {
            CurrenciesList currenciesList;
        }

        private CurrenciesList internetConnection()  {
            URL myURL = null;
            CurrenciesList currenciesListFromInternet = null;
            InputStream dataStream = null;
            try {
                myURL = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
                dataStream = myURL.openConnection().getInputStream();
                currenciesListFromInternet = CurrenciesList.readFromStream(dataStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    dataStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return currenciesListFromInternet;
        }

    }
    }


