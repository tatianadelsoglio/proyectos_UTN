/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Vista;

import Vista.FrmPrincipal;
import javax.swing.ImageIcon;

/**
 *
 * @author Germán
 */
public class GV_Principal {
    
    private FrmPrincipal form;

    private static GV_Principal instanciaUnica;
    private GV_Marca gestorVMarca;
    private GV_Color gestorVColor;
    private GV_Vehiculos gestorVVehiculos;
    private GV_Modelo gestorVModelo;
    
    
    public static GV_Principal getInstancia(){ 
        if(instanciaUnica==null){ //Solo se ejecuta una vez
            instanciaUnica=new GV_Principal();
        }
        return instanciaUnica;
    }

    public FrmPrincipal getForm() {
        return form;
    }

    public void setForm(FrmPrincipal form) {
        this.form = form;
    }
    
   
    public void abrirFormPrincipal(){
        this.setForm(new FrmPrincipal());
        this.getForm().setLocationRelativeTo(null);
        
    }
    
    public void abrirMenuMarca(){
//        GV_Marca gestorVistaMarca = GV_Marca.getInstancia();
        gestorVMarca = new GV_Marca();
        gestorVMarca.abrirFormMarca();
        gestorVMarca.getForm().setVisible(true);
    }
    
    public void abrirMenuColor(){
        gestorVColor = new GV_Color();
        gestorVColor.abrirFormColor();
        gestorVColor.getForm().setVisible(true);
    }
    
    public void abrirMenuModelo(){
        gestorVModelo = new GV_Modelo();
        gestorVModelo.abrirFormModelo();
        gestorVModelo.getForm().setVisible(true);
    }
    
    public void abrirMenuAdmVehiculos(){
        gestorVVehiculos = new GV_Vehiculos();
        gestorVVehiculos.abrirFormVehiculos();
        gestorVVehiculos.getForm().setVisible(true);
    }
    
}
