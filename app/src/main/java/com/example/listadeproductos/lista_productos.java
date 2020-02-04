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
import java.util.List;

public class lista_productos extends AppCompatActivity {

    ListView list_i, list_descrip, list_prec;

    ArrayList<String> listaInformacionId, listaInformacionDesc, listaInformacionPrecio;
    ArrayList<productos> listaId, lista_desc, listaPrecio;

    // INICIO LA CONEXION CON LA BASE DE DATOS
    conexion x = new conexion(this, utilidades.tabla_producto,null,1);



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_1, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.actualizar:
                Intent m_lista = new Intent(lista_productos.this, lista_productos.class);
                startActivity(m_lista);
                break;


            case R.id.ing_prod:

                Dialog ingresar = new Dialog(lista_productos.this);
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
                        Toast.makeText(lista_productos.this, "Producto Ingresado", Toast.LENGTH_SHORT).show();
                        db.close();

                    }
                });
                ingresar.show();
                break;

            case R.id.mod_prod:
                Dialog modificar_d= new Dialog(lista_productos.this);
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
                            Toast.makeText(lista_productos.this, "id invalido",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(lista_productos.this, "Datos del producto actualizados", Toast.LENGTH_SHORT).show();
                        db.close();

                    }
                });
                modificar_d.show();
                break;

            case R.id.elim_prod:
                Dialog eliminar_d = new Dialog(lista_productos.this);
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
                            Toast.makeText(lista_productos.this, "id invalido",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(lista_productos.this, "Producto Borrado de la Lista", Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                });

                eliminar_d.show();
                break;



        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        list_i = (ListView)findViewById(R.id.list_id);
        list_descrip = (ListView)findViewById(R.id.list_descripcion);
        list_prec = (ListView) findViewById(R.id.list_precio);

        consultarListaId();
        consultarListaDescripcion();
        consultarListaPrecio();

        ArrayAdapter adaptadorId = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionId);
        list_i.setAdapter(adaptadorId);

        ArrayAdapter adapterdescripcion = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionDesc);
        list_descrip.setAdapter(adapterdescripcion);

        ArrayAdapter adapterPrecio = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionPrecio);
        list_prec.setAdapter(adapterPrecio);
    }



    private void consultarListaId() {
        SQLiteDatabase db = x.getReadableDatabase();
        productos productoId = null;
        listaId = new ArrayList<productos>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + utilidades.tabla_producto,null);

        while (cursor.moveToNext()){
            productoId = new productos();
            productoId.setId(String.valueOf(cursor.getInt(0)));
            //producto.setNombre(cursor.getString(1));
            //producto.setPrecio(Integer.valueOf(cursor.getString(2)));

            listaId.add(productoId);
        }
        obtenerListaId();
    }

    private void obtenerListaId() {
        listaInformacionId = new ArrayList<String>();
        for (int i=0; i<listaId.size(); i++){
            listaInformacionId.add(listaId.get(i).getId());
        }
    }

    private void consultarListaDescripcion() {
        SQLiteDatabase db = x.getReadableDatabase();
        productos productoDesc = null;
        lista_desc = new ArrayList<productos>();
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + utilidades.tabla_producto,null);
        while (cursor1.moveToNext()){
            productoDesc = new productos();
            productoDesc.setNombre(cursor1.getString(1));
            lista_desc.add(productoDesc);
        }
        obtenerListaDesc();
    }

    private void obtenerListaDesc() {
        listaInformacionDesc = new ArrayList<String>();
        for (int g=0; g<lista_desc.size(); g++){
            listaInformacionDesc.add(lista_desc.get(g).getNombre());
        }
    }

    private void consultarListaPrecio() {
        SQLiteDatabase db = x.getReadableDatabase();
        productos productoPrecio = null;
        listaPrecio = new ArrayList<productos>();
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + utilidades.tabla_producto,null);
        while (cursor2.moveToNext()){
            productoPrecio = new productos();
            productoPrecio.setPrecio(cursor2.getString(2));
            listaPrecio.add(productoPrecio);
        }
        obtenerPrecios();
    }

    private void obtenerPrecios() {
        listaInformacionPrecio = new ArrayList<String>();
        for (int o=0; o<listaPrecio.size(); o++){
            listaInformacionPrecio.add(listaPrecio.get(o).getPrecio());
        }
    }

}
