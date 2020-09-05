package com.teste.consultar.organize.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class Usuario {

    public final static String CHILD_USUARIOS_FIREBASE = "usuarios";
    public final static String CHILD_USUARIOS_DESPESATOTAL_FIREBASE = "despesaTotal";
    public final static String CHILD_USUARIOS_RECEITATOTAL_FIREBASE = "receitaTotal";
    private String nome;
    private String email;
    private String senha;
    private String idUsuario;
    private double despesaTotal;
    private double receitaTotal;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String idUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idUsuario = idUsuario;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public void salvarUsuarioFirebase() {
        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabaseInstance();
        firebaseDatabase.child(CHILD_USUARIOS_FIREBASE)
                        .child(getIdUsuario())
                        .setValue(this);
    }
}
