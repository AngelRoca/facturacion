package Modelo;

import Modelo.conexion;

public class sesion {
    private String User;
    private String Pass;
    private String Permissions;
    private String Access;
    conexion con;
    
    public sesion(String user,String pass){
        con=new conexion();
        String[][] data=null;
        if((data=con.leerDatos("usuarios", "nombre,password,permisos", "nombre='"+user+"' and password='"+pass+"'"))==null){
            Access=User=Pass=Permissions="Acceso Denegado";
        }
        else{
            Access="Acceso Concedido";
            User=data[0][0]; Pass=data[0][1]; Permissions=data[0][2];
        }
    }

    public String getAcceso(){
        return Access;
    }
    
    public String getPermisos(){
        return Permissions;
    }
    
    public String getUsuario(){
        return User;
    }
    
    public String getPassword(){
        return Pass;
    }
}
