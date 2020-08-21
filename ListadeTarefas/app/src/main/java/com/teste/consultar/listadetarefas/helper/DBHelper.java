package com.teste.consultar.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    public static final String NAME_DB = "DB_TAREFAS";
    public static final String TABELA_TAREFAS = "T_TAREFAS";

    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS + " ( ");
        sql.append("    ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("    NOME TEXT NOT NULL ");
        sql.append(" ); ");

        try{
            sqLiteDatabase.execSQL(sql.toString());
            Log.i("INFO DB", "Tabela de dados criada");
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela: "+ e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
