/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modelos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author wxjoy
 */
public class Producto implements Serializable {
    private String nombre;
    private int codigo;
    private String descripcion;
    private double precio;
    private double promPuntuacion;
    private int puntuacion;
    private double precioOferta;
    private Date fechaEntrada;
    private int categoria;
    
    public Producto(){
        
    }
    
    public Producto(int codigo, String nombre, String descripcion, double precio){
        this.codigo=codigo;
        this.nombre= nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPromPuntuacion() {
        return promPuntuacion;
    }

    public void setPromPuntuacion(double promPuntuacion) {
        this.promPuntuacion = promPuntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public double getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(double precioOferta) {
        this.precioOferta = precioOferta;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
    
}
