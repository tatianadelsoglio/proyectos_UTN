/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import javax.swing.JTextField;

/**
 *
 * @author Germán
 */
public class GV {
   
    public boolean esCorrecto(String nombre) {
        return nombre.trim().length() != 0;
    }
    
    public Long textAlong(String valor){
        return Long.parseLong(valor);
    }
    
    public Double StringADouble(String valor){
        double s = Double.parseDouble(valor);
        return s;
    }
    
    public Integer StringAInteger(String valor){
        Integer i = Integer.parseInt(valor);
        return i;
    }
}
