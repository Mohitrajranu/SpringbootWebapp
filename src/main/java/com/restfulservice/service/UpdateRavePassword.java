package com.restfulservice.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.restfulservice.util.BizUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateRavePassword.
 * @author Mohit Raj
 */
@Service
public class UpdateRavePassword {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(UpdateRavePassword.class);
	
	/**
	 * Update record set.
	 *
	 * @param password the password
	 * @param email the email
	 */
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
        	log.error("Problem in loading MySQL JDBC driver" +cnfex.getMessage());
           // cnfex.printStackTrace();
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
 
           log.info(sqlQueryResult + 
                     " Rave update successfully");
        }
        catch(SQLException sqlex){
        	log.error("Problem in updation rave" +sqlex.getMessage());
          //  sqlex.printStackTrace();
        }
        finally {
 
            // Step 3: Closing database connection
            try {
                if(null != connection) {
 
                    // cleanup resources, once after processing
                	if(null != preparedStatement){
                	preparedStatement.clearParameters();
                    preparedStatement.close();
                	}
                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
            	log.error("Problem in connection closing" +sqlex.getMessage());
                //sqlex.printStackTrace();
            }
	}
}
}