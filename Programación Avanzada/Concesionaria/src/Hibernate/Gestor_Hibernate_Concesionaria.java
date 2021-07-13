/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hibernate;

import java.awt.Component;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Germán
 */
public class Gestor_Hibernate_Concesionaria {
    private static Gestor_Hibernate_Concesionaria instanciaUnica;
    static Session session=null;
    static Transaction tx = null;
    private static HibernateUtil_Concesionaria unica;
    
    
    public static Gestor_Hibernate_Concesionaria getInstancia(){ 
        if(instanciaUnica==null){ //Solo se ejecuta una vez
            instanciaUnica=new Gestor_Hibernate_Concesionaria();
            instanciaUnica.abrirsession();
        }
        return instanciaUnica;
    }
    
    public static HibernateUtil_Concesionaria getInstancia2(){
        if(unica==null){ //Solo se ejecuta una vez
            unica=new HibernateUtil_Concesionaria();
            unica.inicializar();
        }
        return unica;
    }
    
    public void abrirsession(){
        this.setSession(HibernateUtil_Concesionaria.getSession());
        
            
        this.setTx(session.beginTransaction());
        
    } 
    
    public void cerrarsession(){
        this.getSession().close();
        this.setSession(null);
        this.setTx(null);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        Gestor_Hibernate_Concesionaria.session = session;
    }

    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        Gestor_Hibernate_Concesionaria.tx = tx;
    }
    
    public void eliminarObjeto(Object objeto){
         try{   
            Session s = HibernateUtil_Concesionaria.getSession();
            Transaction tx = s.beginTransaction();
            s.delete(objeto);        
            tx.commit();
               } catch(Exception ex){
                System.out.println("Repositorio.eliminarObjeto(Object objeto)"+ex);
            ex.printStackTrace();
                JOptionPane.showMessageDialog((Component)null,"El elemento no se puede eliminar:"+ex.getMessage(),"Error",0);
                getTx().rollback();
            }
    }
    
    public void guardarObjeto(Object objeto){
        try{
        Session s = HibernateUtil_Concesionaria.getSession();
        Transaction tx = s.beginTransaction();
        s.save(objeto);        
        tx.commit();
        
        
         System.out.println(" guardaractualizarObjeto() " +objeto.getClass()+": "+objeto.toString());  
        } catch(Exception ex){
            System.out.println("error "+ex);
            System.out.println("Repositorio.guardarObjeto(Object objeto)"+objeto.getClass()+": "+objeto.toString()+ex);
            ex.printStackTrace();
            getTx().rollback();
        }
    }
    
    
}
