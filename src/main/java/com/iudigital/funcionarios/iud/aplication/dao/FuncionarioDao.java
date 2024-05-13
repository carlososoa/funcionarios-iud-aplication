/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.dao;

import com.iudigital.funcionarios.iud.aplication.domain.Funcionario;
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
public class FuncionarioDao {

    private static final String GET_FUNCIONARIOS = "select * from funcionarios f ";

    private static final String CREATE_FUNCIONARIO = "INSERT INTO funcionarios"
            + " (id_tipo_documento, funcionario_identificacion, nombres, apellidos, id_estado_civil, sexo, direccion, telefono, fecha_nacimiento)"
            + " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    private static final String GET_FUNCIONARIO_BY_IDENTIFICACION = "select * from funcionarios where funcionario_identificacion = ?";

    private static final String UPDATE_FUNCIONARIO = "UPDATE funcionarios"
            + " SET id_tipo_documento=?,funcionario_identificacion = ?, nombres=?, apellidos=?,"
            + " id_estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=?"
            + " WHERE id=?";

    private static final String DELETE_FUNCIONARIO = "delete from funcionarios where id = ?";
    
    private static final String GET_FUNCIONARIOS_VISTA ="SELECT f.id , f.id_tipo_documento, t.tipo_documento, f.funcionario_identificacion,"+
            " f.nombres,f.apellidos,f.id_estado_civil, ec.estado_civil, f.sexo, f.direccion, f.telefono, f.fecha_nacimiento "+
            " FROM funcionarios f JOIN tipo_documento t ON f.id_tipo_documento = t.id_tipo_documento "+
            " JOIN estado_civil ec ON f.id_estado_civil = ec.id_estado_civil";

    public List<Funcionario> obtenerFuncionarios() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setId_tipo_documento(resultSet.getInt("id_tipo_documento"));
                funcionario.setFuncionario_identificacion(resultSet.getString("funcionario_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setId_estado_civil(resultSet.getInt("id_estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFecha_nacimiento(resultSet.getString("fecha_nacimiento"));
                funcionarios.add(funcionario);
            }
            return funcionarios;
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
    
    public List<Funcionario> obtenerFuncionariosVista() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS_VISTA);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setId_tipo_documento(resultSet.getInt("id_tipo_documento"));
                funcionario.setFuncionario_identificacion(resultSet.getString("funcionario_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setId_estado_civil(resultSet.getInt("id_estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFecha_nacimiento(resultSet.getString("fecha_nacimiento"));
                funcionario.setTipo_documento(resultSet.getString("tipo_documento"));
                funcionario.setEstado_civil(resultSet.getString("estado_civil"));
                funcionarios.add(funcionario);
            }
            return funcionarios;
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
    
    
    
    public void crearFuncionario(Funcionario funcionario) throws SQLException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(CREATE_FUNCIONARIO);
            preparedStatement.setInt(1,funcionario.getId_tipo_documento());
            preparedStatement.setString(2,funcionario.getFuncionario_identificacion());
            preparedStatement.setString(3,funcionario.getNombres());
            preparedStatement.setString(4,funcionario.getApellidos());
            preparedStatement.setInt(5,funcionario.getId_estado_civil());
            preparedStatement.setString(6,funcionario.getSexo());
            preparedStatement.setString(7,funcionario.getDireccion());
            preparedStatement.setString(8,funcionario.getTelefono());
            preparedStatement.setString(9,funcionario.getFecha_nacimiento()); 
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
    
    public Funcionario obtenerFuncionario(String identificacion) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_IDENTIFICACION);
            preparedStatement.setString(1, identificacion);
            resultSet = preparedStatement.executeQuery();
            Funcionario funcionario = new Funcionario();
            
            if (resultSet.next()) {               
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setId_tipo_documento(resultSet.getInt("id_tipo_documento"));
                funcionario.setFuncionario_identificacion(resultSet.getString("funcionario_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setId_estado_civil(resultSet.getInt("id_estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFecha_nacimiento(resultSet.getString("fecha_nacimiento"));

            }
            return funcionario;
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
    
    public void actualizarFuncionario (int id, Funcionario funcionario) throws SQLException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(UPDATE_FUNCIONARIO);
            preparedStatement.setInt(1,funcionario.getId_tipo_documento());
            preparedStatement.setString(2,funcionario.getFuncionario_identificacion());
            preparedStatement.setString(3,funcionario.getNombres());
            preparedStatement.setString(4,funcionario.getApellidos());
            preparedStatement.setInt(5,funcionario.getId_estado_civil());
            preparedStatement.setString(6,funcionario.getSexo());
            preparedStatement.setString(7,funcionario.getDireccion());
            preparedStatement.setString(8,funcionario.getTelefono());
            preparedStatement.setString(9,funcionario.getFecha_nacimiento());
            preparedStatement.setInt(10,funcionario.getId());
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
    
    public void eliminarFuncionario (int id ) throws SQLException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(DELETE_FUNCIONARIO);           
            preparedStatement.setInt(1,id);
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
