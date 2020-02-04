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

public class activitiuno extends AppCompatActivity {

    Button mostrar_lista;

    //Conexion con la base de datos
    conexion x = new conexion(this, utilidades.tabla_producto,null,1);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.mostrar_lista:
                Intent m_lista = new Intent(activitiuno.this, lista_productos.class);
                startActivity(m_lista);
                break;

            case R.id.ing_prod:

                final Dialog ingresar = new Dialog(activitiuno.this);
                ingresar.setContentView(R.layout.activity_insertar);
                final EditText ing_id = (EditText)ingresar.findViewById(R.id.insertar_id);
                final EditText ing_prod = (EditText)ingresar.findViewById(R.id.insertar_prod);
                final EditText ing_precio = (EditText)ingresar.findViewById(R.id.insertar_precio);
                Button btn_insert = (Button)ingresar.findViewById(R.id.btn_insertar);
                btn_insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = x.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(utilidades.campo_id,ing_id.getText().toString());
                        values.put(utilidades.campo_nombre,ing_prod.getText().toString());
                        values.put(utilidades.campo_precio,ing_precio.getText().toString());
                        db.insert(utilidades.tabla_producto, utilidades.campo_id, values);
                        Toast.makeText(activitiuno.this, "Producto Ingresado", Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                });
                ingresar.show();
                break;

            case R.id.mod_prod:
                Dialog modificar_d= new Dialog(activitiuno.this);
                modificar_d.setContentView(R.layout.modificar);
                final EditText id_p = (EditText)modificar_d.findViewById(R.id.id_producto);
                final EditText desc = (EditText)modificar_d.findViewById(R.id.descripcion);
                final EditText pre = (EditText)modificar_d.findViewById(R.id.precio_pro);
                Button busca = (Button)modificar_d.findViewById(R.id.buscar);
                Button mod = (Button)modificar_d.findViewById(R.id.btn_modificar);

                busca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = x.getReadableDatabase();
                        String[] parametros = {id_p.getText().toString()};

                        try {
                            //CONSTRUIMOS LA ESTRUCTURA PARA PODER ENVIAR LA INFORMACION A NUESTRA BASE DE DATOS
                            Cursor cursor=db.rawQuery("SELECT "+ utilidades.campo_nombre+","+utilidades.campo_precio+" FROM "+utilidades.tabla_producto+" WHERE "+utilidades.campo_id+"=? ",parametros);
                            cursor.moveToFirst();
                            desc.setText(cursor.getString(0));
                            pre.setText(cursor.getString(1));
                        }catch (Exception e){
                            Toast.makeText(activitiuno.this, "id invalido",Toast.LENGTH_SHORT).show();
                            desc.setText("");
                            pre.setText("");
                        }

                    }
                });

                mod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = x.getWritableDatabase();
                        String[] parametros = {id_p.getText().toString()};
                        ContentValues values = new ContentValues();
                        values.put(utilidades.campo_nombre, desc.getText().toString());
                        values.put(utilidades.campo_precio, pre.getText().toString());
                        db.update(utilidades.tabla_producto,values,utilidades.campo_id+"=?",parametros);
                        Toast.makeText(activitiuno.this, "Datos del producto actualizados", Toast.LENGTH_SHORT).show();
                        db.close();

                    }
                });
                modificar_d.show();
                break;

            case R.id.elim_prod:
                Dialog eliminar_d = new Dialog(activitiuno.this);
                eliminar_d.setContentView(R.layout.eliminar);
                final EditText id_pr =(EditText)eliminar_d.findViewById(R.id.id_pro);
                final EditText desc_p = (EditText)eliminar_d.findViewById(R.id.descripcion_pro);
                final EditText pre_pro = (EditText)eliminar_d.findViewById(R.id.precio_prod);
                Button bus = (Button)eliminar_d.findViewById(R.id.buscar_pro);
                Button elim = (Button)eliminar_d.findViewById(R.id.btn_eliminar);

                bus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = x.getReadableDatabase();
                        String[] parametros = {id_pr.getText().toString()};

                        try {
                            //CONSTRUIMOS LA ESTRUCTURA PARA PODER ENVIAR LA INFORMACION A NUESTRA BASE DE DATOS
                            Cursor cursor=db.rawQuery("SELECT "+ utilidades.campo_nombre+","+utilidades.campo_precio+" FROM "+utilidades.tabla_producto+" WHERE "+utilidades.campo_id+"=? ",parametros);
                            cursor.moveToFirst();
                            desc_p.setText(cursor.getString(0));
                            pre_pro.setText(cursor.getString(1));
                        }catch (Exception e){
                            Toast.makeText(activitiuno.this, "id invalido",Toast.LENGTH_SHORT).show();
                            desc_p.setText("");
                            pre_pro.setText("");
                        }
                    }
                });

                elim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = x.getWritableDatabase();
                        String[] parametros = {id_pr.getText().toString()};
                        db.delete(utilidades.tabla_producto,utilidades.campo_id+"=?",parametros);
                        Toast.makeText(activitiuno.this, "Producto Borrado de la Lista", Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                });

                eliminar_d.show();
                break;



        }
        return true;
    }

}
