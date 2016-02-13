/*
MyXML.java
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;


public class MyXML  {

	protected JTextField date;
	protected JTextField time;
	protected JTextField veloce;
	protected JTextField distance;
	protected JTextField track;
	protected JTextField note;
        
        protected ResultSet rset;
        protected FileWriter fw;
        protected BufferedWriter out;
        protected int deep = 0;
        protected String myNode = "";
        protected PreparedStatement stmt=null;
        protected MyJdbc mjdbc = null;
        protected MyTable mtb = null;
        protected int id = 0;        
        
	public MyXML(ResultSet rset) {

          this.rset=rset;

	}
        
	public MyXML(MyTable mtb,MyJdbc mjdbc, int id) {

          this.mjdbc = mjdbc;
          this.mtb=mtb;
          this.rset=mtb.getRset();          
          this.id = id;

	}
	protected void exportXML(JFrame f,String label,String msg)   {
              
                try {
//file                        
                fw = new FileWriter("exportXML.xml");
                out = new BufferedWriter(fw);
                        
//XML
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        factory.setValidating(true);
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.newDocument();
                        Element rootElement=document.createElement("cyclo");
                        document.appendChild(rootElement);
                        Element element;
                        Text newText;
     
                        rset.beforeFirst();
                        ResultSetMetaData rsmd=rset.getMetaData();
                        int count =rsmd.getColumnCount();
                         
                        while(rset.next()) {
			        for(int i=3;i<=count;i++) {
                                    element=document.createElement((rsmd.getColumnName(i)).toString());                                        
                                    newText = document.createTextNode((rset.getObject(i)).toString());
                                    element.appendChild(newText);
                                    rootElement.appendChild(element);                                    

                                }
                        }
                       
                        displayFileTree(document.getDocumentElement());
                        

                        out.close();
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }

	}
        protected void importXML (JFrame f,String importXML,String msg)   {
                try {
//XML
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(importXML);
/*                        
                        Element rootElement=document.createElement("cyclo");
                        document.appendChild(rootElement);
                        Element element;
                        Text newText;
       
                        rset.beforeFirst();
                        ResultSetMetaData rsmd=rset.getMetaData();
                        int count =rsmd.getColumnCount();
//		columnHeaders=new Vector(count);
//		tableData=new Vector();
                         
                        while(rset.next()) {
//			rowData=new Vector(count);
			        for(int i=3;i<=count;i++) {
                                    element=document.createElement((rsmd.getColumnName(i)).toString());                                        
                                    newText = document.createTextNode((rset.getObject(i)).toString());
                                    element.appendChild(newText);
                               rootElement.appendChild(element);                                    

                                }
                        }
  */                     
                        displayTree(document.getDocumentElement());
			mtb.refreshTable("select * from cyklo");                        
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }

        }
        protected void displayFileTree(Node node) {
                short nodeType = node.getNodeType();
                switch(nodeType) {
                        case Node.ELEMENT_NODE:
                          printFileElement((Element)node);
                        break;
                        case Node.TEXT_NODE:
                          printFileText((Text)node);      
                        break;
                        default:
                          System.out.println("default");                        
                        break;  
                        
                }
        }
        protected void displayTree(Node node) {
                short nodeType = node.getNodeType();
                switch(nodeType) {
                        case Node.ELEMENT_NODE:
                          printElement((Element)node);
                        break;
                        case Node.TEXT_NODE:
                          printText((Text)node);      
                        break;
                        default:
                          System.out.println("default");                        
                        break;  
                        
                }
        }
        
        protected void printFileElement(Element node)  {
              System.out.println("<" +node.getNodeName() +">");
              Node child;
              NodeList children = node.getChildNodes();
              int count = children.getLength();
              try {
                      out.write("<" +node.getNodeName() +">");
                      out.newLine();              
                      
              
                      for(int i=0;i<count;i++) {
                              child=children.item(i);
                              displayFileTree(child);
                      }
              
                      out.write("</" +node.getNodeName() +">");
                      out.newLine();
     
              } catch(IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
              }
              System.out.println("</" +node.getNodeName() +">");
        }
        protected void printFileText(CharacterData node) {
                System.out.println(node.getData());
                try {
                        out.write(node.getData());
                        out.newLine();
                } catch(IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
        }
        protected void printElement(Element node)  {
//              System.out.println("<" +node.getNodeName() +">");
              myNode = node.getNodeName();
              Node child;
              NodeList children = node.getChildNodes();
              int count = children.getLength();
      
              for(int i=0;i<count;i++) {
                      child=children.item(i);
                      displayTree(child);
              }
  //            System.out.println("</" +node.getNodeName() +">");
        }
        
        protected void printText(CharacterData node) {
			try {
                if(!(node.getData()).equals("\n")) {
                  if(myNode.equals("date")) {
                          System.out.println(myNode+":"+node.getData());

		          stmt=(mjdbc.getConnection()).prepareStatement(
				"insert into cyklo set date=?,time=?,veloce=?, distance=?,track=?,note=?,id_user=?"
			  );
  
                          stmt.setDate(1,Date.valueOf((node.getData()).trim()));
                  } else if(myNode.equals("time")) {
 			  stmt.setString(2,(node.getData()).trim());                          
                  }  else if(myNode.equals("veloce")) {
                          stmt.setDouble(3,Double.valueOf((node.getData()).trim()));                          
                  }  else if(myNode.equals("distance")) {
  			  stmt.setDouble(4,Double.valueOf((node.getData()).trim()));
                  }  else if(myNode.equals("track")) {
			  stmt.setString(5,(node.getData()).trim());
                  }  else if(myNode.equals("note")) {
		        stmt.setString(6,(node.getData()).trim());
                        stmt.setInt(7,id);                        
		        stmt.executeUpdate(); 
                  }                          
		  
               }
               	} catch(Exception exc) {
				System.out.println(exc.getMessage());
      				exc.printStackTrace();

			}
        }
}
