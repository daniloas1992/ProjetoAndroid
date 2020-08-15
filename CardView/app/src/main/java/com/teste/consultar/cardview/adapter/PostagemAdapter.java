package com.teste.consultar.cardview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teste.consultar.cardview.R;
import com.teste.consultar.cardview.model.Postagem;

import java.util.List;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class PostagemAdapter extends RecyclerView.Adapter<PostagemAdapter.MyViewHolder> {

    private List<Postagem> postagens;

    public PostagemAdapter(List<Postagem> postagens) {
        this.postagens = postagens;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter_postagem, parent, false);
        return new MyViewHolder((itemLista));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Postagem postagem = postagens.get(position);
        holder.tvAutor.setText(postagem.getNomeAutor());
        holder.tvMensagem.setText(postagem.getMensagem());
        holder.imageViewPostagem.setImageResource(postagem.getImagem());
    }

    @Override
    public int getItemCount() {
        return postagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAutor;
        private TextView tvMensagem;
        private ImageView imageViewPostagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvMensagem = itemView.findViewById(R.id.tvMensagem);
            imageViewPostagem = itemView.findViewById(R.id.imageViewPostagem);

        }
    }
}
