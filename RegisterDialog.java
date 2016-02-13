/*
RegisterDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class RegisterDialog {

	protected JButton jbt;
	protected JTextField email;
	protected JTextField password;
	protected JTextField vpassword;
	protected JTextField firstname;
	protected JTextField surname;
	protected JTextField mobil;
	protected JTextField address;
	protected JTextField city;
	protected JTextField zip;
	protected JTextField web;
	protected JLabel jlb;
	protected static String CANCEL="cancel";
	protected static String OK="ok";
	protected MyTable mtb;
	protected MyJdbc mjdbc;
	protected JDialog jdg;
	protected boolean stateOK = false;   
        protected boolean stateCANCEL = false;        

	public RegisterDialog(MyTable mtb,MyJdbc mjdbc) {

	this.mtb=mtb;
	this.mjdbc=mjdbc;

//mtb clone
	}
	protected void showSimpleDialog(JFrame f,String label,String msg) {
		jdg=new JDialog(f,label,true);
		jdg.setSize(300,550);
		jdg.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		gbc.gridx=0;
		gbc.gridy=0;
		jdg.getContentPane().add(new JLabel("email"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("password"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("verify password"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("firstname"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("surname"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("mobil"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("address"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("city"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("zip"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("web"),gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		email=new JTextField(10);
		jdg.getContentPane().add(email,gbc);
		gbc.gridy++;
		password=new JTextField(10);
		jdg.getContentPane().add(password,gbc);
		gbc.gridy++;
		vpassword=new JTextField(10);
		jdg.getContentPane().add(vpassword,gbc);
		gbc.gridy++;
		firstname=new JTextField(10);
		jdg.getContentPane().add(firstname,gbc);
		gbc.gridy++;
		surname=new JTextField(10);
		jdg.getContentPane().add(surname,gbc);
		gbc.gridy++;
		mobil=new JTextField(10);
		jdg.getContentPane().add(mobil,gbc);
		gbc.gridy++;
		address=new JTextField(10);
		jdg.getContentPane().add(address,gbc);
		gbc.gridy++;
		city=new JTextField(10);
		jdg.getContentPane().add(city,gbc);
		gbc.gridy++;
		zip=new JTextField(10);
		jdg.getContentPane().add(zip,gbc);
		gbc.gridy++;
		web=new JTextField(10);
		jdg.getContentPane().add(web,gbc);

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
			System.out.println(firstname.getText()+" "+surname.getText());
//insert into db
			try {
		        	PreparedStatement stmt=(mjdbc.getConnection()).prepareStatement(
				"insert into address set email=?,password=?,firstname=?, surname=?,mobil=?,address=?,city=?,zip=?,web=?"
				);
				stmt.setString(1,email.getText());										stmt.setString(2,password.getText());
				stmt.setString(3,firstname.getText());
				stmt.setString(4,surname.getText());
				stmt.setString(5,mobil.getText());
				stmt.setString(6,address.getText());
				stmt.setString(7,city.getText());
				stmt.setString(8,zip.getText());
				stmt.setString(9,web.getText());
				stmt.executeUpdate();
                                stateOK = true;
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			jdg.setVisible(false);
			jdg.dispose();
//refresh table
			mtb.refreshTable("select * from address");

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

}
