package com.teste.consultar.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.teste.consultar.listadetarefas.R;
import com.teste.consultar.listadetarefas.helper.TarefaDAO;
import com.teste.consultar.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText edTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        edTarefa = findViewById(R.id.edTarefa);

        //Recupera a tarefa de estiver editando
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("TAREFA");

        if( tarefaAtual != null){
            edTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_menu_salvar:

                final String nomeTarefa = edTarefa.getText().toString();

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null) { // Editando tarefa
                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa(tarefaAtual.getId(), nomeTarefa);

                        //Atualizar no banco
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa atualizada!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao atualizar!",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else { // Salvando nova tarefa

                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa(nomeTarefa);
                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa salva!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao salvar!",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Digite a tarefa para salvar!",Toast.LENGTH_SHORT).show();
                    }
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}