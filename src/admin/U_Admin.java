/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import config.dbConnect;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import Startups.Coffeeshop;
import config.Session;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class U_Admin extends javax.swing.JFrame {

    
    public U_Admin() {
        initComponents();
        displayData();
    }
    
    public void setUid(String uid) {
    UID.setText(uid);
}

    private JLabel UID;



    public void displayData()
    {
        try
        {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT u_id, u_username, u_acctype, u_email, u_status FROM tbl_accounts");
            account_table.setModel(DbUtils.resultSetToTableModel(rs));
             rs.close();
        }catch(SQLException ex){
            System.out.println("Errors: "+ex.getMessage());
        }
    }
    
    DefaultTableModel model = new DefaultTableModel();

    
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();

            
            if (row == -1 || column == -1) {
                return; 
            }

            updateDatabase(row, column);
        }
         Session sess = Session.getInstance();
    
    String[] columnNames = {"u_id", "u_email", "u_acctype", "u_username","u_pass", "u_status"};
    model.setColumnIdentifiers(columnNames); 
    model.setRowCount(0);

    String sql = "SELECT u_id, u_email, u_acctype, u_username, u_passw, u_status FROM tbl_accounts WHERE u_id != '"+sess.getUid()+"';";

    try (Connection connect = new dbConnect().getConnection();
         PreparedStatement pst = connect.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            Object[] row = {
                rs.getInt("u_id"),
                rs.getString("email"),
                rs.getString("actype"),
                rs.getString("username"),
                rs.getString("pass"),
                rs.getString("status")
            };
            model.addRow(row); 
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    private void updateDatabase(int row, int column) {
    try (Connection connect = new dbConnect().getConnection()) {
        String columnName = account_table.getColumnName(column); 
        String newValue = account_table.getValueAt(row, column).toString(); 
        int userId = Integer.parseInt(account_table.getValueAt(row, 0).toString());

        String sql = "UPDATE tbl_accounts SET " + columnName + " = ? WHERE u_id = ?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, newValue);
            pst.setInt(2, userId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Database Updated Successfully!");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
    }
}
    void addUser() {
   
     CreateUserForm createuserform = new CreateUserForm();
     createuserform.setVisible(true);
}
    
     public boolean addUser(String firstname, String lastname, String email, String acctype, String username, String pass, String status) {
    Connection con = null;
    PreparedStatement pst = null;

    try {
       
        con = new dbConnect().connect();

        
        String query = "INSERT INTO tbl_accounts (u_email, u_acctype, u_username, u_pass, u_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        pst = con.prepareStatement(query);
        
        
        
        pst.setString(1, email);
        pst.setString(2, acctype);
        pst.setString(3, username);
        pst.setString(4, pass);
        pst.setString(5, status);

        
        int rowsInserted = pst.executeUpdate();
        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        
        try {
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
     public boolean updateUser(String firstname, String lastname, String email, String acctype, String username, String password, String status) {
    try {
        
        Connection con = new dbConnect().getConnection();
        String query = "UPDATE tbl_accounts SET u_email=?, u_acctype=?, u_pass=?, u_status=? WHERE u_username=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, email);
        pst.setString(2, acctype);
        pst.setString(3, password);
        pst.setString(4, status);
        pst.setString(5, username);

        int rowsUpdated = pst.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
     private void loadUsersData() {
    DefaultTableModel model = (DefaultTableModel) account_table.getModel();
    model.setRowCount(0); // Clear the table before reloading

    String sql = "SELECT * FROM tbl_accounts";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db", "root", "");
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("u_id"),
                rs.getString("u_username"),
                rs.getString("u_email")
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


     private void deleteUser() {
         int selectedRow = account_table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        return;
    }

    int userId = (int) account_table.getValueAt(selectedRow, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM tbl_accounts WHERE u_id=?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db", "root", "");
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "User Deleted Successfully!");
                loadUsersData();  // Ensure this method exists
            } else {
                JOptionPane.showMessageDialog(this, "No user found to delete.", "Deletion Failed", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
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
     
     
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Manager_Login1 = new javax.swing.JPanel();
        Header1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Navigation1 = new javax.swing.JPanel();
        register = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        acc_id = new javax.swing.JLabel();
        register1 = new javax.swing.JButton();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Manager_Login1.setBackground(new java.awt.Color(133, 108, 91));
        Manager_Login1.setForeground(new java.awt.Color(204, 204, 204));
        Manager_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header1.setBackground(new java.awt.Color(51, 51, 51));
        Header1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 255, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MILKTEA SHOP");
        Header1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 720, 40));

        Manager_Login1.add(Header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 740, 100));

        Navigation1.setBackground(new java.awt.Color(176, 136, 109));
        Navigation1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register.setText("Back");
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
        });
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        Navigation1.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 140, 30));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/woman.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 80));

        Navigation1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 120, 100));

        acc_id.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_id.setForeground(new java.awt.Color(255, 255, 255));
        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        Navigation1.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 230, 30));

        Manager_Login1.add(Navigation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 610));

        register1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register1.setText("DELETE");
        register1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register1ActionPerformed(evt);
            }
        });
        Manager_Login1.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, 140, 50));

        add.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        Manager_Login1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 140, 50));

        update.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        update.setText("UPDATE");
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateMouseClicked(evt);
            }
        });
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        Manager_Login1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 140, 50));

        account_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(account_table);

        Manager_Login1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 720, 430));

        getContentPane().add(Manager_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 610));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
       
    }//GEN-LAST:event_registerActionPerformed

    private void register1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register1ActionPerformed
    deleteUser();     
    }//GEN-LAST:event_register1ActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
       
        addUser();
    }//GEN-LAST:event_addActionPerformed

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
        adminDashboard ad = new adminDashboard();
        ad.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registerMouseClicked

    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked
       int selectedRow = account_table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String username = account_table.getValueAt(selectedRow, account_table.getColumn("u_username").getModelIndex()).toString();

    if (username == null || username.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Selected user has no username.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        return;
      
    }
    }//GEN-LAST:event_updateMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
           Session sess = Session.getInstance();
       if(sess.getUid() == 0)
       {
           JOptionPane.showMessageDialog(null,"No Account, Login FIrst");
           Coffeeshop c = new Coffeeshop();
           c.setVisible(true);
           this.dispose();
       }else
       {
           
          
           acc_id.setText("" + sess.getUid());
       }        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        int rowIndex = account_table.getSelectedRow();
