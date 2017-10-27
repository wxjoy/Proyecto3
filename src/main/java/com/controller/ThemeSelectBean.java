/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
/**
 *
 * @author wxjoy
 */
@ManagedBean(name = ThemeSelectBean.BEAN_NAME)
@CustomScoped(value="#{window}")
public class ThemeSelectBean implements Serializable{
    public static final String  BEAN_NAME = "themeSelectBean";
    private List<String> themeList;
    
    public String getBeanName( ){ return BEAN_NAME; }
    
    private String theme;
    
    public String getTheme(){
        return theme;
    }
    
    public void setTheme(String theme){
        this.theme = theme;
    }

    public List<String> getThemeList() {
        if(themeList != null)
            return themeList;
        
        themeList = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
        
        Resource resource;
        
        for (String theme: new String[]{"rime","sam"}){
            resource = resourceHandler.createResource("themes/"+theme+"/theme.css","icefaces.ace");
            if(resource != null){
                themeList.add(theme);
            }
        }
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls  = classLoader.getResources("META-INF/resources");
            URL url;
            Matcher matcher = Pattern.compile("((jar) | (vfs)):.*/WEB-INF/lib/(ace-)?(.+?)(-\\d+(\\.\\d+){0,2}(-.+)?)?\\.jar!?/META-INF/resources/?").matcher("");
            String themen;
            while(urls.hasMoreElements()){
                url=urls.nextElement();
                if(matcher.reset(url.toString()).matches()){
                    themen = matcher.group(5);
                    url = classLoader.getResource("META-INF/resources/ace-" + theme);
                    if(url!=null){
                        themeList.add(themen);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThemeSelectBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return themeList;
    }

    public void setThemeList(List<String> themeList) {
        this.themeList = themeList;
    }
    
    
}
