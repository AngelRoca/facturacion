package LogIn;

import Modelo.conexion;

public class sesion {
    private String User;
    private String Pass;
    private String Permissions;
    conexion con;
    
    public sesion(String user,String pass){
        con=new conexion();
        String[][] data=null;
        data=con.leerDatos("usuarios", "nombre,password,permisos", "nombre='"+user+"' and password='"+pass+"'");
        User=data[0][0];
        Pass=data[0][1];
        Permissions=data[0][2];
        System.out.println(User);
        System.out.println(Pass);
        System.out.println(Permissions);
    }
    
}
