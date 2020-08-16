package com.teste.consultar.atmconsultoria.ui.sobre;

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

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Element versao = new Element();
        versao.setTitle(getString(R.string.versao));

        View view = new AboutPage(getActivity())
                .setImage(R.drawable.logo)
                .setDescription(getString(R.string.lorem_ipsum))

                .addGroup(getString(R.string.entre_em_contato))
                .addEmail(getString(R.string.email),getString(R.string.envie_email))
                .addWebsite(getString(R.string.google),getString(R.string.acesse_site))

                .addGroup(getString(R.string.redes_sociais))
                .addFacebook(getString(R.string.usuario_redes_sociais), getString(R.string.facebbok))
                .addInstagram(getString(R.string.usuario_redes_sociais), getString(R.string.instagram))
                .addTwitter(getString(R.string.usuario_redes_sociais), getString(R.string.twitter))
                .addYoutube(getString(R.string.usuario_redes_sociais), getString(R.string.youtube))
                .addGitHub(getString(R.string.usuario_redes_sociais), getString(R.string.github))
                .addPlayStore(getString(R.string.url_playstore), getString(R.string.download_playstore))

                .addItem(versao)

                .create();



        return view;
    }
}