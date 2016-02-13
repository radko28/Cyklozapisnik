/*
WriteDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class WriteAddressDialog extends WriteDialog {

	protected JTextField email;
	protected JTextField password;
	protected JTextField firstname;
	protected JTextField surname;
	protected JTextField mobil;
	protected JTextField address;
	protected JTextField city;
	protected JTextField zip;
	protected JTextField web;

	public WriteAddressDialog(MyTable mtb,MyJdbc mjdbc) {
	  super(mtb,mjdbc);
	}
	protected void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld) {
                                jdg=new JDialog(f,label,true);
                                jdg.setSize(500,550);
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
                                email=new JTextField(20);
                                jdg.getContentPane().add(email,gbc);
                                gbc.gridy++;
                                password=new JTextField(20);
                                jdg.getContentPane().add(password,gbc);
                                gbc.gridy++;
                                firstname=new JTextField(20);
                                jdg.getContentPane().add(firstname,gbc);
                                gbc.gridy++;
                                surname=new JTextField(20);
                                jdg.getContentPane().add(surname,gbc);
                                gbc.gridy++;
                                mobil=new JTextField(20);
                                jdg.getContentPane().add(mobil,gbc);
                                gbc.gridy++;
                                address=new JTextField(20);
                                jdg.getContentPane().add(address,gbc);
                                gbc.gridy++;
                                city=new JTextField(20);
                                jdg.getContentPane().add(city,gbc);
                                gbc.gridy++;
                                zip=new JTextField(20);
                                jdg.getContentPane().add(zip,gbc);
                                gbc.gridy++;
                                web=new JTextField(20);
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
	  panel.add(jbt);
	  jbt=new JButton(CANCEL);
	  panel.add(jbt);


	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if(s.equals(OK)) {
//insert into db
			try {

		        	PreparedStatement stmt=(mjdbc.getConnection()).prepareStatement(
				"insert into address set email=?,password=?,firstname=?, surname=?,mobil=?,address=?,city=?,zip=?,web=?"
				);

				stmt.setString(1,email.getText());
				stmt.setString(2,password.getText());
				stmt.setString(3,firstname.getText());
				stmt.setString(4,surname.getText());
				stmt.setString(5,mobil.getText());
				stmt.setString(6,address.getText());
				stmt.setString(7,city.getText());
				stmt.setString(8,zip.getText());
				stmt.setString(9,web.getText());


				stmt.executeUpdate();
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			jdg.setVisible(false);
			jdg.dispose();
//refresh table
			mtb.refreshTable("select * from address");
                        
                        } else if(s.equals(CANCEL)) {
                                jdg.setVisible(false);
                                jdg.dispose();
                        }
		}
	  });
	  return panel;
	}
}
