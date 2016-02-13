/*
ModifyCycloDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class ModifyCycloDialog extends ModifyDialog {

	protected JTextField date;
	protected JTextField time;
	protected JTextField veloce;
	protected JTextField distance;
	protected JTextField track;
	protected JTextField note;
        int id = 0;

	public ModifyCycloDialog(MyTable mtb,MyJdbc mjdbc) {
	  super(mtb,mjdbc);
	}
	protected void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld) {
                if(ld != null) {
                        String row = (((TableValues)mtb.getModel()).getValueAt2(mtb.getRow(),0)).toString();
                        selectedRow=Integer.valueOf(row).intValue();
                        row = (((TableValues)mtb.getModel()).getValueAt2(mtb.getRow(),1)).toString();
                        int idUser=Integer.valueOf(row).intValue();
                        if(ld.getId() == idUser) {                        
                                jdg=new JDialog(f,label,true);
                                jdg.setSize(300,550);
                                jdg.getContentPane().setLayout(new GridBagLayout());
                                GridBagConstraints gbc=new GridBagConstraints();
                                gbc.anchor=GridBagConstraints.WEST;
                                gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		                gbc.gridx=0;
                                gbc.gridy=0;
                                jdg.getContentPane().add(new JLabel("date"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("time"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("veloce"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("distance"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("track"),gbc);
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("note"),gbc);
                                gbc.gridx=1;
                                gbc.gridy=0;
                                date=new JTextField(10);
                                jdg.getContentPane().add(date,gbc);
                                gbc.gridy++;
                                time=new JTextField(10);
                                jdg.getContentPane().add(time,gbc);
                                gbc.gridy++;
                                veloce=new JTextField(10);
                                jdg.getContentPane().add(veloce,gbc);
                                gbc.gridy++;
                                distance=new JTextField(10);
                                jdg.getContentPane().add(distance,gbc);
                                gbc.gridy++;
                                track=new JTextField(10);
                                jdg.getContentPane().add(track,gbc);
                                gbc.gridy++;
                                note=new JTextField(10);
                                jdg.getContentPane().add(note,gbc);
//
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
				"update cyklo set date=?,time=?,veloce=?, distance=?,track=?,note=? where id="+selectedRow);

				stmt.setDate(1,Date.valueOf(date.getText()));
				stmt.setString(2,time.getText());
				stmt.setDouble(3,Double.valueOf(veloce.getText()));
				stmt.setDouble(4,Double.valueOf(distance.getText()));
				stmt.setString(5,track.getText());
				stmt.setString(6,note.getText());
				stmt.executeUpdate();
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			jdg.setVisible(false);
			jdg.dispose();
//refresh table
			mtb.refreshTable("select * from cyklo order by date");
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
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select * from cyklo where id="+selectedRow);
//insert to dialog field
                        if(rset.next()) {
                                date.setText(rset.getDate(3).toString());
                                time.setText(rset.getObject(4).toString());
                                veloce.setText(rset.getObject(5).toString());
                                distance.setText(rset.getObject(6).toString());
                                track.setText(rset.getObject(7).toString());
                                note.setText(rset.getObject(8).toString());
                        }
                } catch(Exception e) {
                          System.out.println("exception");
                        //e.printStackTrace();
                }
        }
}
