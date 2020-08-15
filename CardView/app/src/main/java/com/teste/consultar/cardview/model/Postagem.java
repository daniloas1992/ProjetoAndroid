package com.teste.consultar.cardview.model;

import com.teste.consultar.cardview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class Postagem {

    private String nomeAutor;
    private String mensagem;
    private int imagem;

    public Postagem() {
    }

    public Postagem(String nomeAutor, String mensagem, int imagem) {
        this.nomeAutor = nomeAutor;
        this.mensagem = mensagem;
        this.imagem = imagem;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public static List<Postagem> getPostagemList() {
        List<Postagem> listaPostagem = new ArrayList<Postagem>();

        listaPostagem.add(new Postagem("Danilo Alves","Postagem de Danilo", R.drawable.imagem1));
        listaPostagem.add(new Postagem("Lidyane Lima","Postagem de Lidyane", R.drawable.imagem2));
        listaPostagem.add(new Postagem("Jefferson Carneiro","Postagem de Jefferson", R.drawable.imagem3));
        listaPostagem.add(new Postagem("Cynthia","Postagem de Cynthia", R.drawable.imagem4));

        return listaPostagem;
    }
}
