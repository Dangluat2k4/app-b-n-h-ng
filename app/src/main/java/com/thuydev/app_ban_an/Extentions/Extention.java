package com.thuydev.app_ban_an.Extentions;

import java.text.NumberFormat;
import java.util.Locale;

public class Extention {
    public static String MakeStyleMoney(int price) {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(price);
    }
}
