/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.domain;

/**
 *
 * @author andri
 */
public class InfoAcademica {
    int id;
    String funcionario_identificacion;
    String universidad;
    String nivel_estudio;
    String titulo;

    public InfoAcademica() {
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

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getNivel_estudio() {
        return nivel_estudio;
    }

    public void setNivel_estudio(String nivel_estudio) {
        this.nivel_estudio = nivel_estudio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
}
