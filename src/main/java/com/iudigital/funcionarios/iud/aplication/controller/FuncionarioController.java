/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.controller;

import com.iudigital.funcionarios.iud.aplication.dao.FuncionarioDao;
import com.iudigital.funcionarios.iud.aplication.domain.Funcionario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author andri
 */
public class FuncionarioController {
    
    private FuncionarioDao funcionarioDao;
    
    public FuncionarioController(){
        funcionarioDao = new FuncionarioDao();
    
    }
    
    public List<Funcionario> obtenerFuncionarios() throws SQLException{
        
        return funcionarioDao.obtenerFuncionarios();
    }
    
    public void crearFuncionario(Funcionario funcionario)throws SQLException{
        funcionarioDao.crearFuncionario(funcionario);    
    }
    
    public Funcionario obtenerFuncionario(String identificacion)throws SQLException{
        
        return funcionarioDao.obtenerFuncionario(identificacion);        
    }
    
    public void actualiziarFuncionario(int id , Funcionario funcionario)throws SQLException{
        funcionarioDao.actualizarFuncionario(id, funcionario);
    }
    
    public void eliminarFuncionario(int id) throws SQLException{
        funcionarioDao.eliminarFuncionario(id);    
    }
    
    public List<Funcionario>obtenerFuncionariosVista()throws SQLException{
        return funcionarioDao.obtenerFuncionariosVista();
    }
    
    
    
}
