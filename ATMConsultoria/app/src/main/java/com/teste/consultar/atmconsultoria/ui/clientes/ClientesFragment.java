package com.teste.consultar.atmconsultoria.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.teste.consultar.atmconsultoria.R;

public class ClientesFragment extends Fragment {

    private ClientesViewModel clientesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientesViewModel =
                ViewModelProviders.of(this).get(ClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);

        return root;
    }
}