package com.teste.consultar.organize.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teste.consultar.organize.R;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;
import com.teste.consultar.organize.helpers.Base64Custom;
import com.teste.consultar.organize.helpers.DateCustom;
import com.teste.consultar.organize.model.Movimentacao;
import com.teste.consultar.organize.model.Usuario;

public class ReceitasActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText edtDataReceita, edtCategoriaReceita, edtDescricaoReceita;
    private EditText edtValorReceita;
    private FloatingActionButton fabSalvarReceita;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabaseInstance();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAuthInstance();
    private double receitaTotalFirebase;
    private double novaReceita;
    private double totalComNovaReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        edtDataReceita = findViewById(R.id.edtDataReceita);
        edtCategoriaReceita = findViewById(R.id.edtCategoriaReceita);
        edtDescricaoReceita = findViewById(R.id.edtDescricaoReceita);
        edtValorReceita = findViewById(R.id.edtValorReceita);
        fabSalvarReceita = findViewById(R.id.fabSalvarReceita);

        fabSalvarReceita.setOnClickListener(this);
        edtDataReceita.setText(DateCustom.getDateToday());
        getReceitaTotal();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fabSalvarReceita) {
            Movimentacao movimentacao = validarInfoReceita();
            if(movimentacao != null) {
                setNovaReceita(movimentacao.getValor());
                setTotalComNovaReceita(getNovaReceita() + getReceitaTotalFirebase());
                atualizarReceitaTotalFirebase();
                movimentacao.salvarMovimentacao();
                finish();
            }
        }
    }

    public Movimentacao validarInfoReceita() {

        final double valor = !edtValorReceita.getText().toString().isEmpty() ? Double.parseDouble(edtValorReceita.getText().toString()) : 0.00 ;
        final String categoria = edtCategoriaReceita.getText().toString();
        final String descricao = edtDescricaoReceita.getText().toString();
        final String data = edtDataReceita.getText().toString();

        if(edtValorReceita.getText().toString().isEmpty()) {
            Toast.makeText(this, "Informe o valor!", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(categoria.isEmpty()) {
            Toast.makeText(this, "Informe a categoria!", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(descricao.isEmpty()) {
            Toast.makeText(this, "Informe a descrição!", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(categoria.isEmpty()) {
            Toast.makeText(this, "Informe a data!", Toast.LENGTH_SHORT).show();
            return null;
        }

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setValor(valor);
        movimentacao.setCategoria(categoria);
        movimentacao.setDescricao(descricao);
        movimentacao.setData(data);
        movimentacao.setTipo(Movimentacao.TIPO_RECEITA);

        return movimentacao;
    }

    private void getReceitaTotal() {

        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        DatabaseReference usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                if(usuario != null){
                    setReceitaTotalFirebase(usuario.getReceitaTotal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void atualizarReceitaTotalFirebase() {
        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        DatabaseReference usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        usuarioReference.child(Usuario.CHILD_USUARIOS_RECEITATOTAL_FIREBASE).setValue(getTotalComNovaReceita());
    }

    public double getReceitaTotalFirebase() {
        return receitaTotalFirebase;
    }

    public void setReceitaTotalFirebase(double receitaTotalFirebase) {
        this.receitaTotalFirebase = receitaTotalFirebase;
    }

    public double getNovaReceita() {
        return novaReceita;
    }

    public void setNovaReceita(double novaReceita) {
        this.novaReceita = novaReceita;
    }

    public double getTotalComNovaReceita() {
        return totalComNovaReceita;
    }

    public void setTotalComNovaReceita(double totalComNovaReceita) {
        this.totalComNovaReceita = totalComNovaReceita;
    }
}