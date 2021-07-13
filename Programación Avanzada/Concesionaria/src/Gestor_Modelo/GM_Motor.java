package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Motor;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class GM_Motor {
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    

    
    public GM_Motor(){
        
    }
    
    public DefaultComboBoxModel obtenerComboMotores(){
        DefaultComboBoxModel motores = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Motor.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            motores.addElement(lista.get(i));
        }
        return motores;
    }
}
