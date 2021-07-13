/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Modelo;

import Hibernate.Gestor_Hibernate_Concesionaria;
import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Modelo;
import Modelo.Version;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Germán
 */
public class GM_Version {
//    private final Gestor_Hibernate_Concesionaria gestorHibernate;
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    
    
    public GM_Version(){
        
    }
    
    public DefaultComboBoxModel obtenerComboVersionXmodelo(Modelo modelo){
        DefaultComboBoxModel versionesXmodelo = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Version.class);
        crit.add(Restrictions.eq("modelo",modelo));
        
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            versionesXmodelo.addElement(lista.get(i));
        }
        return versionesXmodelo;
    }
}
