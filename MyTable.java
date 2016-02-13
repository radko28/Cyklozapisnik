/*
MyTable.java
*/

import javax.swing.*;
import java.sql.*;
import java.awt.Dimension;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MyTable extends JTable {

        Connection conn;
        int selectedRow;
        int selectedCol;
        protected int treeIndex = 0;
        protected ResultSet rset; 
        protected String[] tableName = { 
          "cyklo" , "address"
        };

  public MyTable(Connection conn) {
        super();
        this.conn=conn;
        setPreferredScrollableViewportSize(new Dimension(900, 200));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(true);
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                        selectedRow = lsm.getMinSelectionIndex();
                        selectedCol = getSelectedColumn();
                        System.out.println("Row = " + selectedRow + " Col = " + selectedCol);
                }
        });
        try {
                Statement stmt=conn.createStatement();
                rset=stmt.executeQuery("select * from cyklo order by date");
                this.setModel(new TableValues(rset,treeIndex));
        } catch(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
        }
  }
  public void refreshTable(String query, int treeIndex) {
          this.treeIndex = treeIndex;
          try {
                  Statement stmt=conn.createStatement();
                  rset=stmt.executeQuery(query);
                  this.setModel(new TableValues(rset,treeIndex));
          } catch(Exception e) {
                  System.out.println(e.getMessage());
                  e.printStackTrace();
                  System.out.println(query);
          }
  }
  public void refreshTable(String query) {
          System.out.println("treeIndex = " + treeIndex);          
          try {
                  Statement stmt=conn.createStatement();
                  rset=stmt.executeQuery(query);
                  this.setModel(new TableValues(rset,treeIndex));
          } catch(Exception e) {
                  System.out.println(e.getMessage());
                  e.printStackTrace();
                  System.out.println(query);
          }
  }
  public void removeRow(JFrame f, LoginDialog ld) {
          String row = (((TableValues)getModel()).getValueAt2(selectedRow,0)).toString();
          int intRow=Integer.valueOf(row).intValue();
          row = (((TableValues)getModel()).getValueAt2(getRow(),1)).toString();
          int idUser=Integer.valueOf(row).intValue();
          if(ld != null) {
                  if(ld.getId() == idUser) {                  
                          try {
                                  Statement stmt=conn.createStatement();
                                  int changed=stmt.executeUpdate("delete from "+ tableName[treeIndex] + " where id="+intRow);
                                  rset=stmt.executeQuery("select * from " + tableName[treeIndex]);
                                  this.setModel(new TableValues(rset,treeIndex));
                          } catch(Exception e) {
                                  System.out.println(e.getMessage());
                                  e.printStackTrace();
                          }
                          ((TableValues)getModel()).removeRow(selectedRow);
                  } else
//no privaty                
                        JOptionPane.showMessageDialog(f,"excess denied","",JOptionPane.WARNING_MESSAGE);
         } else
//no log
                JOptionPane.showMessageDialog(f,"No logged user","",JOptionPane.WARNING_MESSAGE);
  }
  public int getRow() {
          return selectedRow;
  }
  public int getCol() {
          return selectedCol;
  }
  public int getTreeIndex() {
          return treeIndex;
  }
  public ResultSet getRset() {
          return rset;
  }
 }
