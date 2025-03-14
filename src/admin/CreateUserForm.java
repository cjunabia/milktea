/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Startups.Coffeeshop;
import config.dbConnect;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DANIEL FAILADONA
 */
public class CreateUserForm extends javax.swing.JFrame {

    /**
     * Creates new form CreateUserForm
     */
    public CreateUserForm() {
        initComponents();
    }
    
       private String userId; // Declare userId at the class level

    public void setUserId(String id) {
        this.userId = id; // Store the user ID for later use
    }
    
    
    
    public static String mail,usname;
    
    public boolean duplicateCheck()
    {
        dbConnect dbc = new dbConnect();
    try {
        String query = "SELECT * FROM users WHERE RegUser = '" + un.getText() + "' OR email = '" + em.getText() + "'";
        ResultSet resultSet = dbc.getData(query);

        if (resultSet.next()) {
            String email = resultSet.getString("email"); 
            if (email.equals(em.getText())) {
                JOptionPane.showMessageDialog(null, "Email is Already Used!");
                em.setText(""); 
            }

            String username = resultSet.getString("RegUser"); 
            if (username.equals(un.getText())) {
                JOptionPane.showMessageDialog(null, "Username is Already Used!");
                un.setText(""); 
            }
            return true;
        } else {
            return false;
        }
    } catch (SQLException ex) {
        System.out.println("" + ex);
        return false;
    }
    }
    
    
    
