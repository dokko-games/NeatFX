package com.dokko.win4jui.api.ui;

import java.util.ArrayList;
import java.util.Arrays;

public class Strings4JUI {

    public static String extractCapitals(String s){
        StringBuilder res = new StringBuilder();
        int l = s.length();
        for(int c = 0; c < l; c++){
            if(Character.isUpperCase(s.charAt(c))){
                char w = s.charAt(c);
                res.append(w);
            }
        }
        return res.toString();
    }
    public static ArrayList<String> extractWords(String s){
        return new ArrayList<>(Arrays.asList(s.split(" ")));
    }
    public static String getFirstLetter(String word) {
        if(word.isEmpty())return "";
        return word.substring(0, 1).toUpperCase();
    }
    public static ArrayList<String> extractWordsFirstLetter(String s){
        ArrayList<String> s2 = extractWords(s);
        ArrayList<String> s3 = new ArrayList<>();
        for(String word : s2){
            s3.add(getFirstLetter(word));
        }
        return s3;
    }
    public static String join(String[] text, String intermediate, String last){
        if (text == null || text.length == 0) {
            return "";
        }
        if (text.length == 1) {
            return text[0];
        }
        if (text.length == 2) {
            return text[0] + last + text[1];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length - 1; i++) {
            sb.append(text[i]);
            if (i < text.length - 2) {
                sb.append(intermediate);
            }
        }
        sb.append(last).append(text[text.length - 1]);

        return sb.toString();
    }
}
