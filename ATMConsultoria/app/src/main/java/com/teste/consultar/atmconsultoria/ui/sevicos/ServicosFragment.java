package com.teste.consultar.atmconsultoria.ui.sevicos;

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

public class ServicosFragment extends Fragment {

    private ServicosViewModel servicosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        servicosViewModel = ViewModelProviders.of(this).get(ServicosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_servicos, container, false);

        return root;
    }
}