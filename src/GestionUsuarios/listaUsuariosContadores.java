/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionUsuarios;

import Modelo.conexion;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shary
 */
public class listaUsuariosContadores {

    private Modelo.conexion con = new conexion();

    public Object[][] getDatos1(DefaultTableModel modelo) {
        int registros = 0;
//obtenemos la cantidad de registros existentes en la tabla
        try {
            PreparedStatement pstm = (PreparedStatement) con.conectar().prepareStatement("SELECT count(1) as total FROM usuarios WHERE permisos=true ");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[][] data = new String[registros][2];
        String[] Datos = new String[2];

//realizamos la consulta sql y llenamos los datos en "Object"
        try {
            PreparedStatement pstm = (PreparedStatement) con.conectar().prepareStatement("SELECT "
                    + " nombre, password"
                    + " FROM usuarios WHERE permisos=true");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                String estNombre = res.getString("nombre");
                String estPassword = res.getString("password");

                data[i][0] = estNombre;
                data[i][1] = estPassword;

                Datos[0] = (String) data[i][0];
                Datos[1] = (String) data[i][1];
                modelo.addRow(Datos);
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

}
