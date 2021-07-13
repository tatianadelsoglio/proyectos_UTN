
import Gestor_Vista.GV_Principal;
import static Gestor_Vista.GV_Principal.getInstancia;
import Hibernate.HibernateUtil_Concesionaria;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Germán
 */
public class Main {
    static public void main(String[] args){
        HibernateUtil_Concesionaria.inicializar();
        GV_Principal gestorVistaPrincipal = getInstancia();
        gestorVistaPrincipal.abrirFormPrincipal();
        gestorVistaPrincipal.getForm().setVisible(true);

    }
}
