package com.teste.consultar.fragmentos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teste.consultar.fragmentos.R;
import com.teste.consultar.fragmentos.fragments.ContatosFragment;
import com.teste.consultar.fragmentos.fragments.ConversasFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConversas;
    private Button btnContatos;
    private ConversasFragment conversasFragment;
    private ContatosFragment contatosFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        btnConversas = findViewById(R.id.btnConversas);
        btnContatos = findViewById(R.id.btnContatos);

        btnConversas.setOnClickListener(this);
        btnContatos.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnConversas:
                transaction = getSupportFragmentManager().beginTransaction();
                conversasFragment = ConversasFragment.newInstance("","");
                transaction.replace(R.id.frameContainer, conversasFragment);
                transaction.commit();
                break;

            case R.id.btnContatos:
                transaction = getSupportFragmentManager().beginTransaction();
                contatosFragment = ContatosFragment.newInstance("","");
                transaction.replace(R.id.frameContainer, contatosFragment);
                transaction.commit();
                break;
        }
    }
}
