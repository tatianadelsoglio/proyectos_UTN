package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Marca;
import Modelo.Modelo;
import Modelo.Vehiculo;
import Vista.GV;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public class GM_Modelo extends GV{
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    private Modelo modeloModelo;
    private GM_Marca gestorModeloMarca;
    
    public Modelo getModelo() {
        return modeloModelo;
    }

    public void setModelo(Modelo modelo) {
        this.modeloModelo = modelo;
    }
    
    public Modelo crearModeloModelo(){
        this.setModelo(new Modelo());
        return this.getModelo();
    }
    
    public GM_Marca getGestorModeloMarca(){
        if (gestorModeloMarca == null) { //Solo se ejecuta una vez
            gestorModeloMarca = GM_Marca.getInstancia();
        }
        return gestorModeloMarca;
    }
   
    
    public GM_Modelo(){
        
    }
    
    public ArrayList<Modelo> buscarObjetoModeloDB(Marca marca){ 
        ArrayList<Modelo> listaModelos = new ArrayList<>();

        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Modelo.class);
        crit.add(Restrictions.eq("marca", marca));

        List list = crit.list();
        for (int x = 0; x < list.size(); x++) {
            listaModelos.add((Modelo) list.get(x));
        }
        return listaModelos;
    }
    
    public ArrayList<Modelo> buscarObjetoModeloDB(){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Modelo.class);
        ArrayList<Modelo> listaModelos = (ArrayList<Modelo>) crit.list();
        
        return listaModelos;
    }
    
    public ArrayList<Modelo> buscarObjetoModeloDB(Marca marca,String nombre){
        ArrayList<Modelo> listaModelos = new ArrayList<>();
        
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Modelo.class);
        crit.add(Restrictions.eq("marca",marca));
        crit.add(Restrictions.like("nombre","%"+nombre+"%"));
        
        List list = crit.list();
        for(int x=0;x<list.size();x++){
            listaModelos.add((Modelo) list.get(x));
        }
        return listaModelos;
    }
    
    public void guardarObjetoModeloDB(Modelo model){
        Session s = gestorHibernate.getSession();
        Transaction tx = s.beginTransaction();
        s.save(model);        
        tx.commit();  
    }
    
    public void borrarObjetoModeloDB(long id){   
        Session s = gestorHibernate.getSession();
        Modelo m = (Modelo)s.get(Modelo.class, id);
        Transaction tx = s.beginTransaction();
        s.delete(m);
        tx.commit();
    }
    
    public boolean sePuedeBorrar(Modelo modelo){
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Vehiculo.class);
        crit.add(Restrictions.eq("modelo",modelo));
        vehiculos = (ArrayList<Vehiculo>) crit.list();
        
        if (vehiculos.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public void actualizarObjetoModeloDB(Modelo modelo){
        Session s = gestorHibernate.getSession();
        Modelo m = (Modelo) s.get(Modelo.class, modelo.getId());        
        m.setNombre(modelo.getNombre());
        m.setMarca(modelo.getMarca());
        
        Transaction tx = s.beginTransaction();
        s.update(m);        
        tx.commit();
    }
    
    public DefaultComboBoxModel obtenerComboModeloXmarca(Marca marca){
        DefaultComboBoxModel modelosXmarcas = new DefaultComboBoxModel();
        ArrayList<Modelo> array = this.buscarObjetoModeloDB(marca);
        
        for (int i = 0; i < array.size(); i++) {
            modelosXmarcas.addElement(array.get(i));
        }
        return modelosXmarcas;
    }
    
    public DefaultComboBoxModel obtenerComboMarcas(){
        return getGestorModeloMarca().obtenerComboMarcas();
    }
    
    public DefaultTableModel obtenerTablaModelos(){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Modelo> lista_modelos = buscarObjetoModeloDB();
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre Modelo");
        modelo.addColumn("Marca");

        for (int i = 0; i < lista_modelos.size(); i++) {
            Object[] fila = new Object[3];
            fila[0] = lista_modelos.get(i).getId();
            fila[1] = lista_modelos.get(i).getNombre();
            fila[2] = lista_modelos.get(i).getMarca();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaModelos(Marca objMarca,String nombreModelo){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Modelo> lista_modelos = buscarObjetoModeloDB(objMarca,nombreModelo);
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre Modelo");
        modelo.addColumn("Marca");

        for (int i = 0; i < lista_modelos.size(); i++) {
            Object[] fila = new Object[3];
            fila[0] = lista_modelos.get(i).getId();
            fila[1] = lista_modelos.get(i).getNombre();
            fila[2] = lista_modelos.get(i).getMarca();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaModelos(Marca objMarca){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Modelo> lista_modelos = buscarObjetoModeloDB(objMarca);
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre Modelo");
        modelo.addColumn("Marca");

        for (int i = 0; i < lista_modelos.size(); i++) {
            Object[] fila = new Object[3];
            fila[0] = lista_modelos.get(i).getId();
            fila[1] = lista_modelos.get(i).getNombre();
            fila[2] = lista_modelos.get(i).getMarca();
            modelo.addRow(fila);
        }
        return modelo;
    }
}
