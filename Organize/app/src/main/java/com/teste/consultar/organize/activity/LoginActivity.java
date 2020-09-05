package com.teste.consultar.organize.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.teste.consultar.organize.R;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;
import com.teste.consultar.organize.model.Usuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginSenha;
    private Button btnLoginEntrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginSenha = findViewById(R.id.edtLoginSenha);
        btnLoginEntrar = findViewById(R.id.btnLoginEntrar);
        btnLoginEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginEntrar:
                final String txtEmail = edtLoginEmail.getText().toString();
                final String txtSenha = edtLoginSenha.getText().toString();
                
                if(txtEmail.isEmpty()){
                    Toast.makeText(this, "Insira o E-mail!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtSenha.isEmpty()){
                    Toast.makeText(this, "Insira a Senha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario(txtEmail, txtSenha);
                loginUsuario(usuario);

                break;
        }
    }

    private void loginUsuario(final Usuario usuario) {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuthInstance();

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "O E-mail informado não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "E-mail ou senha inválida!";
                    } catch (Exception e) {
                        exception = "Falha ao cadastrar usuário: "+e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}