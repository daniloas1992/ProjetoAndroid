package com.teste.consultar.minhasanotaes;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtAnotacao;
    AnotationPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new AnotationPreferences(getApplicationContext());
        edtAnotacao = findViewById(R.id.edtAnotacao);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String anotacao = edtAnotacao.getText().toString();

                if(anotacao.equals("")){
                    Snackbar.make(view, "Preencha a anotação!", Snackbar.LENGTH_SHORT).show();
                } else {
                    preferences.saveAnotation(anotacao);
                    Snackbar.make(view, "Anotação Salva!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        setSharedPreferences();
    }

    private void setSharedPreferences() {
        if(preferences != null){
            final String anotacao = preferences.getPreferenceAnotation();

            if(!anotacao.equals("")){
                edtAnotacao.setText(anotacao);
            }
        }
    }
}