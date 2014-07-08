package Modelo;
import java.sql.*;
import Configuracion.variablesGenerales;

public class conexion {
    Connection con=null;
    variablesGenerales vg=new variablesGenerales();
    
    public conexion(){
        String bd,user,pass;
        bd=vg.baseDeDatos;
        user=vg.usuarioMysql;
        pass=vg.passMysql;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd,user,pass);
            if(con!=null){
                System.out.println("Conectados");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public Connection conectar(){
        return con;
    }
    
    public void desconectar(){
        con=null;
    }
    
    public void agregar(){
    }
    
    public void actualizar(){
    }
    
    public void eliminar(){
    }
    
    public void iniciarSesion(String user,String pass){
        
    }
    
    public void cerrarSesion(){
    }
    
    public boolean prepararEstados(String query,String values){
        int max=values.length();
        boolean r=true;
        String[] val=values.split(",");
        try{
        PreparedStatement pstm=(PreparedStatement)conectar().prepareStatement(query);
        for(int i=0;i<max;i++){
            pstm.setString(i+1, val[i]);
        }
        r=pstm.execute();
        
        }catch(Exception e){System.out.println(e);}
        return r;
    }
}