    public boolean updateCheck()
    {
        dbConnect dbc = new dbConnect();
        String u = UID.getText().trim();
        String e = em.getText().trim();
        String us = un.getText().trim();
        
        try
        {
            String query = "SELECT * FROM tbl_accounts WHERE (u_username='"+ us +"'OR u_email='"+ e +"') AND u_id != '"+u+"'";
            ResultSet resultSet = dbc.getData(query);
            if(resultSet.next())
            {
                mail = resultSet.getString("u_email");
                if(mail.equals(e))
                {
                    JOptionPane.showMessageDialog(null, "Phone Number is Already Used");
                    em.setText("");
                }
                
                usname = resultSet.getString("u_username");
                if(usname.equals(us))
                {
                    JOptionPane.showMessageDialog(null, "Username is Already Used");
                    un.setText("");
                }
                return true;
            }else
            {
                return false;
            }
        }catch(SQLException ex)
        {
            System.out.println(""+ex);
            return false;
        }
    }
    
    
    private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Manager_Login1 = new javax.swing.JPanel();
        Header1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Navigation1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        un = new javax.swing.JTextField();
        register = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        em = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Cpassword = new javax.swing.JPasswordField();
        add = new javax.swing.JButton();
        accType = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        UID = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        update = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Manager_Login1.setBackground(new java.awt.Color(255, 255, 255));
        Manager_Login1.setForeground(new java.awt.Color(204, 204, 204));
        Manager_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header1.setBackground(new java.awt.Color(153, 153, 0));
        Header1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 255, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MILKTEA SHOP");
        Header1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 650, 40));

        Manager_Login1.add(Header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 580, 100));

        Navigation1.setBackground(new java.awt.Color(255, 204, 153));
        Navigation1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Navigation1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, 170));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/cup.png"))); // NOI18N
        Navigation1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        Manager_Login1.add(Navigation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 560));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("PASSWORD:");
        Manager_Login1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 100, 30));

        pass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });
        Manager_Login1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 330, 30));

        un.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        un.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unActionPerformed(evt);
            }
        });
        Manager_Login1.add(un, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 330, 30));

        register.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register.setText("Cancel");
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        Manager_Login1.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 490, 110, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("EMAIL:");
        Manager_Login1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, 60, 30));

        em.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emActionPerformed(evt);
            }
        });
        Manager_Login1.add(em, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 330, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("USERNAME:");
        Manager_Login1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 90, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("CONFIRM PASSWORD:");
        Manager_Login1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 170, 30));

        Cpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CpasswordActionPerformed(evt);
            }
        });
        Manager_Login1.add(Cpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 330, 30));

        add.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        Manager_Login1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 490, 90, 30));

        accType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "Admin" }));
        accType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accTypeActionPerformed(evt);
            }
        });
        Manager_Login1.add(accType, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 330, 30));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("ACCOUNT TYPE:");
        Manager_Login1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 120, 30));

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Pending" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        Manager_Login1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, 330, 30));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("ACCOUNT STATUS:");
        Manager_Login1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 140, 30));

        UID.setEditable(false);
        UID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UIDActionPerformed(evt);
            }
        });
        Manager_Login1.add(UID, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, 330, 30));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("ID:");
        Manager_Login1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 30, 30));

        update.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        Manager_Login1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 490, 90, 30));

        getContentPane().add(Manager_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    private void unActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unActionPerformed

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
        U_Admin ua = new U_Admin();
        ua.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registerActionPerformed

    private void emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emActionPerformed

    private void CpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CpasswordActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        dbConnect dbc = new dbConnect();
        String uname = un.getText().trim();
        String pass = new String(this.pass.getPassword()).trim();
        String Cpass = new String(Cpassword.getPassword()).trim();
        String e = em.getText().trim();
        String at = accType.getSelectedItem().toString().trim();

        if(uname.isEmpty() || pass.isEmpty() || Cpass.isEmpty() || e.isEmpty() || at.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

        }else if(!pass.equals(Cpass))
        {
            JOptionPane.showMessageDialog(null, "Password Does Not Match");
            //System.out.println("Password ["+password+"] Length: "+password.length());
            //System.out.println("Confirm Password ["+Cpassword+"] Length: "+Cpassword.length());
        }else if(pass.length() <= 7)
        {
            JOptionPane.showMessageDialog(null, "Password Must Exceed 8 Characters");
        }else if(!e.contains("@") && !e.contains(".com"))
        {
            JOptionPane.showMessageDialog(null, "Enter Valid Email");
        }else if(duplicateCheck())
        {
            System.out.println("Duplicate Exists");
        }else if (dbc.insertData("INSERT INTO tbl_accounts (u_username, u_accType, u_pass, u_email, u_status) "
        + "VALUES ('" + uname + "', '"+at+"','" + pass + "','" + e + "', 'Pending')"))
        {
            JOptionPane.showMessageDialog(null, "Registered succesfully!");
            U_Admin ua = new U_Admin();
            ua.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_addActionPerformed

    private void accTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accTypeActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void UIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UIDActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
  String userId = UID.getText().trim(); 
    String newEmail = em.getText().trim();
    String newUsername = un.getText().trim();
    String password = new String(pass.getPassword()); 
    String confirmPassword = new String(Cpassword.getPassword()); 
    
    this.UID.setEditable(true);  // Allow text retrieval
    this.UID.setEditable(false); // Restore its original state

    
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    
   if (em.getText().isEmpty() || un.getText().isEmpty() || 
    accType.getSelectedItem() == null || status.getSelectedItem() == null ||
    password.isEmpty() || confirmPassword.isEmpty()) {

    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}
  if (userId.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Error: User ID is missing.", "Error", JOptionPane.ERROR_MESSAGE);
    return; // Stop execution if User ID is missing
}

    if (!newEmail.matches(emailRegex)) {
        JOptionPane.showMessageDialog(this, "Invalid Email! Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!newUsername.matches("[a-zA-Z0-9_]{5,}")) {
        JOptionPane.showMessageDialog(this, "Invalid Username! Must be at least 5 characters and contain only letters, numbers, and underscores.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (!password.equals(confirmPassword)) {
    JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

    dbConnect dbc = new dbConnect();
    
    // Ensure userId is valid before running queries
    if (this.userId == null || this.userId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Error: User ID is missing.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String checkQuery = "SELECT COUNT(*) FROM tbl_accounts WHERE (u_username = ? OR u_email = ?) AND u_id != ?";

    try (Connection conn = dbc.getConnection();
         PreparedStatement pst = conn.prepareStatement(checkQuery)) {

       pst.setString(1, un.getText());  // Convert JTextField to String
       pst.setString(2, em.getText());  // Convert JTextField to String
       pst.setInt(3, Integer.parseInt(this.UID.getText()));  // Convert to int



        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Username or Email already exists! Please use different credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        
        String updateQuery = "UPDATE tbl_accounts SET u_email = ?, u_username = ?, u_accType = ?, u_status = ?, u_password = ? WHERE u_id = ?";
        try (PreparedStatement updatePst = conn.prepareStatement(updateQuery)) {
           updatePst.setString(3, em.getText());
           updatePst.setString(4, un.getText());
           updatePst.setString(5, accType.getSelectedItem().toString());
           updatePst.setString(6, status.getSelectedItem().toString());
           updatePst.setString(7, new String(pass.getPassword())); 
updatePst.setString(8, new String(Cpassword.getPassword()));

           updatePst.setInt(9, Integer.parseInt(userId)); 



            int updated = updatePst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new U_Admin().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_updateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUserForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPasswordField Cpassword;
    private javax.swing.JPanel Header1;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JPanel Navigation1;
    public javax.swing.JTextField UID;
    public javax.swing.JComboBox<String> accType;
    public javax.swing.JButton add;
    public javax.swing.JTextField em;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JPasswordField pass;
    private javax.swing.JButton register;
    public javax.swing.JComboBox<String> status;
    public javax.swing.JTextField un;
    public javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    void setUId(String userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
