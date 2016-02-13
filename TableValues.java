/*
cyclosoft 2004
TableValues.java
*/

import javax.swing.table.*;
import javax.swing.*;
import java.util.Vector;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TableValues extends AbstractTableModel {

	protected Vector columnHeaders;
	protected Vector tableData;
	protected Vector tableData2;
     
	public TableValues(ResultSet rset, int indexTable) throws SQLException {

		Vector rowData;
                Vector rowData2;
		ResultSetMetaData rsmd=rset.getMetaData();
		int count =rsmd.getColumnCount();
		tableData=new Vector();
		tableData2=new Vector();
                columnHeaders=new Vector(count);                
                int col = 3;
                boolean jump = false;
                if(indexTable == 1) {
                        col = 2;
                        jump = true;
                } 
		                
                String unit = "";
		for(int i=col;i<=count;i++) {
                        unit = "";
                        if(indexTable == 0 && i == 5) {
                                unit = "[ m/s ]";
                        } else if(indexTable == 0 && i == 6) {
                                unit = "[ km ]";              
                        } else if(jump && i == (col + 1))
                                continue;
			columnHeaders.addElement(rsmd.getColumnName(i) + new String(" "+unit));
		}
		while(rset.next()) {
                                rowData=new Vector(count);                                
			for(int i=col;i<=count;i++) {
                                System.out.println("i = " + i);
                                if(jump && i == (col + 1))
                                        continue;                                
				rowData.addElement(rset.getObject(i));
			}
			tableData.addElement(rowData);
			rowData2=new Vector(col - 1);
			for(int i=1;i<col;i++) {
				rowData2.addElement(rset.getObject(i));
			}
			tableData2.addElement(rowData2);
		}
	}
        
	public int getColumnCount() {
		return columnHeaders.size();
	}
        
	public int getRowCount() {
		return tableData.size();
	}
        
	public Object getValueAt(int row, int column) {
		Vector rowData=(Vector)(tableData.elementAt(row));
		return rowData.elementAt(column);
	}
        
	public Object getValueAt2(int row, int column) {
		Vector rowData=(Vector)(tableData2.elementAt(row));
		return rowData.elementAt(column);
	}
        
	public boolean isCellEditable(int row,int column) {
		return false;
	}
        
	public String getColumnName(int column) {
		return (String)(columnHeaders.elementAt(column));
	}
        
        public void removeRow(int row) {
                tableData.removeElementAt(row);
                fireTableDataChanged();
        }
}
