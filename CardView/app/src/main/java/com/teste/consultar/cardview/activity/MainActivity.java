package com.teste.consultar.cardview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.teste.consultar.cardview.R;
import com.teste.consultar.cardview.adapter.PostagemAdapter;
import com.teste.consultar.cardview.model.Postagem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPostagem;
    private List<Postagem> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPostagem = findViewById(R.id.recyclerViewPostagens);
        postagens = Postagem.getPostagemList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPostagem.setLayoutManager(layoutManager);

        //Se quiser que a rolagem seja para os lados:
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerPostagem.setLayoutManager(layoutManager);*/

        //Se quiser usar Grid
        /*RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2); // Segundo parêmtro é a quantidade de colunas
        recyclerPostagem.setLayoutManager(layoutManager);*/

        PostagemAdapter adapter = new PostagemAdapter(postagens);
        recyclerPostagem.setAdapter(adapter);
    }
}