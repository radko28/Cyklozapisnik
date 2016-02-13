/*
GraphDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.text.SimpleDateFormat;



public class GraphDialog  {

        protected JComboBox view;
        Map<String, Integer>  viewMap;	
        
        protected static String CANCEL="cancel";
	protected static String OK="ok";
        
        protected String viewSel = "month";        
        
        protected JDialog jdg;
        protected JButton jbt;

	public String showSimpleDialog(JFrame f,String label,String msg) {
		jdg=new JDialog(f,label,true);
		jdg.setSize(300,200);
		jdg.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.insets=new Insets(5,10,5,10);
//labels, textfields
		gbc.gridx=0;
		gbc.gridy=0;
		jdg.getContentPane().add(new JLabel("view"),gbc);
                gbc.gridy++;

		gbc.gridx=1;
		gbc.gridy=0;
		view=new JComboBox();
                populateView();
		jdg.getContentPane().add(view,gbc);
                gbc.gridy++;
                
                view.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent event) {
                                viewSel = (String)view.getSelectedItem();
                        }
                });
//                writeToFields();
//buttons
		gbc.gridx=1;
		gbc.gridy=GridBagConstraints.RELATIVE;
		jdg.getContentPane().add(Buttons(),gbc);

		jdg.setLocationRelativeTo(f);
		jdg.setVisible(true);
                
                return viewSel;

	}
	protected JPanel Buttons() {
	  JPanel panel=new JPanel();
	  panel.setLayout(new FlowLayout());
	  jbt=new JButton(OK);


	  jbt.addActionListener(new ActionListener() {
	  	public void actionPerformed(ActionEvent e) {
			jdg.setVisible(false);
			jdg.dispose();
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
        public void populateView() {
                        
                viewMap = new HashMap<String, Integer>();
                viewMap.put("year",0);
                viewMap.put("month",1);
                viewMap.put("day",2);                
                view.setModel(new DefaultComboBoxModel( (viewMap.keySet()).toArray() ));
        }
        public void populateCyclo() {
                /*
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select * from cyklo");
                        
                        Set yearSet = new HashSet();
                        Set monthSet = new HashSet();
                        Set trackSet = new HashSet();                        
                        Vector monthVector = new Vector();
                        Vector veloceVector = new Vector();
                        Vector distanceVector = new Vector();
                        Vector trackVector = new Vector();                        
                        yearSet.add("-->all");                        
                        monthSet.add("-->all");
                        trackSet.add("-->all");
                        Date date;
                        veloceVector.addElement("-->all");
                        distanceVector.addElement("-->all");                        
                        while(rset.next()) {
                                date = rset.getDate("date");
                                yearSet.add((new SimpleDateFormat("yyyy")).format(date).toString());
                                monthSet.add((new SimpleDateFormat("MM")).format(date).toString());
                                veloceVector.addElement((rset.getObject(5)).toString());
                                distanceVector.addElement((rset.getObject(6)).toString());
                                trackSet.add((rset.getObject(7)).toString());                                
                        }
                        year.setModel(new DefaultComboBoxModel(yearSet.toArray()));
                        month.setModel(new DefaultComboBoxModel(monthSet.toArray()));
                        veloce.setModel(new DefaultComboBoxModel(veloceVector));
                        distance.setModel(new DefaultComboBoxModel(distanceVector));
                        track.setModel(new DefaultComboBoxModel(trackSet.toArray()));                        
                        
                } catch(Exception e) {
                        System.out.println("exception");
                        e.printStackTrace();
                }
                */
        }
}
