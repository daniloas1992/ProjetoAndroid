package com.teste.consultar.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private Button btnSalvar;
    private TextInputEditText edtNome;
    private TextView tvExibirNome;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String NOME = "NOME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSalvar = findViewById(R.id.btnSalvar);
        edtNome = findViewById(R.id.edtNome);
        tvExibirNome = findViewById(R.id.tvExibirNome);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(PREFERENCES, 0);
                SharedPreferences.Editor editor = preferences.edit();

                if(edtNome.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha o nome", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String nome = edtNome.getText().toString();
                editor.putString(NOME, nome);
                editor.commit();

                tvExibirNome.setText(String.format("%s %s", "Olá", nome));
            }
        });

        SharedPreferences preferences = getSharedPreferences(PREFERENCES, 0);

        if(preferences.contains(NOME)){
            final String nome = preferences.getString(NOME,"");
            tvExibirNome.setText(String.format("%s %s", "Olá", nome));
        }

    }
}