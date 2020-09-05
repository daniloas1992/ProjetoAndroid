package com.teste.consultar.organize.helpers;

import android.util.Base64;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class Base64Custom {

    public static String encodeBase64(final String value){
        return Base64.encodeToString(value.getBytes(), Base64.DEFAULT).replaceAll(("\\n|\\r"),"");
    }

    public static String decodeBase64(final String value){
        return new String(Base64.decode(value, Base64.DEFAULT));
    }

}
