/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Vista;

import Gestor_Modelo.GM_Color;
import Modelo.Color;
import Vista.GV;
import Vista.FrmColores;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Germán
 */
public class GV_Color extends GV {
    private FrmColores form;
    private GM_Color gestorModeloColor;
    
    public GM_Color getGestorModeloColor(){
        if (gestorModeloColor == null) { //Solo se ejecuta una vez
            gestorModeloColor = new GM_Color();
        }
        return gestorModeloColor;
    }

    public FrmColores getForm() {
        return form;
    }

    public void setForm(FrmColores form) {
        this.form = form;
    }
    
    public void abrirFormColor() {
        this.setForm(new FrmColores(this));
        this.getForm().setLocationRelativeTo(null);
    }
    
    public void guardarColor() {
        String nombreColor = (this.form).getTxtNuevocolor().getText();
        String codPintura = (this.form).getTxtCodigoPintura().getText();
        if (esCorrecto(nombreColor) == true && esCorrecto(codPintura) == true) {
            this.getGestorModeloColor().crearModeloColor();
            this.getModelo().setNombre(nombreColor);
            this.getModelo().setCodigoPintura(codPintura);
            
            getGestorModeloColor().guardarObjetoColorDB(this.getModelo());
            
            JOptionPane.showMessageDialog(null, "¡¡¡ Color Registrado Exitosamente !!!");
            setModeloATabla(this.getGestorModeloColor().obetnerTablaColores(nombreColor));
            (this.form).setTxtNuevocolor(null);
            (this.form).setTxtCodigoPintura(null);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido registrar el color. Verifique los campos", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
      
    public void setModeloATabla(DefaultTableModel modelo) {
        JTable tabla = (this.form).tablaColores;
        tabla.setModel(modelo);
        int[] anchos = {1, 100,50};
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    
    public void buscarTodosLoscolores() {
        setModeloATabla(this.getGestorModeloColor().obetnerTablaColores());
    }
    
    public void buscarColorEspecifico(){
        setModeloATabla(this.getGestorModeloColor().obetnerTablaColores((this.form).getTxtBuscarColor().getText()));
    }
    
    public void eliminarColor(){
        JTable tablaColores = (this.form).tablaColores;
        int i = tablaColores.getSelectedRow();
        long id = (long) tablaColores.getValueAt(i, 0);
        String nombre = (String) tablaColores.getValueAt(i, 1);
        
        int msj = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el color "+nombre+"?");

        if (msj == JOptionPane.NO_OPTION || msj == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

        if (msj == JOptionPane.YES_OPTION) {
            if(getGestorModeloColor().sePuedeBorrar(id)){
                getGestorModeloColor().borrarObjetoColorDB(id);
                JOptionPane.showMessageDialog(null, "¡¡¡ Color Eliminado Exitosamente !!!");
                setModeloATabla(this.getGestorModeloColor().obetnerTablaColores(nombre));
            }else{
                JOptionPane.showMessageDialog(null, "No se puede eliminar el color "+nombre+" . Existe un vehiculo registrado con ese color.", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void editarColorSeleccionada(){
        String nombreColorActualizada = (this.form).getTxtNombreActualizar().getText();
        String codPinturaActualizar = (this.form).getTxtCodPinturaActualizar().getText();
        long idColorActualizada =textAlong((this.form).getTxtIdActualizar().getText());
        
        if (esCorrecto(nombreColorActualizada) == true && esCorrecto(codPinturaActualizar) == true){
            this.getGestorModeloColor().crearModeloColor();
            this.getModelo().setId(idColorActualizada);
            this.getModelo().setNombre(nombreColorActualizada);
            this.getModelo().setCodigoPintura(codPinturaActualizar);
            
            getGestorModeloColor().actualizarObjetoColorDB(this.getModelo());
            
            JOptionPane.showMessageDialog(null, "¡¡¡ Marca Actualizada Exitosamente !!!");
            setModeloATabla(this.getGestorModeloColor().obetnerTablaColores(nombreColorActualizada));
            desactivarSessionActualizacion();
        }else{
            JOptionPane.showMessageDialog(null, "No se ha podido actualizar el color. Verifique los campos", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void cargarAreaEditarColorSeleccionado(){
        JTable tablaColores = (this.form).tablaColores;

        activarSessionActualizacion();
        int i = tablaColores.getSelectedRow();
        long id = (long) tablaColores.getValueAt(i, 0);
        String nombre = (String) tablaColores.getValueAt(i, 1);
        String codPintura = (String) tablaColores.getValueAt(i, 2);

        (this.form).setTxtIdActualizar(id);
        (this.form).setTxtNombreActualizar(nombre);
        (this.form).setTxtCodPinturaActualizar(codPintura);
    }
    
    public void activarSessionActualizacion(){
        (this.form).activarSeccionActualizacion();
        (this.form).desactivarSeccionBusqueda();
        (this.form).desactivarSeccionNuevoColor();
    }
    
    public void desactivarSessionActualizacion(){
        (this.form).desactivarSeccionActualizacion();
        (this.form).activarSeccionBusqueda();
        (this.form).activarSeccionNuevoColor();
    }
    
    private Color getModelo() {
       return this.getGestorModeloColor().getModelo();
    }
}
