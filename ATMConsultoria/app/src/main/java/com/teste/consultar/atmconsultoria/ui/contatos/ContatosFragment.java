package com.teste.consultar.atmconsultoria.ui.contatos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teste.consultar.atmconsultoria.R;
import com.teste.consultar.atmconsultoria.ui.sevicos.ServicosViewModel;


public class ContatosFragment extends Fragment {

    private ContatosViewModel contatosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contatosViewModel =
                ViewModelProviders.of(this).get(ContatosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contatos, container, false);
        final TextView textView = root.findViewById(R.id.text_contatos);
        contatosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}