package com.digis01.MMarinCajeroClient.Model;


public class Fondo {
    private int IdFondo;
    private String Tipo;
    private int Cantidad;
    private double Denominacion;
    
    public int getIdFondo() {
        return IdFondo;
    }
    public void setIdFondo(int IdFondo) {
        this.IdFondo = IdFondo;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getDenominacion() {
        return Denominacion;
    }

    public void setDenominacion(double Denominacion) {
        this.Denominacion = Denominacion;
    }
}
