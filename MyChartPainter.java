/*
MyChartPainter.java
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.FontMetrics;
import javax.swing.*;
import javax.swing.plaf.*;



public class MyChartPainter extends ChartPainter {
        
        protected static MyChartPainter chartUI=new MyChartPainter();
        int dx2 = 0;
        int tick = 10;
        int y2 = 0;
        int dx = 70;
        int dy = 40;                
        int nameY  = 50;
        Dimension size = null;        
        
        public int indexOfEntryAt(MouseEvent me) {
                
                return -1;
        }
        
        public void paint(Graphics g,JComponent c) {
                size=c.getSize();
                
                int x2=(int)size.getWidth() - 200;
                y2=(int)size.getHeight() - dx;
                dx2 = (int)(x2/(labels.length));
//os x                
                g.drawLine(dy,y2  - 3*tick,x2 + dx,y2 - 3*tick);        
//os y                
                g.drawLine(dy , dx, dy, y2 - 2*tick);        
/*
                Font f = g.getFont();
                int vel = f.getSize();
                LineMetrics metrics = f.getLineMetrics( labels[0],g );
*/
//y
                int maxY = 0;
                for(int i=0;i<labels.length;i++) {
                        maxY = (values[i] > maxY)?(int)values[i]:maxY; 
                }
                for(int i=0;i<=(maxY+10);i+=10) {
//                        g.drawString(" "+values[i],dy - 20,y2-(int)values[i]*(int)size.getHeight()/100);
//                        g.drawString(" "+i,dy - 20,y2 - i*(int)size.getHeight()/100);
//labels
                        g.drawString(" "+i,dy - 30,y2 - i*(int)size.getHeight()/100 - (int)(2.5*tick));
//ticks                        
//                        g.drawLine(dx2*i + dx,y2 - 3*tick,dx2*i + dx,y2 - 2*tick);
                        g.drawLine(dy ,y2 - i*(int)size.getHeight()/100 - 3*tick,dy - tick ,y2 - i*(int)size.getHeight()/100 - 3*tick);
                }
//values                 
                int j =0;
                
                for(int i=0;i<values.length;i++) {
                        if(values[i] > 0.0)
                          drawCross(g,i);
                          if(values.length >= (i+2)) {
                                  if(values[i] > 0.0)
                                          j = i;
                                  if((j < i) && values[i+1] > 0.0 && values[j] > 0.0) {
//                                    g.drawLine(dx2*i + dx ,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick ,dx2*(i+1) + dx ,y2-(int)values[i+1]*(int)size.getHeight()/100 - 3*tick);
                                        g.drawLine(dx2*j + dx ,y2-(int)values[j]*(int)size.getHeight()/100 - 3*tick ,dx2*(i+1) + dx ,y2-(int)values[i+1]*(int)size.getHeight()/100 - 3*tick);
                                  } else if( values[i+1] > 0.0  && values[i] > 0.0) {
                                        g.drawLine(dx2*i + dx ,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick ,dx2*(i+1) + dx ,y2-(int)values[i+1]*(int)size.getHeight()/100 - 3*tick);
                                  }
                          }
                        }
//                        g.fillOval(dx2*i + dx,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick ,20,20);
//x
                Font font = g.getFont();
                FontMetrics metrics = g.getFontMetrics( font );
                for(int i=0;i<labels.length;i++) {
//ticks         
                        g.drawLine(dx2*i + dx,y2 - 3*tick,dx2*i + dx,y2 - 2*tick);
//labels
                        int width = metrics.stringWidth( labels[i] );
//                        g.drawString(labels[i],dx2*i + dx +tick,y2);
                        g.drawString(labels[i],dx2*i + dx -width/2,y2);
                }
//x variable                
                g.drawString(view,x2,y2+3*tick);     
//y variable
//konst
                g.drawString(constVar,x2/2,20);
//y name variable                
                g.drawString(colName,dy - 20,50);
                
        }
        public static ComponentUI createUI(JComponent c) {
                return chartUI;
        }
        public void drawCross(Graphics g, int i) {
//x                
                g.drawLine(dx2*i + dx -10,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick ,dx2*i + dx + 10,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick);
//y
                g.drawLine(dx2*i + dx,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick +10,dx2*i + dx,y2-(int)values[i]*(int)size.getHeight()/100 - 3*tick-10);
        }
}
