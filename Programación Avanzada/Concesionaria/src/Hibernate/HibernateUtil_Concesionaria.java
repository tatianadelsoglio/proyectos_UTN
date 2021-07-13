package Hibernate;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class HibernateUtil_Concesionaria  {
    public static SessionFactory sessionFactory;
    public static Session session;

    public static void inicializar() {
        try {
           AnnotationConfiguration conf = new AnnotationConfiguration();
           try{
                conf.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
                conf.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");                 
                conf.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/concesionaria_final2");
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error de Base de Datos N� 2001");
            }
            
            conf.setProperty("hibernate.connection.username","root");
//            conf.setProperty("hibernate.connection.password","postgres");
       conf.setProperty("hibernate.connection.password","");
//    
//            try{
//                conf.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
//                conf.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect"); 
//                conf.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/PP");
////                conf.setProperty("hibernate.connection.username","root");
//                conf.setProperty("hibernate.connection.password","");
//
//            }
//            catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Error de Base de Datos N� 2001");
//            }


            conf.setProperty("hibernate.connection.pool_size","10");                          
            conf.setProperty("hibernate.hbm2ddl.auto","update");

            conf.addPackage("Modelo");
            conf.addAnnotatedClass(Modelo.Color.class);
            conf.addAnnotatedClass(Modelo.Condicion.class);
            conf.addAnnotatedClass(Modelo.Marca.class);
            conf.addAnnotatedClass(Modelo.Modelo.class);
            conf.addAnnotatedClass(Modelo.Motor.class);
            conf.addAnnotatedClass(Modelo.Tipocombustible.class);
            conf.addAnnotatedClass(Modelo.Tipovehiculo.class);
            conf.addAnnotatedClass(Modelo.Vehiculo.class);
            conf.addAnnotatedClass(Modelo.Version.class);
           
            try {
                    sessionFactory = conf.buildSessionFactory();
                    session=sessionFactory.openSession();
                }
                catch(HibernateException e){
                    JOptionPane.showMessageDialog(null, e);
                }
        } catch (HeadlessException ex) {
            throw new ExceptionInInitializerError(ex);
        } catch (MappingException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session getSession()
    throws HibernateException {
        return session;
    }
}
