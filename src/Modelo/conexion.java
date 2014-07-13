package Modelo;

import java.sql.*;
import Configuracion.variablesGenerales;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.shape.Path;
import javax.swing.JOptionPane;

public class conexion {

    private int registros;
    Connection con = null;
    variablesGenerales vg = new variablesGenerales();

    public conexion() {
        String bd, user, pass;
        bd = vg.baseDeDatos;
        user = vg.usuarioMysql;
        pass = vg.passMysql;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bd, user, pass);
            if (con != null) {
                System.out.println("Conectados");
            }
        } catch (Exception e) {
            System.out.println("Error en la conexion:\n" + e);
        }
    }

    public Connection conectar() {
        return con;
    }

    public void desconectar() {
        con = null;
    }

    // Recibe el nombre de la tabla, los campos a tratar, y los valores a agregar
    public void agregar(String tabla, String campos, String valores) {
        String query = "INSERT INTO " + tabla;
        String[] camp = campos.split(",");
        int max = camp.length;

        query += campos(max, camp);
        query += valores(max);

        if (prepararEstados(query, valores)) {
            //Devuelve verdadero cuando ha surgido algun error
            System.out.println("Esto me dio falso");
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("Esto me dio verdadero");
        }
    }

    //Recibe el nombre de la tabla,los campos a tratar,los valores a actualiza y la clausula a cumplir
    public void actualizar(String tabla, String campos, String valores, String clausula) {
        String[] camp = campos.split(",");
        int max = camp.length;
        String c;
        String query = "UPDATE " + tabla + " SET ", aux = "";
        if (clausula == null) {
            c = "1";
        } else {
            c = clausula;
        }
        for (int i = 0; i < max; i++) {
            aux += camp[i] + "=?";
            if (i != max - 1) {
                aux += ",";
            }
        }
        query += aux + " WHERE " + c;

        if (prepararEstados(query, valores)) {
            //Devuelve verdadero cuando ha surgido algun error
            System.out.println("Esto me dio falso");
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("Esto me dio verdadero");
        }
    }

    // Recibe el nombre de la tabla y la clausula que cumple que row eliminar
    public void eliminar(String tabla, String clausula) {
        String query = "DELETE FROM " + tabla + " WHERE " + clausula;
        if (prepararEstados(query, null)) {
            //Devuelve verdadero cuando ha surgido algun error
            System.out.println("Esto me dio falso");
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("Esto me dio verdadero");
        }
    }

    public void busqueda(String tabla, String clausula) {
        String query;
        registros=0;
        try {
            query = "SELECT count(1) as total FROM " + tabla + " WHERE id_rfc_cliente = " + clausula;
            verificar(query);
//            if (registros == 0) {
//                JOptionPane.showMessageDialog(null, registros);
//
//            } else {
//                JOptionPane.showMessageDialog(null, registros);
//            }

        } catch (Exception e) {

        }

    }

    public void verificar(String dato) {
        try {
            String query = dato;
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
//            JOptionPane.showMessageDialog(null, pstm);
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
              JOptionPane.showMessageDialog(null,registros);
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Recoje el query predesarrollado y los valores a manejar en una query de mysql
    // y posteriormente los ejecuta.
    // Si values viene como null, esta echo para ELIMINAR
    public boolean prepararEstados(String query, String values) {
        boolean r = true;
        String[] val = null;
        int max;
        if (values != null) {
            val = values.split(",");
            max = val.length;

            try {
                PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
                for (int i = 0; i < max; i++) {
                    pstm.setString(i + 1, val[i]);
                }
                r = pstm.execute();
                System.out.println(pstm);
                pstm.close();
            } catch (Exception e) {
                System.out.println("Problemas de sincronizacion con la base de datos:\n " + e);
            }
        } else {
            try {
                PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
                r = pstm.execute();
                pstm.close();
            } catch (Exception e) {
                System.out.println("PREPARAR ESTADOS. " + e);
            }
        }
        desconectar();
        return r;
    }

    private String campos(int max, String[] camp) {
        String str = "(";
        for (int i = 0; i < max; i++) {
            str += camp[i];
            if (i != max - 1) {
                str += ",";
            }
        }
        return str;
    }

    private String valores(int max) {
        String str = ") VALUES (";
        for (int i = 0; i < max; i++) {
            str += "?";
            if (i != max - 1) {
                str += ",";
            }
        }
        str += ")";
        return str;
    }
}
