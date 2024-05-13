/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iudigital.funcionarios.iud.aplication.presentacion;

import com.iudigital.funcionarios.iud.aplication.controller.FuncionarioController;
import com.iudigital.funcionarios.iud.aplication.domain.Funcionario;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author andri
 */
public class FuncionariosIudAplication {
    
    public static void obtenerFuncionarios(FuncionarioController funcionarioController){
        try {
            List<Funcionario> funcionarios = funcionarioController.obtenerFuncionariosVista();
            
            if(funcionarios.isEmpty()){
                System.out.println("No hay funcionarios");}
            else{
                System.out.println("si hay");
                funcionarios.forEach(funcionario ->{
                    System.out.println(funcionario.getId());                    
                    System.out.println(funcionario.getId_tipo_documento());
                    System.out.println(funcionario.getFuncionario_identificacion());
                    System.out.println(funcionario.getNombres());
                    System.out.println(funcionario.getApellidos());
                    System.out.println(funcionario.getId_estado_civil());
                    System.out.println(funcionario.getSexo());
                    System.out.println(funcionario.getDireccion());
                    System.out.println(funcionario.getTelefono());
                    System.out.println(funcionario.getFecha_nacimiento());
                    System.out.println(funcionario.getEstado_civil());
                    System.out.println(funcionario.getTipo_documento());
                    System.out.println("//////");
                    
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        Vista1 vista = new Vista1();
        
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        
        
        
        FuncionarioController funcionarioController = new FuncionarioController();
        obtenerFuncionarios(funcionarioController);
        
    }
}
