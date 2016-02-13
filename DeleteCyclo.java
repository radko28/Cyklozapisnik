/*
DeleteCyclo.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class DeleteCyclo {

	protected JButton jbt;
	protected JTextField date;
	protected JTextField time;
	protected JTextField veloce;
	protected JTextField distance;
	protected JTextField track;
	protected JTextField note;
	
	protected JLabel jlb;
	protected static String CANCEL="cancel";
	protected static String OK="ok";
	protected MyTable mtb;
	protected MyJdbc mjdbc;
	protected JDialog jdg;

	public DeleteCyclo(MyTable mtb,MyJdbc mjdbc) {

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
//insert into db
			try {
                                
		        	PreparedStatement stmt=(mjdbc.getConnection()).prepareStatement(
				"insert into cyklo set date=?,time=?,veloce=?, distance=?,track=?,note=?"
				);

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
			mtb.refreshTable("select * from cyklo");

		}
	  });

	  panel.add(jbt);
	  jbt=new JButton(CANCEL);
	  panel.add(jbt);
	  return panel;
	}

}
