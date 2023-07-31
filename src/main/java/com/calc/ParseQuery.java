package com.calc;

import java.util.Arrays;

public class ParseQuery {

    public String getCommand(String s){
        if (s.indexOf('#') != -1){
            return s.substring(0, s.lastIndexOf('#'));
        }
        return "eval";
    }

    public String getExpression(String s){
        if (s.indexOf('#') != -1){
            int offset = s.lastIndexOf('#')+1;
            char[] charArray = new char[offset];
            Arrays.fill(charArray, ' ');
            return new String(charArray)+s.substring(s.lastIndexOf('#')+1);
        }
        return s;
    }
}
