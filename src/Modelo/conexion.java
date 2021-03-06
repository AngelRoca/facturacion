package Modelo;

import java.sql.*;
import Configuracion.variablesGenerales;

public class conexion {

    Connection con = null;
    variablesGenerales vg = new variablesGenerales();
    String bd, user, pass;
    public String registro_busqueda = "";
    public String registro_busqueda1 = "";
    public String registro_busqueda2 = "";
    public String registro_busqueda3 = "";
    public String registro_busqueda4 = "";
    public String registro_busqueda5 = "";

    public conexion() {
        bd = vg.baseDeDatos;
        user = vg.usuarioMysql;
        pass = vg.passMysql;
    }

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bd, user, pass);
            if (con != null) {
                System.out.println("**Conectados");
            }
        } catch (Exception e) {
            System.out.println("Error en la conexion:\n" + e);
        }
        return con;
    }

    public void desconectar() {
        con = null;
        System.out.println("**Desconectado");
    }

    // Recibe el nombre de la tabla, los campos a tratar, y los valores a agregar
    public boolean agregar(String tabla, String campos, String valores) {
        System.out.println("Entramos a agregar");
        String query = "INSERT INTO " + tabla;
        String[] camp = campos.split(",");
        int max = camp.length;

        query += campos(max, camp);
        System.out.println("Despues de query +  campos");
        query += valores(max);
        System.out.println(query);
        if (prepararEstados(query, valores)) {
            //Devuelve verdadero cuando ha surgido algun error
            System.out.println("agregar falso");
            return false;
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("agregar verdadero");
            return true;
        }
    }
    

    //Recibe el nombre de la tabla,los campos a tratar,los valores a actualiza y la clausula a cumplir
    public boolean actualizar(String tabla, String campos, String valores, String clausula) {
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
            System.out.println("actualizar falso");
            return false;
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("actualizar verdadero");
            return true;
        }
    }

    // Recibe el nombre de la tabla y la clausula que cumple que row eliminar
    public boolean eliminar(String tabla, String clausula) {
        String query = "DELETE FROM " + tabla + " WHERE " + clausula;
        if (prepararEstados(query, null)) {
            //Devuelve verdadero cuando ha surgido algun error
            System.out.println("eliminar falso");
            return false;
        } else {
            //Devuelve falso cuando todo ha salido bien
            System.out.println("eliminar verdadero");
            return true;
        }
    }
    
    

    public String[][] leerDatos(String tabla) {
        int rows, columns;
        if ((rows = getRows(tabla)) == -1) {
            return null;
        }
        if ((columns = getColumns(tabla)) == -1) {
            return null;
        }
        String[][] data = new String[rows][columns];
        String[] columnas = getColumnsNames(tabla);
        String query = "SELECT * FROM " + tabla;
        ResultSet res;

        try {
            if ((res = prepararEstados(query)) == null) {
                return null;
            }
            int i = 0;
            while (res.next()) {
                for (int j = 0; j < columns; j++) {
                    data[i][j] = res.getString(columnas[j]);
                }
                i++;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("Problemas en leerDatos(tabla):\n" + e);
            return null;
        }
        return data;
    }

    public String[][] leerDatos(String tabla, String campos, String condicion) {
        if (condicion == null) {
            condicion = "1";
        }
        int rows, columns;
        if ((rows = getRows(tabla)) == -1) {
            return null;
        }

        String[] columnas = campos.split(",");
        columns = columnas.length;
        String[][] data = new String[rows][columns];
        String query = "SELECT " + campos + " FROM " + tabla + " WHERE " + condicion;

        ResultSet res;
        try {
            if ((res = prepararEstados(query)) == null) {
                return null;
            }
            int i = 0;
            while (res.next()) {
                for (int j = 0; j < columns; j++) {
                    data[i][j] = res.getString(columnas[j]);
                }
                i++;
            }
            if (data[0][0] == null) {
                return null;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("Problemas en leerDatos(campos,tabla,condicion: ):\n" + e);
            return null;
        }
        return data;
    }

    // Recoje el query predesarrollado y los valores a manejar en una query de mysql
    // y posteriormente los ejecuta.
    // Si values viene como null, esta echo para ELIMINAR
    private boolean prepararEstados(String query, String values) {
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
                pstm.close();
            } catch (Exception e) {
                System.out.println("Problemas de sincronizacion con la base de datos:\n" + e);
            }
        } else {
            try {
                PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
                r = pstm.execute();
                pstm.close();
            } catch (Exception e) {
                System.out.println("Problemas al preparar estados:\n" + e);
            }
        }
        desconectar();
        return r;
    }

    private ResultSet prepararEstados(String query) {
        ResultSet res = null;
        try {
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
            res = pstm.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problemas al preparar estados en leerDatos:\n" + e);
        }
        desconectar();
        return res;
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

    private int getRows(String tabla) {
        int rows = -1;
        try {
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement("SELECT count(1) as total FROM " + tabla);
            ResultSet res = pstm.executeQuery();
            res.next();
            rows = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println("Problemas al obtener numero de Filas: " + e);
        }
        return rows;
    }

    private int getColumns(String tabla) {
        int columns = -1;
        String query = "SELECT Count(*) as total FROM INFORMATION_SCHEMA.Columns where TABLE_NAME='" + tabla + "' AND table_schema='" + vg.baseDeDatos + "'  GROUP BY column_default";
        try {
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
            ResultSet res = pstm.executeQuery();
            res.next();
            columns = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println("Problemas al obtener el numero de columnas:\n" + e);
        }
        return columns;
    }

    private String[] getColumnsNames(String tabla) {
        String[] columns = new String[getColumns(tabla)];
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name='" + tabla + "' AND table_schema='" + vg.baseDeDatos + "' GROUP BY column_name ORDER BY column_default";
        try {
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement(query);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                columns[i] = res.getString("column_name");
                i++;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("Problemas al obtener el nombre de las columnas:\n" + e);
        }
        return columns;
    }

    public void busquedaCliente(String tabla, String campo, String clausula) {
        try {
            registro_busqueda = "";
            registro_busqueda1 = "";
            registro_busqueda2 = "";
            registro_busqueda3 = "";
            registro_busqueda4 = "";
            registro_busqueda5 = "";
            PreparedStatement pstm = (PreparedStatement) conectar().prepareStatement("SELECT " + campo + " AS RFC, nombre_cliente AS NOM, domicilio AS DOM, ciudad AS CI, estado AS EST, email AS EM FROM " + tabla + " WHERE " + campo + "= '" + clausula + "';");
            ResultSet res = pstm.executeQuery();
            res.next();
            registro_busqueda = res.getString("RFC");
            registro_busqueda1 = res.getString("NOM");
            registro_busqueda2 = res.getString("DOM");
            registro_busqueda3 = res.getString("CI");
            registro_busqueda4 = res.getString("EST");
            registro_busqueda5 = res.getString("EM");
            res.close();
        } catch (Exception e) {

        }

    }
}
