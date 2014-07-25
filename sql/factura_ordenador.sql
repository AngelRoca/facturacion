-- MySQL dump 10.13  Distrib 5.5.37, for Win64 (x86)
--
-- Host: localhost    Database: factura_ordenador
-- ------------------------------------------------------
-- Server version	5.5.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `rfc` varchar(13) NOT NULL DEFAULT '',
  `nombre_cliente` varchar(45) NOT NULL,
  `domicilio` varchar(45) NOT NULL,
  `ciudad` varchar(20) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`rfc`),
  UNIQUE KEY `id_rfc:cliente_UNIQUE` (`rfc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES ('AIZL940206TYU','ARRIOJA','REG 300','CANCUN','Q.ROO','arrioja@gmail.com'),('CUKS930917AB1','SHARY CHUC','REG. 234 MZ5 LT. 12 AV. 20 NOV','CANCUN','Q. ROO','sharychuc@gmail.com');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factura` (
  `id_factura` int(11) NOT NULL AUTO_INCREMENT,
  `id_rfc_cliente` varchar(13) NOT NULL,
  `fecha_expedicion` date NOT NULL,
  `lugar_expedicion` varchar(30) NOT NULL,
  `forma_pago` varchar(20) NOT NULL,
  `cantidad` longtext NOT NULL,
  `descripcion` longtext NOT NULL,
  `precio_unitario` longtext NOT NULL,
  `importe` longtext NOT NULL,
  `subtotal` double NOT NULL,
  `iva` int(11) NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`id_factura`),
  UNIQUE KEY `id_factura_UNIQUE` (`id_factura`),
  UNIQUE KEY `id_rfc_cliente_UNIQUE` (`id_rfc_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `producto` varchar(45) NOT NULL,
  `precio` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`),
  UNIQUE KEY `id_producto_UNIQUE` (`id_producto`),
  UNIQUE KEY `producto_UNIQUE` (`producto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'IMPRESIONES B&N CARTA',2),(2,'IMPRESIONES B&N OFICIO',4),(3,'IMPRESIONES COLOR CARTA',3),(4,'IMPRESIONES COLOR OFICIO',6),(5,'COPIAS B&N CARTA',1),(6,'COPIAS B&N OFICIO',2),(7,'COPIAS COLOR CARTA',5),(8,'COPIAS COLOR OFICIO',8),(9,'SCANNER CARTA',3),(10,'SCANNER OFICIO',5);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `password` varchar(16) NOT NULL,
  `permisos` tinyint(4) NOT NULL,
  `sucursal` varchar(30) DEFAULT NULL,
  `notas` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_usuario_UNIQUE` (`id`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (24,'SHARY CHUC','12345678',1,NULL,'ESTUDIANTE'),(25,'FLOR LOPEZ','12345',0,'CANCUN','ESTUDIANTE'),(28,'JOSE ARRIOJA','12345',1,NULL,'ESTUDIANTE');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `id_folio` int(11) NOT NULL AUTO_INCREMENT,
  `rfc_cliente` varchar(13) DEFAULT NULL,
  `empresa` varchar(40) DEFAULT NULL,
  `producto` longtext,
  `cantidad` longtext,
  `precio_unitario` longtext,
  `subtotal` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `factura` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_folio`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (6,NULL,NULL,'COPIAS B&N OFICIO|','10|','2|',20,23.2,0),(7,'CUKS930917AB1','null','COPIAS COLOR OFICIO|IMPRESIONES COLOR CARTA|','15|7|','8|3|',141,163.56,1),(8,'CUKS930917AB1','SHARY CHUC','COPIAS COLOR OFICIO|IMPRESIONES COLOR OFICIO|','20|5|','8|6|',190,220.4,1),(9,NULL,NULL,'COPIAS B&N CARTA|COPIAS COLOR OFICIO|','10|23|','1|8|',194,225.04,0),(10,'CUKS930917AB1','SHARY CHUC','COPIAS COLOR CARTA|COPIAS COLOR OFICIO|COPIAS COLOR OFICIO|','4|2|20|','5|8|8|',196,227.36,1),(11,'AIZL940206TYU','ARRIOJA','COPIAS B&N OFICIO|COPIAS COLOR OFICIO|','10|20|','2|8|',180,208.8,1);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-25  0:03:53
