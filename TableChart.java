/*
TableChart.java
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.text.SimpleDateFormat;




public class TableChart extends JComponent implements TableModelListener {
        
        protected ChartPainter cp;
        protected TableValues model;
        protected double[] veloce;
        protected String[] labels;        
        protected int col;
        protected static String ALL="-->all";        
        protected String view = "year";
        protected String year = ALL;
        protected String month = ALL;
        protected GregorianCalendar cal;
        protected int day;
        protected int[] count;
        protected int countYear;
        protected LinkedHashSet yearSet;
        protected HashMap<String, Double>  yearMap;
        protected String constVar = "";
        protected String colName = "";        
                
        
        public TableChart(TableValues tv, int col, FilterDialog fd) {
                this.col = col;
                if(fd != null) {
                        view = fd.getView();
                        year = fd.getYear();
                        month = fd.getMonth();
                        System.out.println("year:"+year +":"+month);
                }
                day = 0;                
                yearMap = new HashMap<String, Double>();                
                setUI(cp=new MyChartPainter());
                setModel(tv);                
        }
        public void tableChanged(TableModelEvent tme) {
                
        }
        public void setModel(TableValues tm) {
          if(tm!=model)
            if(model!=null)
              model.removeTableModelListener(this);
          model=tm;
          model.addTableModelListener(this);
          updateLocalValues(true);
        }
        protected void createVeloce() {
//month                
                if(view.equals("day")) {
                        for(int i=0; i<labels.length;i++) {
                          labels[i]=(String.valueOf(i+1));
                          veloce[i]=0.0;                          
                        }
                        for(int i=model.getRowCount()-1;i>=0;i--) {
                                Object val = model.getValueAt(i,col);
                                Object date = model.getValueAt(i,0);
                                colName = model.getColumnName(col);                                
                                System.out.println("date = "+ (date.toString()).substring(8));
                                veloce[Integer.valueOf((date.toString()).substring(8)).intValue() - 1] = Double.valueOf(val.toString()).doubleValue();
                        }
//year                        
                } else if(view.equals("month")) {
                        for(int i=0; i<labels.length;i++) {
                          labels[i]=(String.valueOf(i+1));
                          veloce[i]=0.0;
                          count[i] = 1;                                
                        }
                        for(int i=0;i<=model.getRowCount()-1;i++) {
                                Object val = model.getValueAt(i,col);
                                Object date = model.getValueAt(i,0);
                                colName = model.getColumnName(col);
                                if(veloce[Integer.valueOf((date.toString()).substring(5,7)).intValue() - 1] >0) {
                                        veloce[Integer.valueOf((date.toString()).substring(5,7)).intValue() - 1] += Double.valueOf(val.toString()).doubleValue();
                                        count[Integer.valueOf((date.toString()).substring(5,7)).intValue() - 1]++;
                                } else
                                        veloce[Integer.valueOf((date.toString()).substring(5,7)).intValue() - 1] = Double.valueOf(val.toString()).doubleValue();
                                System.out.println("year = "+i+":"+Integer.valueOf((date.toString()).substring(5,7)).intValue()+":"+val.toString()+":"+ (date.toString()).substring(5,7));
                                
                        }
                        for(int i=0; i<labels.length;i++) 
                                veloce[i] /= count[i];
                        
                } else if(view.equals("year")) {
                        Object val;
                        Object date;
                        for(int i=0;i<=model.getRowCount()-1;i++) {
                                val = model.getValueAt(i,col);
                                date = model.getValueAt(i,0);
                                colName = model.getColumnName(col);                                
                                yearMap.put((date).toString(),Double.valueOf(val.toString()).doubleValue());
                                System.out.println("map:"+i + ":"+Double.valueOf(val.toString()).doubleValue());                                
                        }
                        int i=0;
                        Map.Entry e;
                        for(Iterator it=yearSet.iterator();it.hasNext();i++) {
                                count[i] = 0;
                                String y = (String)it.next();
                                labels[i] = y;
                                for(Iterator im=yearMap.entrySet().iterator();im.hasNext();) {
                                        e = (Map.Entry)im.next();
                                        if(y.equals((e.getKey().toString()).substring(0,4))) {
                                                count[i]++;
                                                veloce[i] += Double.valueOf(e.getValue().toString()).doubleValue();                                                
                                        }
                                }
                                veloce[i] /= count[i];                                
                                System.out.println("map:"+":"+y+":"+veloce[i]+":"+count[i]);                                
                        }
                }
        }
        protected void updateLocalValues(boolean freshStart) {
                if(freshStart) {
                        if(view.equals("day")) {
                                cal = new GregorianCalendar(Integer.valueOf(year).intValue(),Integer.valueOf(month).intValue() - 1,1);                                
                                day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//                                System.out.println("date:"+Integer.valueOf(year).intValue()+":"+Integer.valueOf(month).intValue() +":"+day);                                
                                labels = new String[day];
                                veloce=new double[day];
                                constVar = month +"-"+year;                                
                        } else if(view.equals(  "month")) {
                                labels = new String[12];
                                veloce=new double[12];
                                count = new int[12];
                                constVar = year;                                
                        } else if(view.equals("year")) {
                                yearSet = new LinkedHashSet();
                                Date date;
                                
                                for(int i=0;i<=model.getRowCount()-1;i++) {
                                        date = (Date)model.getValueAt(i,0);
                                        yearSet.add((new SimpleDateFormat("yyyy")).format(date).toString());
                                        System.out.println("year = "+(new SimpleDateFormat("yyyy")).format(date).toString());                                        
                                }
                                countYear = yearSet.size();
                                labels = new String[countYear];
                                veloce=new double[countYear];
                                count = new int[countYear];
                        }
                }
                createVeloce();
                cp.setLabels(labels);                
                cp.setValues(veloce);
                cp.setView(view);
                cp.setDay(day);
                cp.setVar(constVar);
                cp.setName(colName);                  
                repaint();
        }
}
