package Gestor_Modelo;

import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Tipocombustible;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class GM_Tipocombustible {
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    
    
    public GM_Tipocombustible(){
        
    }
    
    public DefaultComboBoxModel obtenerComboCombustible(){
        DefaultComboBoxModel combustibles = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Tipocombustible.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            combustibles.addElement(lista.get(i));
        }
        return combustibles;
    }
}
