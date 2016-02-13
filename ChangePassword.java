/*
ChangePassword.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class ChangePassword extends ModifyDialog {

	protected JTextField password;
	protected JTextField vpassword;
        int id = 0;

	public ChangePassword(MyTable mtb,MyJdbc mjdbc) {
	  super(mtb,mjdbc);
	}
	protected void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld) {
                if(ld != null) {
                                id = ld.getId();
                                jdg=new JDialog(f,label,true);
                                jdg.setSize(300,550);
                                jdg.getContentPane().setLayout(new GridBagLayout());
                                GridBagConstraints gbc=new GridBagConstraints();
                                gbc.anchor=GridBagConstraints.WEST;
                                gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		                gbc.gridx=0;
                                gbc.gridy=0;
                                jdg.getContentPane().add(new JLabel("password"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("verify password"),gbc);
                                gbc.gridx=1;
                                gbc.gridy=0;
                                password=new JTextField(10);
                                jdg.getContentPane().add(password,gbc);
                                gbc.gridy++;
                                vpassword=new JTextField(10);
                                jdg.getContentPane().add(vpassword,gbc);
//
//buttons
		                gbc.gridx=1;
                                gbc.gridy=GridBagConstraints.RELATIVE;
                                jdg.getContentPane().add(Buttons(),gbc);
                                jdg.setLocationRelativeTo(f);
                                jdg.setVisible(true);
                } else
//no log
                                JOptionPane.showMessageDialog(f,"No logged user","",JOptionPane.WARNING_MESSAGE);
	        }
        
	protected JPanel Buttons() {
	  JPanel panel=new JPanel();
	  panel.setLayout(new FlowLayout());
	  jbt=new JButton(OK);

	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
//insert into db
			try {
                                
		        	PreparedStatement stmt=(mjdbc.getConnection()).prepareStatement(
				"update address set password=? where id="+id);
				stmt.setString(1,password.getText());
				stmt.executeUpdate();
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			jdg.setVisible(false);
        			jdg.dispose();
//refresh table
			mtb.refreshTable("select * from address ",1);
		}
	  });
	  panel.add(jbt);
	  jbt=new JButton(CANCEL);
	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
			jdg.setVisible(false);
			jdg.dispose();
                }
          });
	  panel.add(jbt);
	  return panel;
	}
        protected void writeToFields() {
        }
}
