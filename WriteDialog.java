/*
WriteDialog.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public abstract class WriteDialog {

	protected JButton jbt;

	protected JLabel jlb;
	protected static String CANCEL="cancel";
	protected static String OK="ok";
	protected MyTable mtb;
	protected MyJdbc mjdbc;
	protected JDialog jdg;
        
        

	public WriteDialog(MyTable mtb,MyJdbc mjdbc) {
	  this.mtb=mtb;
	  this.mjdbc=mjdbc;
	}
	protected abstract void showSimpleDialog(JFrame f,String label,String msg, LoginDialog ld);
	protected abstract JPanel Buttons();
}
