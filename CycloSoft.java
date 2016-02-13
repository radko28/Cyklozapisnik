/*
Cyclosoft.java
*/

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class CycloSoft extends JFrame {

	protected Connection conn;
	protected JToolBar jtb;
	protected MyTool mt;
	protected MyTable mtb;
	protected MyJdbc mjdbc;
        protected ConfClass cc = null;
        protected static String file = "";


  	public static void main(String[] args)  {
                if(args.length > 0 )
                        file = args[0];
                
 		CycloSoft cs = new CycloSoft("CycloSoft 2.0");
    		cs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cs.setSize(1350,600);
		cs.setVisible(true);
  	}
        
  	public CycloSoft(String title)  {
    		super(title);
                cc = new ConfClass(file);  
                String choice =cc.LOOK;
                String look = "";
                if(choice.equals("motif")) {
                        look = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                } else if(choice.equals("gtk")) {
                        look = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                } else if(choice.equals("metal")) {
                        look = "javax.swing.plaf.metal.MetalLookAndFeel";
                }
                try {
                        UIManager.setLookAndFeel(look);
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
                
	        mjdbc=new MyJdbc();
		mtb=new MyTable(mjdbc.getConnection());
		Container pane = getContentPane();
                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   (new MyTree(mtb)).getTree(),viewTable());
                JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                               viewTool(),splitPane);                                   
                pane.add(splitPane2);
  	}
	
	public JPanel viewTool() {
    		JPanel panel=new JPanel();
                panel.setLayout(new BorderLayout());
		jtb=new JToolBar();
                jtb.setFloatable(false);                
		jtb.add(mt.getTool());
		panel.add(jtb,BorderLayout.WEST);
		return panel;
	}
        
  	public JPanel viewTable() {
    		JPanel panel=new JPanel();
		mt=new MyTool(this,mtb,mjdbc,cc);
                mtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel.setLayout(new BorderLayout());                
		panel.add(new JScrollPane(mtb),BorderLayout.WEST);
		return panel;
  	}
}
