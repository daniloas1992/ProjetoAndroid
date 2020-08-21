package com.teste.consultar.listadetarefas.helper;

import com.teste.consultar.listadetarefas.model.Tarefa;

import java.util.List;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public interface ITarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
