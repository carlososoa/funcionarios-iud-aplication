/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.controller;

import com.iudigital.funcionarios.iud.aplication.dao.InfoAcademicaDao;
import com.iudigital.funcionarios.iud.aplication.domain.Funcionario;
import com.iudigital.funcionarios.iud.aplication.domain.InfoAcademica;
import java.sql.SQLException;

/**
 *
 * @author andri
 */
public class InfoAcademicaController {
    
    private InfoAcademicaDao infoAcademicaDao;
    
    public InfoAcademicaController(){
        infoAcademicaDao = new InfoAcademicaDao();
        
    }
    
    public InfoAcademica obtenerInfoAcademicaFuncionario(String identificacion)throws SQLException{ 
       
        
        return  infoAcademicaDao.obtenerInfoAcademicaFuncionario(identificacion);
    }
    
    public void crearInfoAcademicaFuncionario(InfoAcademica infoAcademica)throws SQLException{ 
       
        
          infoAcademicaDao.crearInfoAcademicaFuncionario(infoAcademica);
    }
    
    public void actualiziarInfoAcademicaFuncionario( InfoAcademica infoAcademica)throws SQLException{
        infoAcademicaDao.actualizarInfoAcademicaFuncionario( infoAcademica);
    }
    
    
    
    
}
