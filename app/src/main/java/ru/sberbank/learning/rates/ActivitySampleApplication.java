package ru.sberbank.learning.rates;

import android.app.Application;

import ru.sberbank.learning.rates.storage.CurrenciesStorage;

/**
 * @author Дмитрий Соколов <DPSokolov.SBT@sberbank.ru>
 */

public class ActivitySampleApplication extends Application {

    private CurrenciesStorage mStorage = new CurrenciesStorage();

    public CurrenciesStorage getStorage() {
        return mStorage;
    }

}
