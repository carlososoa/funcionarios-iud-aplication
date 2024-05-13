/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.dao;

import com.iudigital.funcionarios.iud.aplication.domain.InfoAcademica;
import com.iudigital.funcionarios.iud.aplication.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andri
 */
public class InfoAcademicaDao {

    private static final String GET_INFO_ACADEMICA_BY_IDENTIFICACION = "select * from info_academica where funcionario_identificacion = ?";

    private static final String UPDATE_INFO_ACADEMICA = "UPDATE info_academica"
            + " SET universidad = ? , nivel_estudio = ? , titulo = ? "
            + " WHERE funcionario_identificacion = ? ";

    private static final String CREAR_INFO_ACADEMICA = "INSERT INTO info_academica"
            + " (funcionario_identificacion, universidad, nivel_estudio, titulo)"
            + " VALUES( ?, ?, ?, ?) ";

    public InfoAcademica obtenerInfoAcademicaFuncionario(String identificacion) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_INFO_ACADEMICA_BY_IDENTIFICACION);
            preparedStatement.setString(1, identificacion);
            resultSet = preparedStatement.executeQuery();
            InfoAcademica infoAcademica = new InfoAcademica();

            if (resultSet.next()) {

                infoAcademica.setId(resultSet.getInt("id"));
                infoAcademica.setFuncionario_identificacion(resultSet.getString("funcionario_identificacion"));
                infoAcademica.setUniversidad(resultSet.getString("universidad"));
                infoAcademica.setNivel_estudio(resultSet.getString("nivel_estudio"));
                infoAcademica.setTitulo(resultSet.getString("titulo"));

            }
            return infoAcademica;
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

    public void crearInfoAcademicaFuncionario(InfoAcademica infoAcademica) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(CREAR_INFO_ACADEMICA);
            preparedStatement.setString(1, infoAcademica.getFuncionario_identificacion());
            preparedStatement.setString(2, infoAcademica.getUniversidad());
            preparedStatement.setString(3, infoAcademica.getNivel_estudio());
            preparedStatement.setString(4, infoAcademica.getTitulo());

            preparedStatement.executeUpdate();

        } finally {

            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }

    }

    public void actualizarInfoAcademicaFuncionario(InfoAcademica infoAcademica) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(UPDATE_INFO_ACADEMICA);
            preparedStatement.setString(1, infoAcademica.getUniversidad());
            preparedStatement.setString(2, infoAcademica.getNivel_estudio());
            preparedStatement.setString(3, infoAcademica.getTitulo());
            preparedStatement.setString(4, infoAcademica.getFuncionario_identificacion());

            preparedStatement.executeUpdate();

        } finally {

            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }

    }

}
