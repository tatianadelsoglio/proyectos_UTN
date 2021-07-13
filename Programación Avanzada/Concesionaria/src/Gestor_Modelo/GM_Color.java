/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Color;
import Modelo.Vehiculo;
import Vista.GV;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Germán
 */
public class GM_Color extends GV {
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    private Color modelo;

    
    public GM_Color(){

    }
    
    public Color getModelo() {
        return modelo;
    }

    public void setModelo(Color modelo) {
        this.modelo = modelo;
    }
    
    public Color crearModeloColor(){
        this.setModelo(new Color());
        return this.getModelo();
    }
    
    public void guardarObjetoColorDB(Color color){
        Session s = gestorHibernate.getSession();
        Transaction tx = s.beginTransaction();
        s.save(color);        
        tx.commit();
    }
    
    public ArrayList<Color> buscarObjetoColorDB(){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Color.class);

        ArrayList<Color> colores = (ArrayList<Color>) crit.list();
        return colores;
    }
    
    public ArrayList<Color> buscarObjetoColorDB(String dato){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Color.class);
        
        Criterion rest1 = Restrictions.like("nombre", "%" + dato + "%");
        Criterion rest2 = Restrictions.like("codigoPintura", "%" + dato + "%");
        
        crit.add(Restrictions.or(rest1,rest2));
        ArrayList<Color> colores = (ArrayList<Color>) crit.list();
        return colores;
    }
        
    public void borrarObjetoColorDB(Long id){
        Session s = gestorHibernate.getSession();
        Color c = (Color)s.get(Color.class, id);
        Transaction tx = s.beginTransaction();
        s.delete(c);        
        tx.commit();
    }
    
    public boolean sePuedeBorrar(long id){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Vehiculo.class);
        crit.add(Restrictions.like("color.id",id));
        
        if (crit.list().size()==0){
            return true;
        }else{
            return false;
        }
    }
    
    public void actualizarObjetoColorDB(Color color){
        Session s = gestorHibernate.getSession();
        Color c = (Color) s.get(Color.class,color.getId());        
        c.setNombre(color.getNombre());
        c.setCodigoPintura(color.getCodigoPintura());
        
        Transaction tx = s.beginTransaction();
        s.update(c);        
        tx.commit();
    }
    
    public DefaultComboBoxModel obtenerComboColores(){
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Color.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            modelo.addElement(lista.get(i));
        }
        return modelo;
    }
    
    public DefaultTableModel obetnerTablaColores(){ //Todos
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Color> lista_colores = null;
        
        lista_colores = buscarObjetoColorDB();
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cod.Pintura");

        for (int i = 0; i < lista_colores.size(); i++) {
            Object[] fila = new Object[3];
            fila[0] = lista_colores.get(i).getId();
            fila[1] = lista_colores.get(i).getNombre();
            fila[2] = lista_colores.get(i).getCodigoPintura();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obetnerTablaColores(String dato){ //Segun dato que puede ser nombre color o cod pintura
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Color> lista_colores = null;
        
        lista_colores = buscarObjetoColorDB(dato);
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cod.Pintura");

        for (int i = 0; i < lista_colores.size(); i++) {
            Object[] fila = new Object[3];
            fila[0] = lista_colores.get(i).getId();
            fila[1] = lista_colores.get(i).getNombre();
            fila[2] = lista_colores.get(i).getCodigoPintura();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
}
