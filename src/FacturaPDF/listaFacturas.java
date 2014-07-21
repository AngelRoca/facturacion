/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FacturaPDF;

/**
 *
 * @author Shary
 */
public class listaFacturas {
    private String folio;
    private String rfc;
    private String empresa;
    private String cantidad;
    private String producto;
    private String precio;
    private String subtotal;
    private String total;

    public listaFacturas(String folio, String rfc, String empresa, String cantidad, String producto, String precio, String subtotal, String total) {
        this.folio = folio;
        this.rfc = rfc;
        this.empresa = empresa;
        this.cantidad = cantidad;
        this.producto = producto;
        this.precio = precio;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    
    
}
