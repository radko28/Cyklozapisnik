/*
OptionsDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class OptionsDialog extends ModifyDialog {

	protected JRadioButton motif;
	protected JRadioButton metal;
	protected JRadioButton gtk;
	protected JTextField dbserver;        
        protected ButtonGroup group = null;        
        protected JFrame f = null;

	public OptionsDialog(MyTable mtb,MyJdbc mjdbc) {
	  super(mtb,mjdbc);
	}
	protected void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld) {
        
                                this.f = f;
                                jdg=new JDialog(f,label,true);
                                jdg.setSize(500,200);
                                jdg.getContentPane().setLayout(new GridBagLayout());
                                GridBagConstraints gbc=new GridBagConstraints();
                                gbc.anchor=GridBagConstraints.WEST;
                                
//                                gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		                gbc.gridx=0;
                                gbc.gridy=0;
                                
                                motif=new JRadioButton("motif");
                                jdg.getContentPane().add(motif,gbc);
                                gbc.gridy++;
                                gtk=new JRadioButton("gtk");
                                jdg.getContentPane().add(gtk,gbc);
                                gbc.gridy++;
                                metal=new JRadioButton("metal");
                                jdg.getContentPane().add(metal,gbc);
                                if((ConfClass.LOOK).equals("motif")) {
                                        motif.setSelected(true);
                                } else if((ConfClass.LOOK).equals("gtk")) {
                                        gtk.setSelected(true);
                                } else if((ConfClass.LOOK).equals("metal")) {
                                        metal.setSelected(true);
                                }
                                
                                
                                group = new ButtonGroup();
                                group.add(motif);
                                group.add(gtk);
                                group.add(metal);
                                
                                ActionListener alistener = new RadioListener();
                                motif.addActionListener(alistener);
                                gtk.addActionListener(alistener);
                                metal.addActionListener(alistener);
                                
//dbserver                                
                                gbc.gridy++;
                                jdg.getContentPane().add(new JLabel("db server"),gbc);
                                gbc.gridx=1;                                                                        
                                dbserver=new JTextField(ConfClass.DBSERVER,20);
                                
                                jdg.getContentPane().add(dbserver,gbc);
                                
//
                                writeToFields();
//buttons
                                gbc.gridy+=10;                                
                                jdg.getContentPane().add(Buttons(),gbc);
                                jdg.setLocationRelativeTo(f);
                                jdg.setVisible(true);
	        }
        
	protected JPanel Buttons() {
	  JPanel panel=new JPanel();
	  panel.setLayout(new FlowLayout());
	  jbt=new JButton(OK);

	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent ae) {
                        ConfClass.DBSERVER = dbserver.getText();
                        
			jdg.setVisible(false);
			jdg.dispose();
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
        }
        
        class RadioListener implements ActionListener {
                public void actionPerformed(ActionEvent ae) {
                        String choice = ae.getActionCommand();
                        System.out.println(choice);
                        String look = "";
                        try {                        
                                if(choice.equals("motif")) {
                                        look = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                                } else if(choice.equals("gtk")) {
                                        look = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                                } else if(choice.equals("metal")) {
                                        look = "javax.swing.plaf.metal.MetalLookAndFeel";
                                }
                                ConfClass.LOOK = choice;
                                UIManager.setLookAndFeel(look);
                                SwingUtilities.updateComponentTreeUI(jdg);                                
                                SwingUtilities.updateComponentTreeUI(f);                                
                        } catch(Exception e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                        } 
                }
        }
}
