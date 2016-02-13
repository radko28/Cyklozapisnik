import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;



public class TableChartPopup extends JFrame {
        public TableChartPopup(TableValues tv, int col, FilterDialog fd) {
                super("Cyclo Graph");
                setSize(800,600);
//                System.out.println("view = " + view);
                TableChart tc=new TableChart(tv, col, fd);
                getContentPane().add(tc,BorderLayout.CENTER);
        }
}
