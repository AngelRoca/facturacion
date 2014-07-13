package Modelo;

import java.sql.*;
import Configuracion.variablesGenerales;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class conexion {

    private int registros;
    private String registro_busqueda;
    private String registro_precio;
    
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
    
     public void busqueda_producto(String id, JTextField produc, JTextField precio) {
        try {
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement("SELECT producto as produc, precio as price from productos where id_producto= " + id + ";");
            ResultSet res = pstm.executeQuery();
            res.next();
            registro_busqueda = res.getString("produc");
            registro_precio = res.getString("price");
            res.close();
//            JOptionPane.showMessageDialog(null, registro_busqueda + " " + registro_precio);
            produc.setText(registro_busqueda);
            precio.setText(registro_precio);
        } catch (Exception e) {
            
        }
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
