package com.codebind;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AuthForm {
    private JPasswordField passwordFieldPass;
    private JTextField textFieldLogin;
    private JPanel authPanel;
    private JButton buttonExec;
    private JFrame authFrame = new JFrame();


    public AuthForm() {
        buttonExec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authFrame.setVisible(false);
                try
                {
                    DBPool pool = new DBPool("jdbc:oracle:thin:@localhost:1521:orcl",textFieldLogin.getText(),String.valueOf(passwordFieldPass.getPassword()));
                    //Connection connect = getConnection(textFieldLogin.getText(),String.valueOf(passwordFieldPass.getPassword()));

                    MainForm mainForm = new MainForm(pool.getConnection());

                    mainForm.showMainForm(pool.getConnection());
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
    }

    public void showAuthForm()
    {
        authFrame.setContentPane(new AuthForm().authPanel);
        authFrame.setDefaultCloseOperation(authFrame.EXIT_ON_CLOSE);
        authFrame.pack();
        authFrame.setVisible(true);

    }
    public Connection getConnection(String user, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",user, password);
        return conn;

    }



}

