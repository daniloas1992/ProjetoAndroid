package com.teste.consultar.organize.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class ConfiguracaoFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference databaseReference;

    public static FirebaseAuth getFirebaseAuthInstance(){

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static DatabaseReference getFirebaseDatabaseInstance(){
        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

}
