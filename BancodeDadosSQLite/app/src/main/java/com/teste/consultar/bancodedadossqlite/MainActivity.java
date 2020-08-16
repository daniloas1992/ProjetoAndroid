package com.teste.consultar.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String BANCO_DADOS = "BANCODADOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            //Criar o banco de dados
            SQLiteDatabase bancoSQL = openOrCreateDatabase(BANCO_DADOS, MODE_PRIVATE, null);

            //Criar tabela
            bancoSQL.execSQL("CREATE TABLE IF NOT EXISTS T_PESSOAS (NOME VARCHAR, IDADE INT(2)) ");

            //Inserir dados
            bancoSQL.execSQL("INSERT INTO T_PESSOAS (NOME, IDADE) VALUES ('Danilo', 28)");
            bancoSQL.execSQL("INSERT INTO T_PESSOAS (NOME, IDADE) VALUES ('Lidy', 29)");

            //Recuperar dados
            Cursor cursor = bancoSQL.rawQuery("SELECT NOME, IDADE FROM T_PESSOAS", null);

            if (cursor != null && cursor.moveToFirst()){
                    do{
                        Log.i("RESULTADO", String.format("%s - %s anos", cursor.getString(cursor.getColumnIndex("NOME")), cursor.getString(cursor.getColumnIndex("IDADE"))));
                    }while(cursor.moveToNext());
            }

            if(cursor != null){
                cursor.close();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}