/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.modelos.Producto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;
import org.icefaces.ace.json.JSONArray;
import org.icefaces.ace.json.JSONObject;

/**
 *
 * @author wxjoy
 */

@ManagedBean(name=ProductosBean.BEAN_NAME)
@CustomScoped(value="#{window}")
public class ProductosBean implements Serializable{
    public static final String BEAN_NAME="productosBean";
    private ArrayList<Producto> productos;
    private List<SelectItem> categorias;
    private boolean visible;
    private boolean verTop;
    private boolean verFecha;
    private Producto producto;
    private int codCategoria;
    private int topN;
    private Date fInicio;
    private Date fFin;
    
    public ProductosBean(){
        productos = new ArrayList<>();
        //esto es solo para prueba de visualizacion
        /*productos.add(new Producto(1,"Pizza","pizza grande de peperonni",90.50));
        productos.add(new Producto(2,"Pizza","pizza grande de jamon",90.50));
        productos.add(new Producto(3,"Pizza","pizza grande de piña",90.50));
        productos.add(new Producto(4,"Pizza","pizza grande de salami",90.50));
        productos.add(new Producto(5,"Pizza","pizza grande de peperonni y jamon",90.50));*/
        //estos datos deben de recuperarse del ws
        categorias = new LinkedList<>();
        categorias.add(new SelectItem(-1,"algo"));
        
        
        categorias = new LinkedList<>();
        categorias.add(new SelectItem(-1, "Todos"));
        categorias.add(new SelectItem(-2, "Ofertas"));
        categorias.add(new SelectItem(-3, "Nuevos"));
        categorias.add(new SelectItem(-4, "Top"));
        categorias.add(new SelectItem(1,"Categoria"));
        //hasta aca hay que modificar*/
        RequestJson("{\"categoria\": -1,\"mas_vendidos\": 20,\"fecha_inicio\": \"01-01-2017\",\"fecha_fin\": \"20-12-2017\",\"producto_ofertado\": 1}");
        codCategoria = -1;
        visible=false;
    }
    //Metodo que hace la request para productos
    private void RequestJson(String msg){
        productos.clear();
        try {
            //productos.add(new Producto(0,"error",msg,0.0));
            URL url = new URL("http://laboratoriovirtual.net:8000/api/productos");//direccion del servicio
            URLConnection connection = url.openConnection();
	    connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setConnectTimeout(5000);
	    connection.setReadTimeout(5000);
	    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	    out.write(msg);//el json con parametros
	    out.close();
 
	   BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           String js="";
           String linea = in.readLine();
	   while (linea != null) {
               js+=linea;
               linea = in.readLine();
	   }
           JSONObject respuesta = new JSONObject(js);
           JSONArray arr = respuesta.getJSONArray("productos");//nombre del parametro que necesito obtener
           for(int i=0;i<arr.length();i++){                    //devuelve un array q recorro
               productos.add(new Producto(i,arr.getJSONObject(i).getString("nombre"),arr.getJSONObject(i).getString("descripcion"),Float.parseFloat(arr.getJSONObject(i).getString("precio"))));
           }
	   in.close();
	} catch (Exception e) {
	    System.out.println(e);
            productos.add(new Producto(0,"error",e.toString(),0.0));
	}
    }
    
    public ArrayList<Producto> getProductos(){
        return productos;
    }
    
    public void setProductos(ArrayList<Producto> productos){
        this.productos = productos;
    }
    
    public void btnCalificar(){
        visible=false;
        //operacion de calificar
    }
    
    public void btnCerrar(){
        visible=false;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public void selectItem(SelectEvent event){
        visible = true;
        producto = (Producto)event.getObject();
        
    }
    
    public void unselectItem(UnselectEvent event){
        visible=false;
    }
    
    public int getCodCategoria(){
        return codCategoria;
    }
    
    public void setCodCategoria(int codCategoria){
        this.codCategoria = codCategoria;
    }

    public boolean isVerTop() {
        return verTop;
    }

    public void setVerTop(boolean verTop) {
        this.verTop = verTop;
    }

    public boolean isVerFecha() {
        return verFecha;
    }

    public void setVerFecha(boolean verFecha) {
        this.verFecha = verFecha;
    }
    
    public void seleccionaCategoria(ValueChangeEvent e){
        if(e.getNewValue() == null)
            return;
        
        codCategoria = (int) e.getNewValue();
        
        System.out.println("Cambio de categoria");
        switch(codCategoria){
            case -1:
                System.out.println("\tTodos");
                verFecha = false;
                verTop = false;
                RequestJson("{\"categoria\": -1,\"mas_vendidos\": 20,\"fecha_inicio\": \"01-01-2017\",\"fecha_fin\": \"20-12-2017\",\"producto_ofertado\": 1}");
                break;
            case -2:
                System.out.println("\tOfertas");
                verFecha = false;
                verTop = false;
                RequestJson("{\"categoria\": -1,\"mas_vendidos\": 20,\"fecha_inicio\": \"01-01-2017\",\"fecha_fin\": \"20-12-2017\",\"producto_ofertado\": 20}");
                break;
            case -3:
                System.out.println("\tNuevos");
                verFecha = false;
                verTop = false;
                RequestJson("{\"categoria\": -1,\"mas_vendidos\": 20,\"fecha_inicio\": \"01-10-2017\",\"fecha_fin\": \"20-12-2017\",\"producto_ofertado\": 1}");
                break;
            case -4:
                System.out.println("\tTop N");
                verFecha = true;
                verTop = true;
                productos.clear();
                break;
            default:
                System.out.println("\tEl resto de categorias");
                verFecha = false;
                verTop = false;
                break;
        }
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    public Date getfInicio() {
        return fInicio;
    }

    public void setfInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public Date getfFin() {
        return fFin;
    }

    public void setfFin(Date fFin) {
        this.fFin = fFin;
    }
    
    public void btnConsultar(){
        //validar opciones...
        Calendar calI = Calendar.getInstance();
        calI.setTime(fInicio);
        Calendar calF = Calendar.getInstance();
        calF.setTime(fFin);
        RequestJson("{\"categoria\": -1,\"mas_vendidos\":"+topN+",\"fecha_inicio\": \""+
                calI.get(Calendar.DAY_OF_MONTH)+"-"+(calI.get(Calendar.MONTH)+1)+"-"
                        +calI.get(Calendar.YEAR)+
                "\",\"fecha_fin\": \""+
                calF.get(Calendar.DAY_OF_MONTH)+"-"+(calF.get(Calendar.MONTH)+1)+"-"
                        +calF.get(Calendar.YEAR)+"\",\"producto_ofertado\": 1}");
    }
    
}
