package ru.sberbank.learning.rates;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ActivityCalculyator extends Activity {
    TextView charCode, summ, rubles;
    EditText editText;
    Float value, nominal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculyator);
        charCode = (TextView) findViewById(R.id.CharCodeCalc);
        summ = (TextView) findViewById(R.id.summ);
        editText = (EditText) findViewById(R.id.editText);
        rubles = (TextView) findViewById(R.id.rubles);

        rubles.setText("RUB");
        charCode.setText(getIntent().getStringExtra("charCode"));
        value = Float.valueOf(getIntent().getStringExtra("value"));

        nominal = Float.valueOf(getIntent().getStringExtra("nominal"));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((s == null )||(s.toString() == "")||(s.length()==0)){
                    summ.setText("");
                }else{

                   float sum =(value * Integer.parseInt(s.toString())/nominal);
                    if (Float.class.isInstance(sum)){
                       summ.setText(String.valueOf(String.format(Locale.US, "%.2f", sum)));}
                }



            }
        });


    }
}
