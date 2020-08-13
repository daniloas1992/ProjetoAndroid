package com.teste.consultar.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgResultado;
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Bundle dados = getIntent().getExtras();
        int numero =dados.getInt("NUMERO");

        imgResultado = findViewById(R.id.imgResultado);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        if (numero == 0){
            imgResultado.setImageResource(R.drawable.moeda_cara);
        } else {
            imgResultado.setImageResource(R.drawable.moeda_coroa);
        }
    }

    @Override
    public void onClick(View view) {
       finish();
    }
}