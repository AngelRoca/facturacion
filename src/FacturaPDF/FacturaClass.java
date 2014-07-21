/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FacturaPDF;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import Modelo.conexion;

/**
 *
 * @author Shary
 */
public class FacturaClass {
    DefaultTableModel modelo;
    conexion con = new conexion();
    public FacturaClass(DefaultTableModel model) {
        modelo=model;
    }
        public Object[][] getDatos() {
        int registros = 0;
//obtenemos la cantidad de registros existentes en la tabla
        try {
            PreparedStatement pstm = (PreparedStatement) con.conectar().prepareStatement("SELECT count(1) as total FROM ventas where factura=true; ");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[][] data = new String[registros][8];
        String[] Datos = new String[8];

//realizamos la consulta sql y llenamos los datos en "Object"
        try {
            PreparedStatement pstm = (PreparedStatement) con.conectar().prepareStatement("SELECT "
                    + "id_folio,rfc_cliente,empresa,producto,cantidad,precio_unitario,subtotal,total"
                    + " FROM ventas where factura=true"
                    + " ORDER BY id_folio; ");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                String estId_folio = res.getString("id_folio");
                String estRfc = res.getString("rfc_cliente");
                String estEmpresa = res.getString("empresa");
                String estProducto = res.getString("producto");
                String estCantidad = res.getString("cantidad");
                String estPrecio = res.getString("precio_unitario");
                String estSubtotal = res.getString("subtotal");
                String estTotal = res.getString("total");

                data[i][0] = estId_folio;
                data[i][1] = estRfc;
                data[i][2] = estEmpresa;
                data[i][3] = estProducto;
                data[i][4] = estCantidad;
                data[i][5] = estPrecio;
                data[i][6] = estSubtotal;
                data[i][7] = estTotal;

                Datos[0] = (String) data[i][0];
                Datos[1] = (String) data[i][1];
                Datos[2] = (String) data[i][2];
                Datos[3] = (String) data[i][3];
                Datos[4] = (String) data[i][4];
                Datos[5] = (String) data[i][5];
                Datos[6] = (String) data[i][6];
                Datos[7] = (String) data[i][7];
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
