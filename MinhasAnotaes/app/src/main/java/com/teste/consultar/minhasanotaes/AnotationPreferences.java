package com.teste.consultar.minhasanotaes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class AnotationPreferences {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final static String ARQUIVO_PREFERENCIAS = "ARQUIVO_PREFERENCIAS";
    private final static String CHAVE_NOME = "CHAVE_NOME";

    public AnotationPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        editor = preferences.edit();
    }

    public void saveAnotation(final String anotacao){
        editor.putString(CHAVE_NOME, anotacao);
        editor.commit();
    }

    public String getPreferenceAnotation(){
        return preferences.getString(CHAVE_NOME,"");
    }
}
