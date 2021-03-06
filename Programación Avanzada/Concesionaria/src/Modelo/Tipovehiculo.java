package Modelo;
// Generated 05/11/2019 00:34:25 by Hibernate Tools 4.3.1



import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tipovehiculo generated by hbm2java
 */
@Entity
@Table (name="tipovehiculo")
public class Tipovehiculo  implements java.io.Serializable {

    @Id @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)    
    private Long id;
    private String nombre;
     

    public Tipovehiculo() {
    }

	
    public Tipovehiculo(String nombre) {
        this.nombre = nombre;
    }
    public Tipovehiculo(String nombre, Set vehiculos) {
       this.nombre = nombre;
       
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre; //To change body of generated methods, choose Tools | Templates.
    }




}


