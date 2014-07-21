package GestionUsuarios;
import Modelo.*;

public class gestionUsuarios {
    conexion con;
    sesion s;
    
    public gestionUsuarios(sesion s){
        this.s=s;
    }
    
    public boolean agregarContador(String nombre,String pass,String notas){
        String valores=nombre+",";
        valores+=pass+",1,";
        valores+=notas;
        System.out.println("antes de agregar="+valores);
        if((con.agregar("usuarios", "nombre,password,permisos,notas", valores))) 
            System.out.println("Despues de agregar");
        return true;
    }
    
    public void agregarCajero(){
        
    }
    
    public void eliminarContador(){
        
    }
    
    public void eliminarCajero(){
        
    }
    
    public String getUsuario(){
        return s.getUsuario();
    }
    
    public String getUserName(){
        return s.getUsuario();
    }
}
