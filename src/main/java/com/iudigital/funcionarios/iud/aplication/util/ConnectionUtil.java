/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author andri
 */
public class ConnectionUtil {
    
    private static final String URL = "jdbc:mysql://localhost:3306/funcionarios_iud";
    
    private static final String USER = "root";
    
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException{
        
      return DriverManager.getConnection(URL,USER,PASSWORD);
    
    }
}
