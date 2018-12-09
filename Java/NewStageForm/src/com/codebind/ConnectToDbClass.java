package com.codebind;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDbClass {
    public boolean connectionToDb(String userName,String passwordUser)
    {
        boolean isConnected;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").getDeclaredConstructor().newInstance();
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",userName,passwordUser);
            isConnected = true;

        }
        catch (Exception ex)
        {
            System.out.println(ex);
            isConnected = false;
        }

        return isConnected;
    }


}








