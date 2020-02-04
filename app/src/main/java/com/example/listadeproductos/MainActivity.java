package com.example.listadeproductos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listadeproductos.entidades.productos;
import com.example.listadeproductos.utilidades.utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user =(EditText)findViewById(R.id.usuario);
        pass=(EditText)findViewById(R.id.contras);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(user.getText().toString(),pass.getText().toString());
            }
        });
    }
    private void check(String userName, String userPassword){
        //Definiendo usuario y contraseña para el acceso
        if ((userName.equals("Admin"))&&(userPassword.equals("123"))){
            Intent intent=new Intent(MainActivity.this, activitiuno.class);
            startActivity(intent);
        }
    }
}