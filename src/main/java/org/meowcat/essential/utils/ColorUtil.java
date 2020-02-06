package org.meowcat.essential.utils;

public class ColorUtil {
    public static String replaceColor(String str) {
        return str.replaceAll(LanguageUtil.colorAlt + "([0-9a-fmnrklo])","§$1");
    }
}
