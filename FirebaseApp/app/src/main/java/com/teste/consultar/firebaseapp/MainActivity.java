package com.teste.consultar.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(); // getReference para começar no primeiro nó do banco
    private FirebaseAuth userAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criando usuário para acessar o banco do Firebase:
        /*userAuth.createUserWithEmailAndPassword("daniloas1992@gmail.com","1234!45@").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("CreateUser","Usuário cadastrado com sucesso!");
                } else {
                    Log.i("CreateUser","Erro ao cadastrar o usuário!");
                }
            }
        });*/


        // Verificar se o usuário está logado
        /*if(userAuth.getCurrentUser() != null) {
            Log.i("CreateUser","Usuário logado!");
        } else {
            Log.i("CreateUser","Usuário não está logado!");
        }*/

        // Fazer logout
        /*userAuth.signOut();*/

        // Fazer login
        userAuth.signInWithEmailAndPassword("daniloas1992@gmail.com","1234!45@").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("signInUser","Usuário logado com sucesso!");
                } else {
                    Log.i("signInUser","Erro ao logar o usuário!");
                }
            }
        });

        //Inserindo informações no banco do Firebase:
        DatabaseReference usuarios = dbReference.child("usuarios");
        DatabaseReference produtos = dbReference.child("produtos");

        /*
        Usuario user = new Usuario();
        user.setNome("Lidy");
        user.setSobrenome("Lima");
        user.setIdade(29);
        usuarios.child("002").setValue(user);*/

        /*Produto produto = new Produto();
        produto.setDescricao("Notebook");
        produto.setMarca("Lenovo");
        produto.setPreco(3599.99);
        produtos.child("001").setValue(produto);*/

        // Recuperando informações do banco do Firebase
        /*usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        // Inserindo usuário com identificador único
        /*Usuario user1 = new Usuario();
        user1.setNome("Pedro");
        user1.setSobrenome("Marocas");
        user1.setIdade(30);
        usuarios.push().setValue(user1);*/

        //Pesquisa com filtros: Pesquisando por id
        /*DatabaseReference usuariosPesquisa = usuarios.child("-MFJ-l7-l9IzpspU3t6A");
        usuariosPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                Log.i("FIREBASE", String.format("Nome: %s - Sobrenome: %s - Idade: %s",user.getNome(), user.getSobrenome(), user.getIdade()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        Query usuariosPesquisa2 = usuarios.orderByChild("nome").equalTo("Danilo"); //Pesquisa com filtros: Pesquisando por termo
        Query usuariosPesquisa3 = usuarios.orderByKey().limitToFirst(2); //Pesquisa com filtros: Buscar uma quantidade de registro por vez
        Query usuariosPesquisa4 = usuarios.orderByChild("idade").startAt(30); //Pesquisa com filtros: Valor maior ou igual
        Query usuariosPesquisa5 = usuarios.orderByChild("idade").endAt(28); //Pesquisa com filtros: Valor menor ou igual
        Query usuariosPesquisa6 = usuarios.orderByChild("idade").startAt(20).endAt(28); //Pesquisa com filtros: Entre dois valores
        Query usuariosPesquisa7 = usuarios.orderByChild("nome").startAt("Da").endAt("Do" + "\uf8ff"); //Pesquisa com filtros: Termo inicial e final


        usuariosPesquisa7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}