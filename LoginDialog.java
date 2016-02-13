/*
LoginDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class LoginDialog {

	protected JButton jbt;
	protected JTextField password;
	protected JTextField email;
	protected JLabel jlb;
	protected static String CANCEL="cancel";
	protected static String OK="ok";
	protected MyTable mtb;
	protected MyJdbc mjdbc;
	protected JDialog jdg;
	protected boolean stateOK=false;
	protected boolean stateCANCEL=false;        
	protected int id;
	protected String user = "";        

	public LoginDialog(MyTable mtb,MyJdbc mjdbc) {

                this.mtb=mtb;
                this.mjdbc=mjdbc;
//mtb clone
	}
	protected void showSimpleDialog(JFrame f,String label,String msg) {
		jdg=new JDialog(f,label,true);
		jdg.setSize(300,150);
		jdg.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("email"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("password"),gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridy++;                
		email=new JTextField(10);
		jdg.getContentPane().add(email,gbc);
		gbc.gridy++;
		password=new JTextField(10);
		jdg.getContentPane().add(password,gbc);

//buttons
		gbc.gridx=1;
		gbc.gridy=GridBagConstraints.RELATIVE;
		jdg.getContentPane().add(Buttons(),gbc);

		jdg.setLocationRelativeTo(f);
		jdg.setVisible(true);

	}
	protected JPanel Buttons() {
	  JPanel panel=new JPanel();
	  panel.setLayout(new FlowLayout());
	  jbt=new JButton(OK);


	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
			try {
				PreparedStatement stmt=(mjdbc.getConnection()).prepareStatement(
				"select id, email from address where email=? && password=?");
				stmt.setString(1,email.getText());
				stmt.setString(2,password.getText());
				ResultSet rset=stmt.executeQuery();
				rset.last();
//create log session
				if(rset.getRow()==1) {
				  Statement stmt2=(mjdbc.getConnection()).createStatement();
				  id=rset.getInt("id");
                                  user=rset.getString("email");                                  
				  stmt2.executeUpdate("update address set log='1' where id="+id);
				  stateOK=true;
                                  mtb.refreshTable("select * from cyklo where id_user="+id + " order by date",0);
				}
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			jdg.setVisible(false);
			jdg.dispose();
//refresh table
		}
	  });
	  panel.add(jbt);
	  jbt=new JButton(CANCEL);
          jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
                        stateCANCEL = true;
			jdg.setVisible(false);
			jdg.dispose();
                }
          });          
	  panel.add(jbt);
	  return panel;
	}
	public boolean getStateOK() {
	  return stateOK;
	}
	public boolean getStateCANCEL() {
	  return stateCANCEL;
	}
        public void setStateOK(boolean stateOK) {
          this.stateOK = stateOK;      
	}
        
        
        
	public int getId() {
	  return id;
	}

       	public String getUser() {
	  return user;
	}
}