if (rowIndex < 0) {
    JOptionPane.showMessageDialog(null, "Please select a User!");
} else {
    try {
        dbConnect dbc = new dbConnect();
        TableModel tbl = account_table.getModel();

        String userId = tbl.getValueAt(rowIndex, 0).toString(); 
        String query = "SELECT * FROM tbl_accounts WHERE u_id = ?";

        PreparedStatement pst = dbc.getConnection().prepareStatement(query);
        pst.setString(1, userId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            CreateUserForm cuf = new CreateUserForm();
           cuf.UID.setText(userId); 
            cuf.status.setSelectedItem(rs.getString("u_status"));
            cuf.accType.setSelectedItem(rs.getString("u_acctype"));
            cuf.em.setText(rs.getString("u_email"));
            cuf.un.setText(rs.getString("u_username"));
            cuf.pass.setEnabled(true); 
             cuf.Cpassword.setEnabled(true);

            cuf.setVisible(true);
            this.dispose();
        }
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }        // TODO add your handling code here:
    }//GEN-LAST:event_updateActionPerformed
    }
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
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new U_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header1;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JPanel Navigation1;
    private javax.swing.JLabel acc_id;
    private javax.swing.JTable account_table;
    private javax.swing.JButton add;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton register;
    private javax.swing.JButton register1;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

   

   
}
