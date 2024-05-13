/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.dao;

import com.iudigital.funcionarios.iud.aplication.domain.Familiar;
import com.iudigital.funcionarios.iud.aplication.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andri
 */
public class FamiliarDao {
    
    private static final String AGREGAR_FAMILIAR = "INSERT INTO familiares"
            +"(funcionario_identificacion,nombres,apellidos,cedula,rol)"
            +"VALUES (?,?,?,?,?)";
    
    private static final String GET_FAMILIARES_BY_FUNCIONARIO_IDENTIFICACION ="select * from familiares where funcionario_identificacion = ?";
    
    
    public List<Familiar> obtenerFamiliares(String funcionario_identificacion) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Familiar> familiares = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FAMILIARES_BY_FUNCIONARIO_IDENTIFICACION);
            preparedStatement.setString(1, funcionario_identificacion);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Familiar familiar = new Familiar();
                
                familiar.setId(resultSet.getInt("id"));
                familiar.setFuncionario_identificacion(resultSet.getString("funcionario_identificacion"));
                familiar.setNombres(resultSet.getString("nombres"));
                familiar.setApellidos(resultSet.getString("apellidos"));
                familiar.setCedula(resultSet.getString("cedula"));
                familiar.setRol(resultSet.getString("rol"));
                
                
                familiares.add(familiar);
            }
            System.out.println("hola familiarDao");
            return familiares;
        } finally {

            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

    }
    public void crearFamiliar(Familiar familiar) throws SQLException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(AGREGAR_FAMILIAR);
            
            preparedStatement.setString(1,familiar.getFuncionario_identificacion());
            preparedStatement.setString(2,familiar.getNombres());
            preparedStatement.setString(3,familiar.getApellidos());
            preparedStatement.setString(4,familiar.getCedula());
            preparedStatement.setString(5,familiar.getRol());             
            preparedStatement.executeUpdate();
            
        }finally{
            
            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            
        }
        
        
    }
    
} 
