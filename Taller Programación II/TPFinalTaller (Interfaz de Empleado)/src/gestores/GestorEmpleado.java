package gestores;

import dominio.Empleado;
import dominio.Rol;
import interfaz.FrmConsultarEmpleados;
import interfaz.FrmNuevoEmpleado;
import interfaz.FrmPrincipal;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Alumno
 */
public class GestorEmpleado extends Observable {
    
    private static GestorEmpleado instanciaUnica;
    private FrmPrincipal principal;
    private ArrayList<Empleado> empleadosDelSistema = new ArrayList<Empleado>();
    
    private FrmConsultarEmpleados frmConsultarEmpleados ;

   
    public static GestorEmpleado getInstancia(FrmPrincipal principal) {
        if (instanciaUnica == null) {
            instanciaUnica = new GestorEmpleado(principal);
        }
        return instanciaUnica;
    }
    
    private GestorEmpleado(FrmPrincipal principal) {
        this.principal = principal;
    }

    public void mostrarPantallaAdministrar() {
        if (this.frmConsultarEmpleados == null){
        frmConsultarEmpleados = new FrmConsultarEmpleados(this);
        this.principal.mostrarVentana(frmConsultarEmpleados);
        } else {         
         this.principal.mostrarVentana(frmConsultarEmpleados);   
        }
    }

    public void mostrarPantallaNuevoEmpleado() {
        FrmNuevoEmpleado frmNuevoEmpleado = new FrmNuevoEmpleado(this);
        this.principal.mostrarVentana(frmNuevoEmpleado);
    }

    public void guardarNuevoEmpleado(Empleado nuevoEmpleado) {
        this.empleadosDelSistema.add(nuevoEmpleado);
        setChanged();
        notifyObservers();
    }

    public ArrayList<Empleado> getEmpleadosDelSistema() {
        return empleadosDelSistema;
    }

    public void mostrarPantallaEditarEmpleado(Empleado empleadoSeleccionado) {
        FrmNuevoEmpleado frmNuevoEmpleado = new FrmNuevoEmpleado(this, empleadoSeleccionado);
        this.principal.mostrarVentana(frmNuevoEmpleado);
    }

    public void editarEmpleado(Empleado instanciaEditando) {
        setChanged();
        notifyObservers();
    }

    public Iterable<Rol> getRoles() {
        
        return GestorRol.getInstancia(this.principal).getRoles(); 
    }
}
