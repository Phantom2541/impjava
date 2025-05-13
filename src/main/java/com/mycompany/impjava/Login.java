package com.mycompany.impjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.event.*;
import com.mycompany.impjava.Register;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        button1 = new java.awt.Button();
        jPasswordField1 = new javax.swing.JPasswordField();
        showPasswordCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(78, 16, 164));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setFont(new java.awt.Font("Imprint MT Shadow", 1, 48));
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("IMP Library");

        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("__________________________________________________________");

        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("__________________________________________________________");

        jLabel4.setFont(new java.awt.Font("UD Digi Kyokasho NK", 0, 13));
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("We strive to satisfy our users and ask for nothing in return");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 18));
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Email :");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18));
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Log In");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 18));
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("Password:");

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 13));
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setText("Don’t have an account?");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 13));
        jLabel9.setForeground(new java.awt.Color(51, 255, 102));
        jLabel9.setText("Register Here");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        button1.setActionCommand("Login");
        button1.setBackground(new java.awt.Color(0, 255, 102));
        button1.setFont(new java.awt.Font("Verdana", 1, 18));
        button1.setForeground(new java.awt.Color(0, 51, 0));
        button1.setLabel("Login");
        button1.setName("Login");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        showPasswordCheckBox.setText("Show Password");
        showPasswordCheckBox.setBackground(new java.awt.Color(78, 16, 164));
        showPasswordCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        showPasswordCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(showPasswordCheckBox)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel8)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel9))
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(137, 137, 137)
                                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(181, 181, 181)
                                                .addComponent(jLabel6)))
                                .addGap(0, 47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(showPasswordCheckBox)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9))
                                .addGap(30, 30, 30)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {
        String email = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        String sql = "SELECT password, role FROM users WHERE email = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");

                if (password.equals(dbPassword)) {
                    JOptionPane.showMessageDialog(this, "✅ Login Successful! Welcome, " + email);

                    if ("librarian".equalsIgnoreCase(role)) {
                        Dashboard dashboard = new Dashboard();
                        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        dashboard.setVisible(true);
                        dashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    } else {
                        stuSearchBook studentDashboard = new stuSearchBook();
                        studentDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        studentDashboard.setVisible(true);
                        studentDashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email not found!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showPasswordCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        if (showPasswordCheckBox.isSelected()) {
            jPasswordField1.setEchoChar((char) 0); // show password
        } else {
            jPasswordField1.setEchoChar('*'); // hide password
        }
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {
        Register register = new Register();
        register.setVisible(true);
        register.pack();
        register.setLocationRelativeTo(null);
        register.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox showPasswordCheckBox;
    // End of variables declaration
}
