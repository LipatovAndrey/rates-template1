package ru.sberbank.learning.rates;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

/**
 * Created by Андрей on 28.04.2017.
 */

public class MyAdapter extends BaseAdapter {
    int counter=60;

    private final Context mContext;

    public MyAdapter(Context context){this.mContext = context;}
    @Override
    public int getCount() {
        return counter;
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.Name);
            viewHolder.charcode = (TextView) view.findViewById(R.id.CharCode);
            viewHolder.code= (TextView) view.findViewById(R.id.NumCode);
            viewHolder.value = (TextView) view.findViewById(R.id.Value);
            viewHolder.nominal = (TextView) view.findViewById(R.id.nominal);

            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        InfoLoader myInfoLoader = new InfoLoader(holder, mContext, position);
        myInfoLoader.execute();

        return view;
    }
    private static class ViewHolder{
        private TextView name;
        private TextView value;
        private TextView code;
        private TextView charcode;
        private TextView nominal;
    }



    private static class InfoLoader extends AsyncTask<Void, Void, InfoLoader.InfoBundle> {
        private WeakReference<ViewHolder> holder;
        private int position;
        private Context mContext;
        private int count;
        public InfoLoader(ViewHolder holder, Context context, int position) {
            this.position = position;
            this.holder = new WeakReference<ViewHolder>(holder);
            mContext = context;
            //присваиваем ссылки на элементы холдера
        }

        @Override
        protected InfoBundle doInBackground(Void... params) {
            InfoBundle bundle = new InfoBundle();
            bundle.currenciesList = internetConnection();
            if (bundle == null) {
                return null;
            }

            return bundle;
        }

        @Override

        protected void onPostExecute(InfoBundle bundle) {
            if (bundle == null){
                return;
            }
            ViewHolder myHolder = holder.get();
            List<Currency> curlist = bundle.currenciesList.getCurrencies();
            count = curlist.size();
            if (position < count){
            Currency cur = curlist.get(position);

            myHolder.value.setText( String.valueOf(cur.getValue()));
            myHolder.name.setText(cur.getName());
            myHolder.charcode.setText(cur.getCharCode());
            myHolder.code.setText(String.valueOf(cur.getNumCode()));
            myHolder.nominal.setText(String.valueOf(cur.getNominal()));}
        }

        public static class InfoBundle {
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


