package com.codebind;

import com.sun.source.tree.CatchTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.awt.*;
import javax.swing.table.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainForm {

    private JPanel panelMain;
    private JRadioButton radioButtonInsert;
    private JRadioButton radioButtonChange;
    private JTable tableDoc;
    private JTextField textFieldNumDoc;
    private JTextField textFieldDateCreation;
    private JTextField textFieldFile;
    private JTextField textFieldNotice;
    private JButton buttonExec;
    private JRadioButton radioButtonDelete;
    public JFrame mainForm = new JFrame("Doc");
    ButtonGroup radioButtonGroup = new ButtonGroup();

    public MainForm(Connection connection) {
        Vector columnNames = new Vector();
        Vector data = new Vector();

        try {
            //  прочитаем всё из таблицы
            String sql = "Select * from DOCTABLE"; //faef
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  получим имена колонок
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            //  считаем ряды
            while (rs.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }

        //  создадим таблицу с данными из БД
        tableDoc= new JTable(data, columnNames) {
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);
                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane(tableDoc);
        mainForm.getContentPane().add(tableDoc);
        JPanel buttonPanel = new JPanel();
        mainForm.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        radioButtonGroup.add(radioButtonInsert);
        radioButtonGroup.add(radioButtonChange);
        radioButtonGroup.add(radioButtonDelete);

        buttonExec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButtonInsert.isSelected())
                {
                    JOptionPane.showMessageDialog(null,"insert");
                }else if(radioButtonChange.isSelected())
                {
                    JOptionPane.showMessageDialog(null,"Change");
                }else if(radioButtonDelete.isSelected())
                {
                    JOptionPane.showMessageDialog(null,"Delete");
                }
            }
        });
    }



    public void showMainForm(Connection connection)
    {
        mainForm.setContentPane(new MainForm(connection).panelMain);
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.pack();
        mainForm.setVisible(true);
    }





}


