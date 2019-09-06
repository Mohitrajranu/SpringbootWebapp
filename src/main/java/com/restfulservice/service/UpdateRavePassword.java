package com.restfulservice.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.restfulservice.util.BizUtil;

@Service
public class UpdateRavePassword {

	
	public void updateRecordSet(String password,String email){
		// variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BizUtil biz = null;
        String encryptpaaswd= null;
        // Step 1: Loading or registering MySQL JDBC driver class
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem in loading MySQL JDBC driver");
            cnfex.printStackTrace();
        }
 
        // Step 2: Opening database connection
        try {
        	biz = new BizUtil();
        	encryptpaaswd = biz.encrypt(password);
            // Step 2.A: Create and get connection using DriverManager
            connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/rave2", "root", "password"); 
 
            // create SQL query to update PLAYER info
            /*
            update person set password = 'bizlem12!@',encrypt_password = '877c2ed8c784cf8823c424b65893b33d' 
            where Email = 'leadautoconvert@gmail.com';
            */
            String sqlUpdateQuery = 
                       "update person set password = ?,encrypt_password = ? WHERE email = ?";
 
            // Step 2.B: Creating JDBC Statement 
        preparedStatement = connection.prepareStatement(sqlUpdateQuery);
 
            // set values for PreparedStatement for respective ?
            preparedStatement.setString(1, password);    // AGE
            preparedStatement.setString(2, encryptpaaswd);     // PLAYER_ID
            preparedStatement.setString(3, email);
            // Step 2.C: Executing SQL & retrieve data into ResultSet
            int sqlQueryResult = preparedStatement.executeUpdate();
 
            System.out.println(sqlQueryResult + 
                     " indicates PLAYER info updation is successful");
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
 
            // Step 3: Closing database connection
            try {
                if(null != connection) {
 
                    // cleanup resources, once after processing
                	preparedStatement.clearParameters();
                    preparedStatement.close();
 
                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
	}
}
}