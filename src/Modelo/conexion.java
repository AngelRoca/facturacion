package Modelo;

import java.sql.*;
import Configuracion.variablesGenerales;

public class conexion {
    
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
        //System.out.println(query);
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
        if (clausula == null)
            c = "1";
        else
            c = clausula;
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
    
    public Object[][] leerDatos(String tabla){
        int rows,columns;
        if((rows=getRows(tabla))==-1)return null;
        if((columns=getColumns(tabla))==-1)return null;
        Object[][] data = new String[rows][columns];
        String[] columnas=getColumnsNames(tabla);
        String query="SELECT * FROM "+tabla;
        ResultSet res;
        
        try{if((res=prepararEstados(query))==null)return null;
            int i=0;
            while(res.next()){
                for(int j=0;j<columns;j++){
                    data[i][j]=res.getString(columnas[j]);
                }
                i++;
            }  
            res.close();
        }catch(Exception e){
            System.out.println("Problemas en leerDatos(tabla):\n"+e);
            return null;
        }
        return data;
    }
    
    public Object [][] leerDatos(String tabla,String campos,String condicion){
        if(condicion==null)condicion="1";
        int rows,columns;
        if((rows=getRows(tabla))==-1)
            return null;
        String[] columnas=campos.split(",");
        columns=columnas.length;
        Object[][] data = new String[rows][columns];
        String query="SELECT "+campos+" FROM "+tabla+" WHERE "+condicion;

        ResultSet res;
        try{ if((res=prepararEstados(query))==null)return null;
            int i = 0;
            while(res.next())
            {
                for(int j=0;j<columns;j++){
                    data[i][j]=res.getString(columnas[j]);
                }
             i++;
            }
        res.close();
        }catch(SQLException e){
        System.out.println("Problemas en leerDatos(campos,tabla,condicion: ):\n"+e);
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
                System.out.println(pstm);
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
    
    private ResultSet prepararEstados(String query){
        ResultSet res=null;
        try{
            PreparedStatement pstm = (PreparedStatement)conectar().prepareStatement(query);
            res = pstm.executeQuery();
        }catch(SQLException e){
        System.out.println("Problemas al preparar estados en leerDatos:\n"+e);
        }
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
    
    private int getRows(String tabla){
        int rows = -1;
        try{
           PreparedStatement pstm=(PreparedStatement)conectar().prepareStatement("SELECT count(1) as total FROM "+tabla);
           ResultSet res = pstm.executeQuery();
           res.next();
           rows = res.getInt("total");
           res.close();
           }
         catch(SQLException e)
         {
           System.out.println("Problemas al obtener numero de Filas: "+e);
         }
        return rows;
    }
    
    private int getColumns(String tabla){
        int columns=-1;
        String query="SELECT Count(*) as total FROM INFORMATION_SCHEMA.Columns where TABLE_NAME='"+tabla+"' AND table_schema='"+vg.baseDeDatos+"'  GROUP BY column_default";
        try{
           PreparedStatement pstm=(PreparedStatement)conectar().prepareStatement(query);
           ResultSet res = pstm.executeQuery();
           res.next();
           columns = res.getInt("total");
           res.close();
           }
         catch(SQLException e)
         {
           System.out.println("Problemas al obtener el numero de columnas:\n"+e);
         }
        return columns;
    }
    private String[] getColumnsNames(String tabla){
        String[] columns=new String[getColumns(tabla)];
        String query="SELECT column_name FROM information_schema.columns WHERE table_name='"+tabla+"' AND table_schema='"+vg.baseDeDatos+"' GROUP BY column_name ORDER BY column_default";
        try{
           PreparedStatement pstm=(PreparedStatement)conectar().prepareStatement(query);
           ResultSet res = pstm.executeQuery();
           int i=0;
                while(res.next()){
                columns[i]=res.getString("column_name");
                i++;
                }
           res.close();
           }
         catch(Exception e)
         {
           System.out.println("Problemas al obtener el nombre de las columnas:\n"+e);
         }
        return columns;
    }
}