package com.teste.consultar.listadetarefas.model;

import java.io.Serializable;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class Tarefa implements Serializable {

    private Long id;
    private String nomeTarefa;

    public Tarefa(Long id, String nomeTarefa) {
        this.id = id;
        this.nomeTarefa = nomeTarefa;
    }

    public Tarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }
}
