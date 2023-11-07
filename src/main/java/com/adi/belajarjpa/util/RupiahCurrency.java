package com.adi.belajarjpa.util;

import java.text.NumberFormat;
import java.util.Locale;

public class RupiahCurrency {

    public String toRupiahFormat(int nominal) {

        Locale locale = new Locale("in", "ID");
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(locale);
        return rupiahFormat.format(nominal);
    }


}
