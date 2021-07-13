package Gestor_Modelo;


import Hibernate.HibernateUtil_Concesionaria;
import Modelo.Marca;
import Modelo.Modelo;
import Modelo.Vehiculo;
import Vista.GV;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class GM_Vehiculo extends GV{
    private final HibernateUtil_Concesionaria gestorHibernate = null;
    private Vehiculo modelo;
    private GM_Color gestorModeloColor;
    private GM_Condicion gestorModeloCondicion;
    private GM_Motor gestorModeloMotor;
    private GM_Tipocombustible gestorModeloCombustible;
    private GM_Marca gestorModeloMarca;
    private GM_Tipovehiculo gestorModeloTipoVehiculo;
    private GM_Modelo gestorModeloModelo;
    private GM_Version gestorModeloVersion;
    
    public Vehiculo getModelo() {
        return modelo;
    }

    public void setModelo(Vehiculo modelo) {
        this.modelo = modelo;
    }
    
    public Vehiculo crearModeloVehiculo(){
        this.setModelo(new Vehiculo());
        return this.getModelo();
    }
    
    public GM_Motor getGestorModeloMotor(){
        if (gestorModeloMotor == null) { //Solo se ejecuta una vez
            gestorModeloMotor = new GM_Motor();
        }
        return gestorModeloMotor;
    }
    
    public GM_Version getGestorModeloVersion(){
        if (gestorModeloVersion == null) { //Solo se ejecuta una vez
            gestorModeloVersion = new GM_Version();
        }
        return gestorModeloVersion;
    }
    
    public GM_Modelo getGestorModeloModelo(){
        if (gestorModeloModelo == null) { //Solo se ejecuta una vez
            gestorModeloModelo = new GM_Modelo();
        }
        return gestorModeloModelo;
    }
    
    public GM_Marca getGestorModeloMarca(){
//        if (gestorModeloMarca == null) { //Solo se ejecuta una vez
//            gestorModeloMarca = new GM_Marca();
//        }
//        return gestorModeloMarca;
        if(gestorModeloMarca==null){
            gestorModeloMarca= GM_Marca.getInstancia();
        }
        return gestorModeloMarca;
    }
    
     public GM_Tipovehiculo getGestorModeloTipoVehiculo(){
        if (gestorModeloTipoVehiculo == null) { //Solo se ejecuta una vez
            gestorModeloTipoVehiculo = new GM_Tipovehiculo();
        }
        return gestorModeloTipoVehiculo;
    }
    
    public GM_Tipocombustible getGestorModeloCombustible(){
        if (gestorModeloCombustible == null) { //Solo se ejecuta una vez
            gestorModeloCombustible = new GM_Tipocombustible();
        }
        return gestorModeloCombustible;
    }
    
    public GM_Color getGestorModeloColor(){
        if (gestorModeloColor == null) { //Solo se ejecuta una vez
            gestorModeloColor = new GM_Color();
        }
        return gestorModeloColor;
    }
    
    public GM_Condicion getGestorModeloCondicion(){
        if (gestorModeloCondicion == null) { //Solo se ejecuta una vez
            gestorModeloCondicion = new GM_Condicion();
        }
        return gestorModeloCondicion;
    }
   
    public GM_Vehiculo(){
        
    }
    
    public void guardarObjetoVehiculoDB(Vehiculo v){
        Session s = gestorHibernate.getSession();
        Transaction tx = s.beginTransaction();
        s.save(v);        
        tx.commit();
    }
    
    
    public ArrayList<Vehiculo> buscarObjetoVehiculoDB(){ 
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Vehiculo.class);
        ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) crit.list();

        return vehiculos;
    }
    
    public ArrayList<Vehiculo> buscarObjetoVehiculoDB(Marca marca,String modelo){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Vehiculo.class);
        crit.createAlias("modelo", "modelo");
        crit.add(Restrictions.eq("marca",marca));
        crit.add(Restrictions.like("modelo.nombre", "%" + modelo + "%"));
        
        ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) crit.list();

        return vehiculos;
    }
    
    public ArrayList<Vehiculo> buscarObjetoVehiculoDB(Marca marca){
        Session s = gestorHibernate.getSession();
        Criteria crit = s.createCriteria(Vehiculo.class);
        
        crit.add(Restrictions.eq("marca",marca));
        
        ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) crit.list();

        return vehiculos;
    }
    
    public void borrarObjetoVehiculoDB(long id){
        Session s = gestorHibernate.getSession();
        Vehiculo v = (Vehiculo)s.get(Vehiculo.class, id);
        Transaction tx = s.beginTransaction();
        s.delete(v);        
        tx.commit();
    }
    
    public DefaultComboBoxModel obtenerComboColores(){
        return getGestorModeloColor().obtenerComboColores();
    }
    
    public DefaultComboBoxModel obtenerComboCondiciones(){
        return getGestorModeloCondicion().obtenerComboCondiciones();
    }
    
    public DefaultComboBoxModel obtenerComboMotores(){
        return getGestorModeloMotor().obtenerComboMotores();
    }
    
    public DefaultComboBoxModel obtenerComboCombustible(){
        return getGestorModeloCombustible().obtenerComboCombustible();
    }
    
    public DefaultComboBoxModel obtenerComboMarcas(){
        return getGestorModeloMarca().obtenerComboMarcas();
    }
    
    public DefaultComboBoxModel obtenerComboTipoVehiculos(){
        return getGestorModeloTipoVehiculo().obtenerComboTipoVehiculos();
    }
    
    public DefaultComboBoxModel obtenerComboModelos(Marca marca){
        return getGestorModeloModelo().obtenerComboModeloXmarca(marca);
    }
    
    public DefaultComboBoxModel obtenerComboVersiones(Modelo modelo){
        return getGestorModeloVersion().obtenerComboVersionXmodelo(modelo);
    }
    
    public DefaultTableModel obtenerTablaVehiculo(Marca marca,String nombreModelo){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Vehiculo> lista_vehiculos = buscarObjetoVehiculoDB(marca,nombreModelo);
        
        modelo.addColumn("id");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Versión");
        
       
        modelo.addColumn("Kms");
        
        modelo.addColumn("Precio");
        modelo.addColumn("Stock (Uni)");

        for (int i = 0; i < lista_vehiculos.size(); i++) {
            Object[] fila = new Object[7];
            fila[0] = lista_vehiculos.get(i).getId();
            fila[1] = lista_vehiculos.get(i).getMarca();
            fila[2] = lista_vehiculos.get(i).getModelo();
            fila[3] = lista_vehiculos.get(i).getVersion_1();
            
            
            fila[4] = lista_vehiculos.get(i).getKms();
            
            fila[5] = lista_vehiculos.get(i).getPrecioLista();
            fila[6] = lista_vehiculos.get(i).getStock();
            
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaVehiculo(){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Vehiculo> lista_vehiculos = buscarObjetoVehiculoDB();
        
        modelo.addColumn("id");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Versión");
        
       
        modelo.addColumn("Kms");
        
        modelo.addColumn("Precio");
        modelo.addColumn("Stock (Uni)");

        for (int i = 0; i < lista_vehiculos.size(); i++) {
            Object[] fila = new Object[7];
            fila[0] = lista_vehiculos.get(i).getId();
            fila[1] = lista_vehiculos.get(i).getMarca();
            fila[2] = lista_vehiculos.get(i).getModelo();
            fila[3] = lista_vehiculos.get(i).getVersion_1();
            
            
            fila[4] = lista_vehiculos.get(i).getKms();
            
            fila[5] = lista_vehiculos.get(i).getPrecioLista();
            fila[6] = lista_vehiculos.get(i).getStock();
            
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaVehiculo(Marca marca){
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Vehiculo> lista_vehiculos = buscarObjetoVehiculoDB(marca);
        
        modelo.addColumn("id");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Versión");
        
       
        modelo.addColumn("Kms");
        
        modelo.addColumn("Precio");
        modelo.addColumn("Stock (Uni)");

        for (int i = 0; i < lista_vehiculos.size(); i++) {
            Object[] fila = new Object[7];
            fila[0] = lista_vehiculos.get(i).getId();
            fila[1] = lista_vehiculos.get(i).getMarca();
            fila[2] = lista_vehiculos.get(i).getModelo();
            fila[3] = lista_vehiculos.get(i).getVersion_1();
            
            
            fila[4] = lista_vehiculos.get(i).getKms();
            
            fila[5] = lista_vehiculos.get(i).getPrecioLista();
            fila[6] = lista_vehiculos.get(i).getStock();
            
            modelo.addRow(fila);
        }
        return modelo;
    }
}
