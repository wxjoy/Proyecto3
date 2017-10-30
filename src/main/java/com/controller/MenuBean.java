/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.CustomScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wxjoy
 */
@ManagedBean(name=MenuBean.BEAN_NAME)
@CustomScoped(value="#{window}")
public class MenuBean {
    public static final String BEAN_NAME="menuBean";
    public MenuBean(){
        
    }
    
    public void menuTop(){
        redireccionar("/Proyecto3/faces/top.xhtml");
    }
    
    public void menuProducto(){
        redireccionar("/Proyecto3/faces/productos.xhtml");
    }
    
    public void menuLogout(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)fc.getExternalContext().getSession(false);
        sesion.invalidate();
        fc.getExternalContext().getSession(true);
        redireccionar("/Proyecto3/faces/Login.xhtml");
    }
    
    public void redireccionar(String dir){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec;
        try{
            ec = fc.getExternalContext();
            ec.redirect(dir);
        }catch(IOException ex){
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
}
