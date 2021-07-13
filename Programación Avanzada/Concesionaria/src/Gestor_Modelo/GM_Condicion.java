/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Modelo;

import Hibernate.Gestor_Hibernate_Concesionaria;
import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Condicion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Germán
 */
public class GM_Condicion {
//    private final Gestor_Hibernate_Concesionaria gestorHibernate;
    private final HibernateUtil_Concesionaria gestorHibernate = null;

    
    public GM_Condicion(){
        
    }
    
    
    public DefaultComboBoxModel obtenerComboCondiciones(){
        DefaultComboBoxModel condicion = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Condicion.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            condicion.addElement(lista.get(i));
        }
        return condicion;
    }
}
