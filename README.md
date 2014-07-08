facturacion
===========

Proyecto de facturacion de un Centro de copiado para la materia DIseño de Sistemas

Los integrantes de nuestro equipo son:
Shary Chuc Uc, Arrioja Zamudio, Angel Roca

===================================================================================================

INSTRUCCIONES DE USO

Paquetes de uso general:

-Configuracion --
import Configuracion.variablesGenerales;
clase variablesGenerales    
En esta clase se deben de configurar las variables de la base de datos
de cada Desarrollador (Mas instrucciones en los comentarios de la clase)


-Modelo --
import Modelo.conexion;       
Para puder utilizar la clase conexion del Modelo, junto con sus funciones
  
  
  conexion con=new conexion();
  Crea el objeto conexion, para acceder a los datos de la base de datos
  El objeto conexion obtiene sus valores del paquete Configuracion  
  
  
  con.agregar(String tabla,String campos,String valores); 
  Recibe el nombre de la tabla, los campos a tratar y los valores a agregar
  
  
  con.actualizar(String tabla,String campos,String valores,String clausula); 
  Recibe el nombre de la tabla los campos a tratar,los valores a actualiza 
  y la clausula a cumplir


  con.eliminar(String tabla,String clausula)
  Recibe el nombre de la tabla y la clausula que cumple que row eliminar
    
