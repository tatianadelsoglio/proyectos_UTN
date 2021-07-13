/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Marca;
import Modelo.Modelo;
import Modelo.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;



/**
 *
 * @author Germán
 */
public class GM_Marca extends Observable {
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    private Marca modelo;

    public Marca getModelo() {
        return modelo;
    }
    
    private static GM_Marca instanciaUnica;
    
    
    public static GM_Marca getInstancia(){ 
        if(instanciaUnica==null){ //Solo se ejecuta una vez
            instanciaUnica=new GM_Marca();
        }
        return instanciaUnica;
    }

    public void setModelo(Marca modelo) {
        this.modelo = modelo;
    }
    
    public Marca crearModeloMarca(){
        this.setModelo(new Marca());
        return this.getModelo();
    }
    
    
    public GM_Marca(){
        
    }
    
    
    public void guardarObjetoMarcaDB(Marca marca){
        Session s = gestorHibernate.getSession();
        Transaction tx = s.beginTransaction();
        s.save(marca);        
        tx.commit();
        this.setChanged();
        this.notifyObservers();
        
    }
    
    public void actualizarObjetoMarcaDB(Marca marcaActualizada){
        Session s = gestorHibernate.getSession();
        Marca m = (Marca) s.get(Marca.class,marcaActualizada.getId());
        m.setNombre(marcaActualizada.getNombre());
        
        Transaction tx = s.beginTransaction();
        s.update(m);        
        tx.commit();
        this.setChanged();
        this.notifyObservers();
    }
    
    public void borrarObjetoMarcaDB(long id){
        Session s = gestorHibernate.getSession();
        
        Marca m = (Marca) s.get(Marca.class, id);
        
        Transaction tx = s.beginTransaction();
        s.delete(m);        
        tx.commit();
        this.setChanged();
        this.notifyObservers();
    }
    
    public boolean sePuedeBorrar(long id){
        Session s = gestorHibernate.getSession();
        
        Criteria crit1 = s.createCriteria(Vehiculo.class);
        crit1.add(Restrictions.eq("marca.id",id));
        
        Criteria crit2 = s.createCriteria(Modelo.class);
        crit2.add(Restrictions.eq("marca.id",id));
        
        
        if (crit1.list().isEmpty() && (crit2.list().isEmpty())){
            return true;
        }else{
            return false;
        }
    }
    
    public ArrayList<Marca> buscarObjetoMarcaDB(String nombreMarca){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Marca.class);
        crit.add(Restrictions.like("nombre", "%" + nombreMarca + "%"));
        
        ArrayList<Marca> marcas = (ArrayList<Marca>) crit.list();

        return marcas;
    }
    
    public ArrayList<Marca> buscarTodosObjetosMarcaDB(){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Marca.class);
        ArrayList<Marca> marcas = (ArrayList<Marca>) crit.list();

        return marcas;
    }
    
    public DefaultComboBoxModel obtenerComboMarcas(){
        DefaultComboBoxModel marcas = new DefaultComboBoxModel();
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Marca.class);
        List lista = crit.list();
        
        for (int i = 0; i < lista.size(); i++) {
            marcas.addElement(lista.get(i));
        }
        return marcas;
    }
    
    public DefaultTableModel obtenerTablaMarcas(){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Marca> lista_marcas = buscarTodosObjetosMarcaDB();
        modelo.addColumn("id");
        modelo.addColumn("Nombre Marca");

        for (int i = 0; i < lista_marcas.size(); i++) {
            Object[] fila = new Object[2];
            fila[0] = lista_marcas.get(i).getId();
            fila[1] = lista_marcas.get(i).getNombre();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaMarcas(String nombreMarca){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Marca> lista_marcas = buscarObjetoMarcaDB(nombreMarca);
        modelo.addColumn("id");
        modelo.addColumn("Nombre Marca");

        for (int i = 0; i < lista_marcas.size(); i++) {
            Object[] fila = new Object[2];
            fila[0] = lista_marcas.get(i).getId();
            fila[1] = lista_marcas.get(i).getNombre();
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public void generarReporteMarcas(){
        try{
            JasperReport jr = (JasperReport) JRLoader.loadObject(GM_Marca.class.getResource("/Reportes/ListadoMarcas.jasper"));
            //Map parametros = new HashMap<String,Object>();
            //parametros.put("titulo","Listado de Personas");
            
            List<Marca> listaMarcas = buscarTodosObjetosMarcaDB();
            JRBeanCollectionDataSource datos = new JRBeanCollectionDataSource(listaMarcas);
            
            JasperPrint jp = JasperFillManager.fillReport(jr,null,datos);
            JasperViewer jv = new JasperViewer(jp,false);
            jv.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
