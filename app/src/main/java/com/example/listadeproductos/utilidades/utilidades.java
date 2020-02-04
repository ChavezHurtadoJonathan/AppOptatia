package com.example.listadeproductos.utilidades;

public class utilidades {

    public static final String tabla_producto = "productos";
    public static final String campo_id = "id";
    public static final String campo_nombre = "nombre";
    public static final String campo_precio = "precio";

    public static final String crear_tabla = "CREATE TABLE "+ tabla_producto+ "("+campo_id+" INTEGER, "+campo_nombre+" TEXT, "+campo_precio+" TEXT)";
}
