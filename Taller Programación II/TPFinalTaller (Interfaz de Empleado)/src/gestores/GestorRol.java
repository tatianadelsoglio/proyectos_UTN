/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestores;

import dominio.Rol;
import interfaz.FrmConsultarRol;
import interfaz.FrmNuevoRol;
import interfaz.FrmPrincipal;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author luchy
 */
public class GestorRol extends Observable{
    
    private static GestorRol instanciaUnica;
    private ArrayList<Rol> rolesDelSistema = new ArrayList<Rol>();
    private FrmPrincipal principal;
    private FrmConsultarRol frmConsultarRol ; 
    
    
     public static GestorRol getInstancia(FrmPrincipal principal) {
        if (instanciaUnica == null) {
            instanciaUnica = new GestorRol(principal);
        }
        return instanciaUnica;
    }
     
     private GestorRol(FrmPrincipal principal) {
        this.principal = principal;
    }
    
    public ArrayList<Rol> getRolesDelSistema() {
        return rolesDelSistema;
    }

    public void mostrarPantallaNuevoRol() {
        FrmNuevoRol frmNuevoRol = new FrmNuevoRol(this);
        this.principal.mostrarVentana(frmNuevoRol);
    }

    public void guardarNuevoRol(Rol nuevoRol) {
        this.rolesDelSistema.add(nuevoRol);
        setChanged();
        notifyObservers();
    }
    
   
    public void editarRol(Rol instanciaEditando) {
        setChanged();
        notifyObservers();
    }

    public void mostrarPantallaAdministrar() {
        if (frmConsultarRol == null){
        frmConsultarRol = new FrmConsultarRol(this);
        this.principal.mostrarVentana(frmConsultarRol);
        } else {
         frmConsultarRol.setVisible(true);

        }
    }

    public void mostrarPantallaEditarRol(Rol rolSeleccionado) {
       FrmNuevoRol frmNuevoRol = new FrmNuevoRol(this, rolSeleccionado);
        this.principal.mostrarVentana(frmNuevoRol);
    }
    
    public void eliminarRol(Rol rolAEliminar){
                for (int i = 0; i<this.rolesDelSistema.size(); i++){
                    if (this.rolesDelSistema.get(i).getNombreRol().equals(rolAEliminar.getNombreRol())){
                        this.rolesDelSistema.remove(i);
                        break;
                    } 
                }
           setChanged();     
           notifyObservers();

                
    }

    Iterable<Rol> getRoles() {
        return rolesDelSistema; 
    }

}

