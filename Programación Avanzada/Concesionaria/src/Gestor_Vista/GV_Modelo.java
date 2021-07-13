package Gestor_Vista;

import Gestor_Modelo.GM_Modelo;
import Modelo.Marca;
import Modelo.Modelo;
import Vista.GV;
import Vista.FrmModelos;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class GV_Modelo extends GV {
    private FrmModelos form;
    private GM_Modelo gestorModeloModelo;
    
    public GM_Modelo getGestorModeloModelo(){
        if (gestorModeloModelo == null) { //Solo se ejecuta una vez
            gestorModeloModelo = new GM_Modelo();
        }
        return gestorModeloModelo;
    }
    
    public FrmModelos getForm() {
        return form;
    }

    public void setForm(FrmModelos form) {
        this.form = form;
    }
    
    public void abrirFormModelo() {
        this.setForm(new FrmModelos(this));
        this.getForm().setLocationRelativeTo(null);
        this.cargarComboMarcas();
        this.getForm().desactivarSessionActualizacion();
    }
    
    public void cargarComboMarcas(){
        this.form.cboMarcasDeModelo.setModel(getGestorModeloModelo().obtenerComboMarcas());
        this.form.cboMarcaBusqueda.setModel(getGestorModeloModelo().obtenerComboMarcas());
    }
    
    public void setModeloATabla(DefaultTableModel modelo) {
        JTable tabla = (this.form).tablaModelos;
        tabla.setModel(modelo);
        int[] anchos = {0,50,25};
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    
    public void buscarTodosLosModelos() {
        setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos());
    }
    
    public void buscarModeloEspecifico(){
        Marca marcaSeleBusqueda = (Marca) (this.form).getCboMarcaBusqueda().getSelectedItem();
        String nombreModelo = (this.form).getTxtNombreMBusqueda().getText();
        if(esCorrecto(nombreModelo)){
            setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos(marcaSeleBusqueda, nombreModelo));
        }else{
            setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos(marcaSeleBusqueda));
        }
    }
    
    public void cargarModeloSelecParaActualizacion(){
        this.getForm().activarSessionActualizacion();
        JTable tablaModelos = (this.form).tablaModelos;
        int i = tablaModelos.getSelectedRow();
        this.getForm().setTxtIdActualizarModelo((long)tablaModelos.getValueAt(i, 0));
        this.getForm().setTxtNombreModeloActualizar((String)tablaModelos.getValueAt(i, 1));
        Marca nombreMarca = (Marca)tablaModelos.getValueAt(i, 2);
        cargarComboMarcaActualizar(nombreMarca.getId());
    }
    
    public void cargarComboMarcaActualizar(long id){
        (this.form).getCboMarcaActualizar().setModel(getGestorModeloModelo().obtenerComboMarcas());
        int cant = (this.form).getCboMarcaActualizar().getItemCount();
        int index = 0;
        for (int i=0;i<cant;i++){
            if((this.form).getCboMarcaActualizar().getItemAt(i).getId()==id){
                (this.form).getCboMarcaActualizar().setSelectedIndex(index);
            }
            index=index+1;
        }
    }
    
    public void actualizarModelo(){
        String nombreModeloActualizada = (this.form).getTxtNombreModeloActualizar();
        long idModeloActualizar =textAlong((this.form).getTxtIdActualizarModelo());
        Marca marcaSele = (Marca)(this.form).getCboMarcaActualizar().getSelectedItem();
        
        if (esCorrecto(nombreModeloActualizada) == true){
            this.getGestorModeloModelo().crearModeloModelo();
            this.getModelo().setMarca(marcaSele);
            this.getModelo().setNombre(nombreModeloActualizada);
            this.getModelo().setId(idModeloActualizar);
            
            getGestorModeloModelo().actualizarObjetoModeloDB(this.getModelo());
            JOptionPane.showMessageDialog(null, "¡¡¡ Modelo Actualizado Exitosamente !!!");
            setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos(marcaSele, nombreModeloActualizada));
            this.getForm().desactivarSessionActualizacion();
        }else{
            JOptionPane.showMessageDialog(null, "No se ha podido actualizar el modelo. Verifique todos los campos", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void guardarModelo() {
        String nombreModelo = (this.form).getTxtNombreNuevoModelo().getText();
        Marca marcaSel = (Marca)(this.form).getCboMarcasDeModelo().getSelectedItem();
        if (esCorrecto(nombreModelo)) {
            this.getGestorModeloModelo().crearModeloModelo();
            this.getModelo().setMarca(marcaSel);
            this.getModelo().setNombre(nombreModelo);
            
            getGestorModeloModelo().guardarObjetoModeloDB(this.getModelo());
            
            JOptionPane.showMessageDialog(null, "¡¡¡ Modelo Registrado Exitosamente !!!");
            setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos(marcaSel, nombreModelo));
            (this.form).setTxtNombreNuevoModelo(null);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido registrar el modelo. Verifique el campo Nombre", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void eliminarModelo(){
        JTable tablaModelos = (this.form).tablaModelos;
        int i = tablaModelos.getSelectedRow();
        long id = (long) tablaModelos.getValueAt(i, 0);
        Marca marca = (Marca)tablaModelos.getValueAt(i, 2);
        String nombreModelo = (String)tablaModelos.getValueAt(i,1);
        
        this.getGestorModeloModelo().crearModeloModelo();
        this.getModelo().setId(id);
        this.getModelo().setMarca(marca);
        this.getModelo().setNombre(nombreModelo);
        
        
        int msj = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el modelo " + nombreModelo + " ?");

        if (msj == JOptionPane.NO_OPTION || msj == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

        if (msj == JOptionPane.YES_OPTION){
            if(getGestorModeloModelo().sePuedeBorrar(this.getModelo())==true){
                getGestorModeloModelo().borrarObjetoModeloDB(id);
                JOptionPane.showMessageDialog(null, "¡¡¡ Modelo Eliminado Exitosamente !!!");
                setModeloATabla(this.getGestorModeloModelo().obtenerTablaModelos(marca, nombreModelo));
            }else{
                JOptionPane.showMessageDialog(null, "No se puede eliminar "+nombreModelo+" debido a que existe un vehiculo registrado con ese modelo", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private Modelo getModelo() {
       return this.getGestorModeloModelo().getModelo();
    }
}
