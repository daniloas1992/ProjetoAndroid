package com.teste.consultar.organize.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class DespesasActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText edtDataDespesa, edtCategoriaDespesa, edtDescricaoDespesa;
    private EditText edtValorDespesa;
    private FloatingActionButton fabSalvarDespesa;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabaseInstance();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAuthInstance();
    private double despesaTotalFirebase;
    private double novaDespesa;
    private double totalComNovaDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        edtDataDespesa = findViewById(R.id.edtDataDespesa);
        edtCategoriaDespesa = findViewById(R.id.edtCategoriaDespesa);
        edtDescricaoDespesa = findViewById(R.id.edtDescricaoDespesa);
        edtValorDespesa = findViewById(R.id.edtValorDespesa);
        fabSalvarDespesa = findViewById(R.id.fabSalvarDespesa);

        fabSalvarDespesa.setOnClickListener(this);
        edtDataDespesa.setText(DateCustom.getDateToday());
        getDespesaTotal();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fabSalvarDespesa) {
            Movimentacao movimentacao = validarInfoDespesas();
            if(movimentacao != null) {
                setNovaDespesa(movimentacao.getValor());
                setTotalComNovaDespesa(getNovaDespesa() + getDespesaTotalFirebase());
                atualizarDespesaTotalFirebase();
                movimentacao.salvarMovimentacao();
                finish();
            }
        }
    }

    public Movimentacao validarInfoDespesas() {

        final double valor = !edtValorDespesa.getText().toString().isEmpty() ? Double.parseDouble(edtValorDespesa.getText().toString()) : 0.00 ;
        final String categoria = edtCategoriaDespesa.getText().toString();
        final String descricao = edtDescricaoDespesa.getText().toString();
        final String data = edtDataDespesa.getText().toString();

        if(edtValorDespesa.getText().toString().isEmpty()) {
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
        movimentacao.setTipo(Movimentacao.TIPO_DESPESA);

        return movimentacao;
    }

    private void getDespesaTotal() {

        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        DatabaseReference usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                setDespesaTotalFirebase(usuario.getDespesaTotal());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void atualizarDespesaTotalFirebase() {
        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        DatabaseReference usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        usuarioReference.child(Usuario.CHILD_USUARIOS_DESPESATOTAL_FIREBASE).setValue(getTotalComNovaDespesa());
    }

    public double getDespesaTotalFirebase() {
        return despesaTotalFirebase;
    }

    public void setDespesaTotalFirebase(double despesaTotalFirebase) {
        this.despesaTotalFirebase = despesaTotalFirebase;
    }

    public double getNovaDespesa() {
        return novaDespesa;
    }

    public void setNovaDespesa(double novaDespesa) {
        this.novaDespesa = novaDespesa;
    }

    public double getTotalComNovaDespesa() {
        return totalComNovaDespesa;
    }

    public void setTotalComNovaDespesa(double totalComNovaDespesa) {
        this.totalComNovaDespesa = totalComNovaDespesa;
    }
}