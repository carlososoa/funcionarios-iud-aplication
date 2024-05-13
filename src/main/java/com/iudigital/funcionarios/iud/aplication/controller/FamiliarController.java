/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.controller;

import com.iudigital.funcionarios.iud.aplication.dao.FamiliarDao;
import com.iudigital.funcionarios.iud.aplication.domain.Familiar;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author andri
 */
public class FamiliarController {
    
    private FamiliarDao familiarDao;
    
    public FamiliarController(){
        familiarDao = new FamiliarDao();
    
    }
    
    public List<Familiar> obtenerFamiliares(String funcionario_identificacion) throws SQLException{
        
        return familiarDao.obtenerFamiliares(funcionario_identificacion);
    }
    
    public void crearFamiliar(Familiar familiar)throws SQLException{
        System.out.println("hola Familiar Controler");
        
        familiarDao.crearFamiliar(familiar);
    }
    
}
