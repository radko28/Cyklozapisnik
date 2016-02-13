/*
FilterDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.text.SimpleDateFormat;



public class FilterDialog  {

        protected JComboBox email;
        Map<String, Integer>  emailMap;	
        protected JComboBox year;
        protected JComboBox month;        
	protected JComboBox veloce;
	protected JComboBox distance;
	protected JComboBox track;
        
        protected static String ALL="-->all";        
        protected static String CANCEL="cancel";
	protected static String OK="ok";
        
        protected double veloceSel = 0;
        protected String emailSel = ALL;
        protected String yearSel = ALL;
        protected String monthSel = ALL; 
        protected String view = "year";
        
        protected JDialog jdg;
        protected MyTable mtb;
	protected MyJdbc mjdbc;
        protected JButton jbt;


	public FilterDialog(MyTable mtb,MyJdbc mjdbc) {
                this.mtb=mtb;
                this.mjdbc=mjdbc;
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
		jdg.getContentPane().add(new JLabel("year"),gbc);
                gbc.gridy++;
		jdg.getContentPane().add(new JLabel("month"),gbc);                
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("veloce"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("distance"),gbc);
		gbc.gridy++;
		jdg.getContentPane().add(new JLabel("track"),gbc);
                
		gbc.gridx=1;
		gbc.gridy=0;
		email=new JComboBox();
                populateEmail();
		jdg.getContentPane().add(email,gbc);
                gbc.gridy++;
		year=new JComboBox();
		month=new JComboBox();
		veloce=new JComboBox();
		distance=new JComboBox();
		track=new JComboBox();                
                populateCyclo();                
		jdg.getContentPane().add(year,gbc);                
		gbc.gridy++;
                jdg.getContentPane().add(month,gbc);                
		gbc.gridy++;
		jdg.getContentPane().add(veloce,gbc);
		gbc.gridy++;
		jdg.getContentPane().add(distance,gbc);
		gbc.gridy++;
		jdg.getContentPane().add(track,gbc);
                
                email.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent event) {
                                emailSel = (String)email.getSelectedItem();
                        }
                });
                veloce.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent event) {
                                veloceSel = Double.valueOf((String)veloce.getSelectedItem()).doubleValue() - 0.01;
                        }
                });
                year.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent event) {
                                yearSel = (String)year.getSelectedItem();
                        }
                });
                month.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent event) {
                                monthSel = (String)month.getSelectedItem();
                        }
                });
                
//
//                writeToFields();

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
                        String query = "select * from cyklo ";
                        query = query + "where ";
                        
                        if(emailMap.get(emailSel) >0)
                          query = query + "id_user = " + emailMap.get(emailSel);
                        else
                          query = query + "id_user > " + emailMap.get(emailSel);
                        if(!yearSel.equals(ALL)) {
                          query = query + " && SUBSTRING(date,1,4) = " + yearSel;
                          view = "month";
                          
                          if(!monthSel.equals(ALL)) {
                            query = query + " && SUBSTRING(date,6,2) = " + monthSel;
                            view = "day";
                          }
                                  
                        }
                        query = query + " && veloce > " + veloceSel + " order by date";
                        System.out.println("query = " + query);                        

                        mtb.refreshTable(query);
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
                /*
                String row = (((TableValues)mtb.getModel()).getValueAt(mtb.getRow(),0)).toString();
                selectedRow=Integer.valueOf(row).intValue();
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select * from cyklo where id="+selectedRow);
//insert to dialog field
                        if(rset.next()) {
                                date.setText(rset.getObject(4).toString());
                                time.setText(rset.getObject(5).toString());
                                veloce.setText(rset.getObject(6).toString());
                                distance.setText(rset.getObject(7).toString());
                                track.setText(rset.getObject(8).toString());
                                note.setText(rset.getObject(9).toString());
                        }
                } catch(Exception e) {
                          System.out.println("exception");
                        e.printStackTrace();
                }
                */
        }
        public void populateEmail() {
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select email, id from address");
                        
                        emailMap = new HashMap<String, Integer>();
                        emailMap.put("-->all",0);                        
                        
                        while(rset.next()) {
                                emailMap.put(rset.getString("email"),rset.getInt("id"));
                        }
                        email.setModel(new DefaultComboBoxModel( (emailMap.keySet()).toArray() ));
                        
                } catch(Exception e) {
                        System.out.println("exception");
                        e.printStackTrace();
                }
        }
        public void populateCyclo() {
                try {
                        Statement stmt=(mjdbc.getConnection()).createStatement();
                        ResultSet rset=stmt.executeQuery("select * from cyklo");
                        
                        LinkedHashSet yearSet = new LinkedHashSet();
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
                                System.out.println("year:"+(new SimpleDateFormat("yyyy")).format(date).toString());
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
        }
        public String getView() {
                return view;
        }
        public String getYear() {
                return yearSel;
        }
        public String getMonth() {
                return monthSel;
        }
        public String getUser() {
                return emailSel;
        }
}
