/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author User
 */
@ManagedBean(name=RegistroBean.BEAN_NAME)
@CustomScoped(value="#{window}")
public class RegistroBean {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_49425/WebService.asmx.wsdl")
    private WebService service;
    public static final String BEAN_NAME="registroBean";
    private String usuario;
    private String password;
    private FacesContext fc;
    
    public RegistroBean(){
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void btnRegistrar(){
        boolean result = false;
        
        try { // Call Web Service Operation
            WebServiceSoap port = service.getWebServiceSoap();
            // TODO initialize WS operation arguments here
            java.lang.String usr = usuario;
            java.lang.String pass = password;
            // TODO process result here
            result = port.crear(usr, pass);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        if(result){
            redireccionar("/Proyecto3/faces/Login.xhtml");
        }else{
            
        }
    }
    
    private void redireccionar(String direccion){
        fc = FacesContext.getCurrentInstance();
        ExternalContext ec;
        try{
            ec = fc.getExternalContext();
            ec.redirect(direccion);
        } catch (IOException ex) {
            Logger.getLogger(RegistroBean.class.getName()).log(Level.SEVERE, null, ex);
            usuario="algo";
        }
    }
}
