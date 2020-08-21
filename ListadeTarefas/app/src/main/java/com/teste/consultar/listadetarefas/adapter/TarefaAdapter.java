package com.teste.consultar.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teste.consultar.listadetarefas.R;
import com.teste.consultar.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    List<Tarefa> listaTerafas;

    public TarefaAdapter(List<Tarefa> listaTerafas) {
        this.listaTerafas = listaTerafas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter, parent, false);
        return new TarefaViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return this.listaTerafas.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        Tarefa tarefa = listaTerafas.get(position);

        holder.tvTarefa.setText(tarefa.getNomeTarefa());
    }



    public class TarefaViewHolder extends RecyclerView.ViewHolder {

        TextView tvTarefa;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTarefa = itemView.findViewById(R.id.tvTarefa);
        }
    }
}
