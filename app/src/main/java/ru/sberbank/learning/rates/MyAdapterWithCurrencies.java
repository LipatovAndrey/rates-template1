package ru.sberbank.learning.rates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ru.sberbank.learning.rates.networking.CurrenciesList;
import ru.sberbank.learning.rates.networking.Currency;
import ru.sberbank.learning.rates.storage.CurrenciesStorage;

/**
 * Created by user7 on 29.04.2017.
 */

public class MyAdapterWithCurrencies extends BaseAdapter {
    CurrenciesStorage mCurrenciesStorage;
    CurrenciesList currenciesList;
    List<Currency> currencyList;
    WeakReference <Context> mContext;

    public MyAdapterWithCurrencies(CurrenciesStorage CurrenciesStorage, Context context){
        this.mCurrenciesStorage= CurrenciesStorage;
        this.currenciesList = mCurrenciesStorage.getLoadedList();
        this.currencyList = this.currenciesList.getCurrencies();
        mContext = new WeakReference<Context>(context);
    }
    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Currency getItem(int position) {
        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(mContext.get());
            view = inflater.inflate(R.layout.item, parent, false);

            MyAdapterWithCurrencies.ViewHolder viewHolder = new MyAdapterWithCurrencies.ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.Name);
            viewHolder.charcode = (TextView) view.findViewById(R.id.CharCode);
            viewHolder.code= (TextView) view.findViewById(R.id.NumCode);
            viewHolder.value = (TextView) view.findViewById(R.id.Value);
            viewHolder.nominal = (TextView) view.findViewById(R.id.nominal);

            view.setTag(viewHolder);
        }
        MyAdapterWithCurrencies.ViewHolder holder = (MyAdapterWithCurrencies.ViewHolder) view.getTag();
        Currency cur = currencyList.get(position);

        holder.value.setText( String.valueOf(cur.getValue()));
        holder.name.setText(cur.getName());
        holder.charcode.setText(cur.getCharCode());
        holder.code.setText(String.valueOf(cur.getNumCode()));
        holder.nominal.setText(String.valueOf(cur.getNominal()));


        return view;

    }private static class ViewHolder{
        private TextView name;
        private TextView value;
        private TextView code;
        private TextView charcode;
        private TextView nominal;
    }
}
