/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.domain;

/**
 *
 * @author andri
 */
public class Familiar {
    int id;
    String funcionario_identificacion;
    String nombres;
    String apellidos;
    String cedula;
    String rol;

    public Familiar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getFuncionario_identificacion() {
        return funcionario_identificacion;
    }

    public void setFuncionario_identificacion(String funcionario_identificacion) {
        this.funcionario_identificacion = funcionario_identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}
