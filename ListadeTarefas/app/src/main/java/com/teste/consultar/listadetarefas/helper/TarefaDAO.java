package com.teste.consultar.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.teste.consultar.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;

    public TarefaDAO(Context context) {

        DBHelper dbHelper = new DBHelper(context);
        writeDB = dbHelper.getWritableDatabase();
        readDB = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("NOME", tarefa.getNomeTarefa());

        try {
            writeDB.insert(DBHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Tarefa inserida com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao inserir no banco: "+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("NOME", tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};
            writeDB.update(DBHelper.TABELA_TAREFAS, cv, "ID=?", args);
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao atualizar registro no banco: "+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try {
            String[] args = {tarefa.getId().toString()};
            writeDB.delete(DBHelper.TABELA_TAREFAS, "ID=?", args);
            Log.i("INFO", "Tarefa excluída com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao excluir registro no banco: "+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT ID, NOME FROM " + DBHelper.TABELA_TAREFAS + " ;";

        Cursor cursor = readDB.rawQuery(sql, null);

        if(cursor != null) {
            if (cursor.moveToFirst()){
                do  {

                    Long id = cursor.getLong(cursor.getColumnIndex("ID"));
                    String nomeTarefa = cursor.getString(cursor.getColumnIndex("NOME"));

                    Tarefa tarefa = new Tarefa(id, nomeTarefa);

                    tarefas.add(tarefa);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }


        return tarefas;
    }
}
