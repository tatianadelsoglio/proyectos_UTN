/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Vista;

import Gestor_Modelo.GM_Marca;
import Modelo.Marca;
import Vista.GV;
import Vista.FrmMarcas;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Germán
 */
public class GV_Marca extends GV{

    private FrmMarcas form;
    private GM_Marca gestorModeloMarca;
    
    public GM_Marca getGestorModeloMarca(){
        if (gestorModeloMarca == null) { //Solo se ejecuta una vez
            gestorModeloMarca = GM_Marca.getInstancia();
        }
        return gestorModeloMarca;
    }

    public FrmMarcas getForm() {
        return form;
    }

    public void setForm(FrmMarcas form) {
        this.form = form;
    }

    public void abrirFormMarca() {
        this.setForm(new FrmMarcas(this));
        this.getForm().setLocationRelativeTo(null);
    }

    public void guardarMarca() {
        String nombreMarca = (this.form).getTxtNuevaMarca().getText();
        if (esCorrecto(nombreMarca)) {
            this.getGestorModeloMarca().crearModeloMarca();
            this.getModelo().setNombre(nombreMarca);
            
            getGestorModeloMarca().guardarObjetoMarcaDB(this.getModelo());
            
            JOptionPane.showMessageDialog(null, "¡¡¡ Marca Registrada Exitosamente !!!");
            setModeloATabla(this.getGestorModeloMarca().obtenerTablaMarcas(nombreMarca));
            (this.form).setTxtNuevaMarca(null);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido registrar la marca. Verifique el campo Nombre", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void buscarMarca() {
        String nombreMarcaABuscar = (this.form).getTxtBuscarMarca().getText();
        if (esCorrecto(nombreMarcaABuscar) == true) {
            setModeloATabla(this.getGestorModeloMarca().obtenerTablaMarcas(nombreMarcaABuscar));
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de una marca para buscar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void buscarTodasLasMarcas() {
        setModeloATabla(this.getGestorModeloMarca().obtenerTablaMarcas());
    }
    
    public void cargarAreaEditarMarcaSeleccionada(){
        JTable tablaMarcas = (this.form).tablaMarcas;
        if (tablaMarcas.getSelectedRows().length == 1){
            activarSessionActualizacion();
            int i = tablaMarcas.getSelectedRow();
            long id = (long) tablaMarcas.getValueAt(i, 0);
            String nombre = (String) tablaMarcas.getValueAt(i, 1);
            
            (this.form).setTxtIdActualizar(id);
            (this.form).setTxtNombreActualizar(nombre);
        }else{
           JOptionPane.showMessageDialog(null, "Para editar seleccione una unica fila.", "Atención", JOptionPane.INFORMATION_MESSAGE); 
        }
    }
    
    public void editarMarcaSeleccionada(){
        String nombreMarcaActualizada = (this.form).getTxtNombreActualizar().getText();
        long idMarcaActualizada =textAlong((this.form).getTxtIdActualizar().getText());
        
        if (esCorrecto(nombreMarcaActualizada) == true){
            this.getGestorModeloMarca().crearModeloMarca();
            this.getModelo().setNombre(nombreMarcaActualizada);
            this.getModelo().setId(idMarcaActualizada);
            
            
            getGestorModeloMarca().actualizarObjetoMarcaDB(this.getModelo());
            JOptionPane.showMessageDialog(null, "¡¡¡ Marca Actualizada Exitosamente !!!");
            setModeloATabla(this.getGestorModeloMarca().obtenerTablaMarcas(nombreMarcaActualizada));
            desactivarSessionActualizacion();
        }else{
            JOptionPane.showMessageDialog(null, "No se ha podido actualizar la marca. Verifique el campo Nombre", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void eliminarMarca() {
        JTable tablaMarcas = (this.form).tablaMarcas;
        if (tablaMarcas.getSelectedRows().length == 1) {

            int i = tablaMarcas.getSelectedRow();
            long id = (long) tablaMarcas.getValueAt(i, 0);
            String nombre = (String) tablaMarcas.getValueAt(i, 1);

            int msj = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar la marca seleccionada?");

            if (msj == JOptionPane.NO_OPTION || msj == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "Operación cancelada", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }

            if (msj == JOptionPane.YES_OPTION) {
                if(gestorModeloMarca.sePuedeBorrar(id)){
                    gestorModeloMarca.borrarObjetoMarcaDB(id);
                    JOptionPane.showMessageDialog(null, "¡¡¡ Marca Eliminada Exitosamente !!!");
                    setModeloATabla(this.getGestorModeloMarca().obtenerTablaMarcas(nombre));
                }else{
                    JOptionPane.showMessageDialog(null, "No se puede eliminar la marca seleccionada debido a que existe un vehiculo y/o modelo con esa marca.", "Atención", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Para eliminar seleccione una unica fila...", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setModeloATabla(DefaultTableModel modelo) {
        JTable tabla = (this.form).tablaMarcas;
        tabla.setModel(modelo);
        int[] anchos = {1, 100};
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    
    public void activarSessionActualizacion(){
        (this.form).activarSeccionActualizacion();
        (this.form).desactivarSeccionBusqueda();
        (this.form).desactivarSeccionNuevaMarca();
    }
    
    public void desactivarSessionActualizacion(){
        (this.form).desactivarSeccionActualizacion();
        (this.form).activarSeccionBusqueda();
        (this.form).activarSeccionNuevaMarca();
    }
    
    public void obtenerReporteTodasLasMarcas(){
        gestorModeloMarca.generarReporteMarcas();
    }
    
    private Marca getModelo() {
       return this.getGestorModeloMarca().getModelo();
    }
}
