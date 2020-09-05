package com.teste.consultar.organize.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;
import com.teste.consultar.organize.helpers.Base64Custom;
import com.teste.consultar.organize.helpers.DateCustom;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class Movimentacao {
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private String chaveID;
    private double valor;
    public static final String CHILD_MOVIMENTACAO_FIREABSE = "movimentacao";
    public static final String TIPO_DESPESA = "D";
    public static final String TIPO_RECEITA = "R";

    public Movimentacao() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getChaveID() {
        return chaveID;
    }

    public void setChaveID(String chaveID) {
        this.chaveID = chaveID;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void salvarMovimentacao() {

        FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuthInstance();

        final String idUsuario = Base64Custom.encodeBase64(firebaseAuth.getCurrentUser().getEmail());

        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabaseInstance();
        firebaseDatabase.child(CHILD_MOVIMENTACAO_FIREABSE)
                        .child(idUsuario)
                        .child(DateCustom.getMonthYear(getData()))
                        .push()
                        .setValue(this);
    }
}
