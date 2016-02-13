/*
MyTool.java
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.table.TableModel;


public class MyTool extends JPanel   {

	protected JFrame f;
    	protected JButton jtb;
	protected JPanel panel;
	protected String[] label={
          "register","change password","logout","login","write",
          "modify","delete","graph","filter","exportXML","importXML","options"," user: ",
          "choose graph view"
        };
	protected RegisterDialog rd;
       	protected WriteDialog[] wd;
       	protected ModifyDialog[] md;
       	protected OptionsDialog od;        
        protected TableChartPopup tc=null;
	protected LoginDialog ld;
	protected MyTable mtb;
	protected MyJdbc mjdbc;
        protected JLabel userLabel;
        protected JLabel viewLabel;        
        protected FilterDialog fd = null;
        protected ConfClass cc = null;
        protected MyXML mx;
        protected ChangePassword cp = null;
        protected JButton jbreg = null;
        protected JButton jbpas = null;
        protected JButton jblogin = null;
        protected JButton jblogout = null;
        protected JButton jbwrite = null;
        protected JButton jbmodify = null;
        protected JButton jbdelete = null;
        protected JButton jbgraph = null;
        protected JButton jbfilter = null;
        protected JButton jbexml = null;
        protected JButton jbixml = null;
        protected JButton jboptions = null;        

	public MyTool(JFrame f,MyTable mtb,MyJdbc mjdbc, ConfClass cc) {
		super();
                
		this.mtb=mtb;
		this.mjdbc=mjdbc;
		this.f=f;
                this.cc = cc;
		wd = new WriteDialog[2];
                wd[0] =  new WriteCycloDialog(mtb,mjdbc);
		wd[1] =  new WriteAddressDialog(mtb,mjdbc);
		md = new ModifyDialog[2];
                md[0] =  new ModifyCycloDialog(mtb,mjdbc);
		md[1] =  new ModifyAddressDialog(mtb,mjdbc);
		panel=new JPanel();
//register                
                jbreg = button(label[0],true);
		panel.add(jbreg);
//password                
                jbpas = button(label[1],false);
		panel.add(jbpas);
//logout                
                jblogout = button(label[2],false);
		panel.add(jblogout);
//login                
                jblogin = button(label[3],true);
		panel.add(jblogin);
//write
                jbwrite = button(label[4],false);
		panel.add(jbwrite);
//modify
                jbmodify = button(label[5],false);
		panel.add(jbmodify);
//delete
                jbdelete = button(label[6],false);
		panel.add(jbdelete,false);
//graph
                jbgraph = button(label[7],false);
		panel.add(jbgraph,false);
//filter
                jbfilter = button(label[8],false);
		panel.add(jbfilter,false);
//exportXML
                jbexml = button(label[9],false);
		panel.add(jbexml,false);
//importXML
                jbixml = button(label[10],false);
		panel.add(jbixml,false);
//options                
                jboptions = button(label[11],true);
		panel.add(jboptions,false);
                
                
		panel.add(new JLabel(" user: "));                
                userLabel=new JLabel("");
       		panel.add(userLabel);                
		panel.add(new JLabel(" view: "));                
                viewLabel=new JLabel("");
       		panel.add(viewLabel);      
  	}
	public JPanel getTool() {
	  return panel;
	}
	protected JButton button(String label, boolean state) {
          JButton jbt = new JButton(label);
          jbt.setEnabled(state);
	  jbt.addActionListener(actionListener);
          return jbt; 
	}
 	ActionListener actionListener=new ActionListener()  {
    		public void actionPerformed(ActionEvent event) {
			AbstractButton btn=(AbstractButton)(event.getSource());
			System.out.println(btn.getText());
			if((btn.getText()).compareToIgnoreCase(label[0])==0) {
//register dialog
				rd=new RegisterDialog(mtb,mjdbc);
                                rd.showSimpleDialog(f,label[0],"waw");
                                if(rd.getStateOK()) {                                
                                        jblogout.setEnabled(true);
                                        jblogin.setEnabled(false);
                                        jbpas.setEnabled(true);  
                                        jbreg.setEnabled(false);                                
                                        jbwrite.setEnabled(true);
                                        jbmodify.setEnabled(true);
                                        jbdelete.setEnabled(true);
                                        jbgraph.setEnabled(true);
                                        jbfilter.setEnabled(true);
                                        jbexml.setEnabled(true);
                                        jbixml.setEnabled(true);
                                } else if(!rd.getStateCANCEL() && !rd.getStateOK())
//no privaty                
                                        JOptionPane.showMessageDialog(f,"Excess denied","",JOptionPane.WARNING_MESSAGE);
                                
//change password
                        } else if((btn.getText()).compareToIgnoreCase(label[1])==0) {
				cp=new ChangePassword(mtb,mjdbc);
				cp.showSimpleDialog(f,label[1],"waw",ld);
			} else if((btn.getText()).compareToIgnoreCase(label[2])==0) {
//logout
			try {
				  Statement stmt=(mjdbc.getConnection()).createStatement();
				  stmt.executeUpdate("update address set log='0' where id="+ld.getId());
				  ld.setStateOK(false);
                                  userLabel.setText("");
                                  viewLabel.setText("");                                  
                                  jblogout.setEnabled(false);
                                  jblogin.setEnabled(true);
                                  jbpas.setEnabled(false);
                                  jbreg.setEnabled(true);
                                  jbdelete.setEnabled(false);
                                  jbgraph.setEnabled(false);
                                  jbfilter.setEnabled(false);
                                  jbexml.setEnabled(false);
                                  jbixml.setEnabled(false);
                                
                                  
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();
			}
			} else if((btn.getText()).compareToIgnoreCase(label[3])==0) {
//login dialog
				ld=new LoginDialog(mtb,mjdbc);
				ld.showSimpleDialog(f,label[3],"waw");
                                if(ld.getStateOK()) {
                                        userLabel.setText( ld.getUser());
                                        jblogout.setEnabled(true);
                                        jblogin.setEnabled(false);
                                        jbpas.setEnabled(true);  
                                        jbreg.setEnabled(false);
                                        jbwrite.setEnabled(true);
                                        jbmodify.setEnabled(true);
                                        jbdelete.setEnabled(true);
                                        jbgraph.setEnabled(true);
                                        jbfilter.setEnabled(true);
                                        jbexml.setEnabled(true);
                                        jbixml.setEnabled(true);
                              } else if(!ld.getStateCANCEL() && !ld.getStateOK())
//no privaty                
                                        JOptionPane.showMessageDialog(f,"Excess denied","",JOptionPane.WARNING_MESSAGE);
                                
                               
//write
                        } else if((btn.getText()).compareToIgnoreCase(label[4])==0) {
				wd[mtb.getTreeIndex()].showSimpleDialog(f,label[4],"waw",ld);
//modify
                        } else if((btn.getText()).compareToIgnoreCase(label[5])==0) {
				md[mtb.getTreeIndex()].showSimpleDialog(f,label[5],"waw",ld);
//delete
                        } else if((btn.getText()).compareToIgnoreCase(label[6])==0) {
                                mtb.removeRow(f,ld);
//graph
                        } else if((btn.getText()).compareToIgnoreCase(label[7])==0) {
//choose dialog
                                tc = new TableChartPopup((TableValues)mtb.getModel(),mtb.getSelectedColumn(),fd);
                                tc.setVisible(true);
//filter
                        } else if((btn.getText()).compareToIgnoreCase(label[8])==0) {
                                fd=new FilterDialog(mtb,mjdbc);                                
				fd.showSimpleDialog(f,label[8],"waw");
                       		viewLabel.setText( fd.getUser());                                
//export XML
                        } else if((btn.getText()).compareToIgnoreCase(label[9])==0) {
				mx=new MyXML(mtb.getRset());
				mx.exportXML(f,label[9],"waw");
//import XML
                        } else if((btn.getText()).compareToIgnoreCase(label[10])==0) {
//input file dialog
                                JFileChooser chooser = new JFileChooser();
                                int option = chooser.showOpenDialog(MyTool.this);
                                if(option == JFileChooser.APPROVE_OPTION) {
                                        File sf = chooser.getSelectedFile(); 
                                        if(sf != null) {
                                                mx=new MyXML(mtb,mjdbc,ld.getId());
                                                mx.importXML(f,sf.getName(),"waw");
                                        }
                                }
//options
                        } else if((btn.getText()).compareToIgnoreCase(label[11])==0) {
                                od=new OptionsDialog(mtb,mjdbc);                                
				od.showSimpleDialog(f,label[11],"waw",ld);
                                System.out.println("dbserver = "+ConfClass.DBSERVER);
                                mjdbc=new MyJdbc();
                                cc.writeFile("conf.txt");
                                
                        }
		}
	};
}
