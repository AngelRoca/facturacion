package LogIn;

import Modelo.conexion;

public class sesion {
    
    public sesion(){
        conexion con=new conexion();
        //con.agregar("usuarios", "nombre,password,permisos", "rocks,rufu,2");
        //con.actualizar("usuarios", "nombre,password", "Ces,rumble", "id=2");
        con.eliminar("usuarios", "id=7");
    }
}
