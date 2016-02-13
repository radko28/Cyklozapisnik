import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;


public abstract class ChartPainter extends ComponentUI {
        protected Font textFont=new Font("Serif",Font.PLAIN,12);
        protected Color textColor=Color.black;
        protected Color colors[]=new Color[] {
                Color.red,Color.blue,Color.yellow,Color.black,Color.green,
                Color.white,Color.gray,Color.cyan,Color.magenta,Color.darkGray
        };
        protected double values[]=new double[0];        
        protected String labels[]=new String[0];
        protected String view = new String();
        protected int day = 0;
        protected String constVar = new String();
        protected String colName = new String();        
        
        
        
        public void setTextFont(Font f) {textFont=f;}
        public Font getTextFont() {return textFont;}
        
        public void setColor(Color[] clist) {colors=clist;}
        public Color[] getColor() {return colors;}
        
        public void setColor(int index,Color c) {colors[index]=c;}
        public Color getColor(int index) {return colors[index];}
        
        public void setTextColor(Color c) {textColor=c;}
        public Color getTextColor() {return textColor;}
        
        public void setLabels(String[] l) {labels=l;}
        public void setValues(double[] v) {values=v;}
        public void setView(String v) {view = v;}
        public void setDay(int v) {day = v;}
        public void setVar(String v) {constVar = v;}
        public void setName(String v) {colName = v;}        
        
        public abstract int indexOfEntryAt(MouseEvent me);
        public abstract void paint(Graphics g,JComponent c);        
}
