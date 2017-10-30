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

/**
 *
 * @author wxjoy
 */
@ManagedBean(name=LoginBean.BEAN_NAME)
@CustomScoped(value="#{window}")
public class LoginBean {
    public static final String BEAN_NAME="loginBean";
    private String usuario;
    private String password;
    private FacesContext fc;
    
    public LoginBean(){}

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
    
    public void btnIngresar(){
        /**
         * Tenes que hacer la validacion para guardar atributos en la sesion
         * en este caso el atributo que indica que la sesion esta iniciada se llama
         * "codPersona"
         * de momento para poder seguir probando le voy a meter el correo....
         */
        fc = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) fc.getExternalContext().getSession(true);
        sesion.setAttribute("codPersona", usuario);
        /**en caso que los datos sean validos redirecciono*/
        redireccionar("/Proyecto3/faces/productos.xhtml");
    }
    
    public void btnRegistro(){
        redireccionar("/Proyecto3/faces/Registro.xhtml");
    }
    
    private void redireccionar(String direccion){
        fc = FacesContext.getCurrentInstance();
        ExternalContext ec;
        try{
            ec = fc.getExternalContext();
            ec.redirect(direccion);
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
