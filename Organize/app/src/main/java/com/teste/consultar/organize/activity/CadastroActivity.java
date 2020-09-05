package com.teste.consultar.organize.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.teste.consultar.organize.R;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;
import com.teste.consultar.organize.helpers.Base64Custom;
import com.teste.consultar.organize.model.Usuario;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCadastroNome, edtCadastroEmail, edtCadastroSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadastroNome = findViewById(R.id.edtCadastroNome);
        edtCadastroEmail = findViewById(R.id.edtCadastroEmail);
        edtCadastroSenha = findViewById(R.id.edtCadastroSenha);
        btnCadastrar = findViewById(R.id.btnCadastrarUsuario);
        btnCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCadastrarUsuario:
                final String txtNome = edtCadastroNome.getText().toString();
                final String txtEmail = edtCadastroEmail.getText().toString();
                final String txtSenha = edtCadastroSenha.getText().toString();

                if(txtNome.isEmpty()){
                    Toast.makeText(this, "Insira o nome!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtEmail.isEmpty()){
                    Toast.makeText(this, "Insira o E-mail!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtSenha.isEmpty()){
                    Toast.makeText(this, "Insira a Senha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario(txtNome, txtEmail, txtSenha);
                cadastrarUsuario(usuario);

                break;
        }
    }

    private void cadastrarUsuario(final Usuario usuario) {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuthInstance();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final String idUsuario = Base64Custom.encodeBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvarUsuarioFirebase();
                    Toast.makeText(getApplicationContext(), "Usuario cadastrado!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String exception = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Formato de e-mail inválido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "Este e-mail já foi cadastrado!";
                    } catch (Exception e) {
                        exception = "Falha ao cadastrar usuário: "+e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}