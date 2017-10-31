/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.modelos.Producto;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.net.ssl.HttpsURLConnection;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;

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
    private final String USER_AGENT = "Mozilla/5.0";

    public ProductosBean(){
        productos = new ArrayList<>();
        //esto es solo para prueba de visualizacion
        productos.add(new Producto(1,"Pizza","pizza grande de peperonni",90.50));
        productos.add(new Producto(2,"Pizza","pizza grande de jamon",90.50));
        productos.add(new Producto(3,"Pizza","pizza grande de piña",90.50));
        productos.add(new Producto(4,"Pizza","pizza grande de salami",90.50));
        productos.add(new Producto(5,"Pizza","pizza grande de peperonni y jamon",90.50));
        //estos datos deben de recuperarse del ws
        categorias = new LinkedList<>();
        categorias.add(new SelectItem(-1, "Todos"));
        categorias.add(new SelectItem(-2, "Ofertas"));
        categorias.add(new SelectItem(-3, "Nuevos"));
        categorias.add(new SelectItem(-4, "Top"));
        categorias.add(new SelectItem(1,"Categoria"));
        //hasta aca hay que modificar
        codCategoria = -1;
        visible=false;
    }
    
    public ArrayList<Producto> getProductos(){
        return productos;
    }
    
    public void setProductos(ArrayList<Producto> productos){
        this.productos = productos;
    }
    
    public void btnCalificar(){
        visible=false;
        //System.out.println("EL BOTON DE CALIFICAR!!!!"+ this.producto.getCodigo()+ "..."+this.producto.getPuntuacion());
        
        try {
        
            //productos.add(new Producto(0,"error",msg,0.0));
            String url = "http://laboratoriovirtual.net:8000/api/producto/puntuar";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "codigo="+this.producto.getCodigo()+"&puntuacion="+this.producto.getPuntuacion();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
            
	} catch (Exception e) {
	    System.out.println(e);
            productos.add(new Producto(0,"error",e.toString(),0.0));
	}
        
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
                break;
            case -2:
                System.out.println("\tOfertas");
                verFecha = false;
                verTop = false;
                break;
            case -3:
                System.out.println("\tNuevos");
                verFecha = false;
                verTop = false;
                break;
            case -4:
                System.out.println("\tTop N");
                verFecha = true;
                verTop = true;
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
    }
    
}
