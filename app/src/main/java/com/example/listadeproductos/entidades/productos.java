package com.example.listadeproductos.entidades;

import android.content.Intent;
import android.telephony.mbms.StreamingServiceInfo;

public class productos {
private String id;
private String nombre;
private String precio;

    public productos() {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
