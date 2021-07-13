package Gestor_Vista;

import Gestor_Modelo.GM_Vehiculo;
import Modelo.Color;
import Modelo.Condicion;
import Modelo.Marca;
import Modelo.Modelo;
import Modelo.Motor;
import Modelo.Tipocombustible;
import Modelo.Tipovehiculo;
import Modelo.Vehiculo;
import Modelo.Version;
import Vista.GV;
import Vista.FrmVehiculos;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GV_Vehiculos extends GV implements Observer{
    private FrmVehiculos form;
    private GM_Vehiculo getGestorModeloVehiculo;
    
    public GM_Vehiculo getGestorModeloVehiculo(){
        if (getGestorModeloVehiculo == null) { //Solo se ejecuta una vez
            getGestorModeloVehiculo = new GM_Vehiculo();
        }
        return getGestorModeloVehiculo;
    }

    public FrmVehiculos getForm() {
        return form;
    }

    public void setForm(FrmVehiculos form) {
        this.form = form;
    }
    
    public void abrirFormVehiculos() {
        this.setForm(new FrmVehiculos(this));
        this.getForm().setLocationRelativeTo(null);
        this.cargarComboColores();
        this.cargarComboTipoVehiculo();
        this.cargarComboMarcas();
        this.cargarComboCombustible();
        this.cargarComboMotor();
        this.cargarComboCondiciones();
        this.actualizarComboModelo();
        this.actualizarComboVersion();
        this.getGestorModeloVehiculo().getGestorModeloMarca().addObserver(this);
    }
    
    public void cargarComboColores(){
        this.form.cboColores.setModel(getGestorModeloVehiculo().obtenerComboColores()); 
    }
    
    public void cargarComboTipoVehiculo(){
        this.form.cboTipoVehiculo.setModel(getGestorModeloVehiculo().obtenerComboTipoVehiculos());
    }
    
    public void cargarComboMarcas(){
        this.form.cboMarcas.setModel(getGestorModeloVehiculo().obtenerComboMarcas());
        this.form.cboMarcasBusqueda.setModel(getGestorModeloVehiculo().obtenerComboMarcas());
    }
    
    public void cargarComboCombustible(){
        this.form.cboCombustibles.setModel(getGestorModeloVehiculo().obtenerComboCombustible());
    }
    
    public void cargarComboMotor(){
       form.cboMotores.setModel(getGestorModeloVehiculo().obtenerComboMotores());
    }
    
    public void cargarComboCondiciones(){
        this.form.cboCondicion.setModel(getGestorModeloVehiculo().obtenerComboCondiciones());
    }
    
    public void actualizarComboModelo(){
        this.form.cboModelos.removeAllItems();
        Marca marca = (Marca)form.cboMarcas.getSelectedItem();
        this.form.cboModelos.setModel(getGestorModeloVehiculo().obtenerComboModelos(marca));
    }
    
    public void actualizarComboVersion(){
        this.form.cboVersiones.removeAllItems();
        Modelo modelo = (Modelo) form.cboModelos.getSelectedItem();
        this.form.cboVersiones.setModel(getGestorModeloVehiculo().obtenerComboVersiones(modelo));
    }
        
    public void guardarVehiculo(){
            this.getGestorModeloVehiculo().crearModeloVehiculo();
            this.getModelo().setDescripcion(this.getForm().getAreaDescripcion().getText());
            this.getModelo().setMarca((Marca)this.getForm().getCboMarcas().getSelectedItem());
            this.getModelo().setModelo((Modelo)this.getForm().getCboModelos().getSelectedItem());
            this.getModelo().setMotor((Motor)this.getForm().getCboMotores().getSelectedItem());
            this.getModelo().setPrecioLista(StringADouble(this.getForm().getTxtPrecio().getText()));
            this.getModelo().setStock(StringAInteger(this.getForm().getTxtStock().getText()));
            this.getModelo().setTipocombustible((Tipocombustible)this.getForm().getCboCombustibles().getSelectedItem());
            this.getModelo().setTipovehiculo((Tipovehiculo)this.getForm().getCboTipoVehiculo().getSelectedItem());
            this.getModelo().setVersion_1((Version)this.getForm().getCboVersiones().getSelectedItem());
            this.getModelo().setColor((Color)this.getForm().getCboColores().getSelectedItem());
            this.getModelo().setKms(StringAInteger(this.getForm().getTxtKms().getText()));
            this.getModelo().setCondicion((Condicion)this.getForm().getCboCondicion().getSelectedItem());
            try{
                getGestorModeloVehiculo().guardarObjetoVehiculoDB(this.getModelo());
                JOptionPane.showMessageDialog(null, "¡¡¡ Vehiculo Registrado Exitosamente !!!");
                setModeloATabla(this.getGestorModeloVehiculo().obtenerTablaVehiculo(this.getGestorModeloVehiculo().getModelo().getMarca(),this.getGestorModeloVehiculo().getModelo().getModelo().getNombre()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "No se ha podido registrar el vehiculo."+e, "Atención", JOptionPane.INFORMATION_MESSAGE);
            }   
    }
        
    public void setModeloATabla(DefaultTableModel modelo) {
        JTable tabla = (this.form).tablaVehiculos;
        tabla.setModel(modelo);
    }
    
    public void buscarTodosLosVehiculos() {
        setModeloATabla(this.getGestorModeloVehiculo().obtenerTablaVehiculo());
    }
    
    public void buscarVehiculosEspecificos(){
        Marca marcaSel=(Marca)this.getForm().getCboMarcasBusqueda().getSelectedItem();
        String modelo = this.getForm().getTxtModeloBusqueda().getText();
        if(esCorrecto(modelo)){
            setModeloATabla(this.getGestorModeloVehiculo().obtenerTablaVehiculo(marcaSel, modelo));
        }else{
           setModeloATabla(this.getGestorModeloVehiculo().obtenerTablaVehiculo(marcaSel));
        }
    }
    
    public void borrarVehiculoSeleccionado(){
        JTable tablaVehiculos = (this.form).tablaVehiculos;
        int i = tablaVehiculos.getSelectedRow();
        long id = (long) tablaVehiculos.getValueAt(i, 0);
        
        int msj = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el vehiculo seleccionado?");

        if (msj == JOptionPane.NO_OPTION || msj == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

        if (msj == JOptionPane.YES_OPTION) {
            getGestorModeloVehiculo().borrarObjetoVehiculoDB(id);
            JOptionPane.showMessageDialog(null, "¡¡¡ Vehiculo Eliminado Exitosamente !!!");
            setModeloATabla(this.getGestorModeloVehiculo().obtenerTablaVehiculo());
        }
    }

    private Vehiculo getModelo() {
       return this.getGestorModeloVehiculo.getModelo();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.cargarComboMarcas();
    }

    

    

    
    
}
