package LogIn;

import Modelo.conexion;

public class sesion {
    
    public sesion(){
        conexion con=new conexion();
        /*con.agregar("usuarios", "nombre,password,permisos", "rocks,rufu,2");
        con.actualizar("usuarios", "nombre,password", "Ces,rumble", "id=2");
        con.eliminar("usuarios", "id=7");*/
        Object[][] r=con.leerDatos("productos");
        int a=r.length;
        int b=r[0].length;
        for(int i=0;i<a;i++){
            for(int j=0;j<b;j++){
                System.out.println(r[i][j]);
            }
            System.out.println("\n");
        }
        
    }
}
