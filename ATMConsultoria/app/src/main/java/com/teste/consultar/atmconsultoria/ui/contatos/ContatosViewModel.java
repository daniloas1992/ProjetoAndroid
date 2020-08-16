package com.teste.consultar.atmconsultoria.ui.contatos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContatosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContatosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is contatos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}