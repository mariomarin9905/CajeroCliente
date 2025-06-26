
package com.digis01.MMarinCajeroClient.Model;

public class Usuario {
   
    
    private int IdUsuario;
  
    private String Nombre;
    
    private String ApellidoPaterno;
   
    private String ApellidoMatreno;
   
    private String UserName;
   
    private String Password;
   
    private double Saldo;
    
    public Rol rol;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMatreno() {
        return ApellidoMatreno;
    }

    public void setApellidoMatreno(String ApellidoMatreno) {
        this.ApellidoMatreno = ApellidoMatreno;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
