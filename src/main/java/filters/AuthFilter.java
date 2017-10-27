/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wxjoy
 */
@WebFilter(filterName="AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter, Serializable{
    public AuthFilter(){
        
    }
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        try{
            HttpServletRequest req = (HttpServletRequest)sr;
            HttpServletResponse res = (HttpServletResponse) sr1;
            HttpSession ses = req.getSession(false);
            String reqURI= req.getRequestURI();
            if(reqURI.contains("/Login.xhtml") || (ses != null && ses.getAttribute("codPersona") != null)
                    || reqURI.contains("javax.faces.resource") || reqURI.contains("/Registro.xhtml")) {
                fc.doFilter(sr, sr1);
            }else{
                res.sendRedirect(req.getContextPath() + "/faces/Login.xhtml");
            }
        }catch(Throwable t){
            System.out.println(t.getMessage());
        }
    }

    @Override
    public void destroy() {
        
    }
    
}
