/*
ModifyAddressDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class ModifyAddressDialog extends ModifyDialog {

	protected JTextField email;
	protected JTextField password;
	protected JTextField firstname;
	protected JTextField surname;
	protected JTextField mobil;
	protected JTextField address;
	protected JTextField city;
	protected JTextField zip;
	protected JTextField web;


	public ModifyAddressDialog(MyTable mtb,MyJdbc mjdbc) {
                super(mtb,mjdbc);
	}
	protected void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld) {
                if(ld != null) {
                        String row = (((TableValues)mtb.getModel()).getValueAt2(mtb.getRow(),0)).toString();
                        int selectedRow=Integer.valueOf(row).intValue();
                        if(ld.getId() == selectedRow) {                        
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
                                int fa = 20;
                                email=new JTextField(fa);
                                jdg.getContentPane().add(email,gbc);
                                gbc.gridy++;
                                password=new JTextField(fa);
                                jdg.getContentPane().add(password,gbc);
                                gbc.gridy++;
                                firstname=new JTextField(fa);
                                jdg.getContentPane().add(firstname,gbc);
                                gbc.gridy++;
                                surname=new JTextField(fa);
                                jdg.getContentPane().add(surname,gbc);
                                gbc.gridy++;
                                mobil=new JTextField(fa);
                                jdg.getContentPane().add(mobil,gbc);
                                gbc.gridy++;
                                address=new JTextField(fa);
                                jdg.getContentPane().add(address,gbc);
                                gbc.gridy++;
                                city=new JTextField(fa);
                                jdg.getContentPane().add(city,gbc);
                                gbc.gridy++;
                                zip=new JTextField(fa);
                                jdg.getContentPane().add(zip,gbc);
                                gbc.gridy++;
                                web=new JTextField(fa);
                                jdg.getContentPane().add(web,gbc);
                                writeToFields();
//buttons
                		gbc.gridx=1;
                                gbc.gridy=GridBagConstraints.RELATIVE;
                                jdg.getContentPane().add(Buttons(),gbc);
                                jdg.setLocationRelativeTo(f);
                                jdg.setVisible(true);
                        } else
//no privaty                
                                JOptionPane.showMessageDialog(f,"Excess denied","",JOptionPane.WARNING_MESSAGE);
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
				"update address set email=?,password=?,firstname=?, surname=?,mobil=?,address=?,city=?,zip=?,web=? where id="+selectedRow);

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
                String row = (((TableValues)mtb.getModel()).getValueAt2(mtb.getRow(),0)).toString();
                selectedRow=Integer.valueOf(row).intValue();
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select * from address where id="+selectedRow);
                        ResultSetMetaData rsmd=rset.getMetaData();
//insert to dialog field
                        if(rset.next()) {
                                email.setText(rset.getObject(2).toString());
                                password.setText(rset.getObject(3).toString());
                                firstname.setText(rset.getObject(4).toString());
                                surname.setText(rset.getObject(5).toString());
                                mobil.setText(rset.getObject(6).toString());
                                address.setText(rset.getObject(7).toString());
                                city.setText(rset.getObject(8).toString());
                                zip.setText(rset.getObject(9).toString());	             	                        web.setText(rset.getObject(11).toString());
                        }
                } catch(Exception e) {
                          System.out.println("exception");
                        e.printStackTrace();
                }
        }
}