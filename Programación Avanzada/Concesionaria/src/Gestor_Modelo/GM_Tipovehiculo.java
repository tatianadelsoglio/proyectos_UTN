package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Tipovehiculo;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Session;


public class GM_Tipovehiculo {
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    
    public GM_Tipovehiculo(){
        
    }
    
   public DefaultComboBoxModel obtenerComboTipoVehiculos(){
        DefaultComboBoxModel tvehiculos = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Tipovehiculo.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            tvehiculos.addElement(lista.get(i));
        }
        return tvehiculos;
    }
}
