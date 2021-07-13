/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author luchy
 */
public class Rol {
    private String nombreRol;
    //private Double factorSueldo;
    private String descripcion; 

    public Rol(String nombreRol, String descripcion) {
        this.nombreRol= nombreRol;
        this.descripcion = descripcion; 
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
   /*
    public Double getFactorSueldo() {
        return factorSueldo;
    }

    public void setFactorSueldo(Double factorSueldo) {
        this.factorSueldo = factorSueldo;
    }

    public void setFactorSueldo(Integer factorSueldo) {
        this.factorSueldo = Double.parseDouble(factorSueldo.toString());
    }
    */
    @Override
    public String toString() {
        return nombreRol;
    }
}
