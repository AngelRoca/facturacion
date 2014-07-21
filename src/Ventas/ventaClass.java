package Ventas;

import Modelo.conexion;
import javax.swing.JTextField;

public class ventaClass {

    conexion con = new conexion();

    public void busqueda_producto(String id, JTextField produc, JTextField precio) {
        Object[][] product;
        try {
            product = con.leerDatos("productos", "producto,precio", "id_producto=" + id);
            produc.setText("" + product[0][0]);
            precio.setText("" + product[0][1]);
        } catch (Exception e) {

        }
    }
}
