import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.Dimension;



public class MyTree {

	protected JTree myTree;
	protected MyTable mtb;
	protected static String[] childs={"myBook","address"};
	protected DefaultMutableTreeNode leadSelection;
	protected DefaultMutableTreeNode root;

	public MyTree(MyTable mtb) {
		this.mtb=mtb;
                
		myTree=new JTree(getRootNode());

	}
	public JTree getTree() {
		myTree.addTreeSelectionListener(selectionListener);
		return myTree;
	}
	protected MutableTreeNode getRootNode() {
		root=new DefaultMutableTreeNode("cyclobook");
		DefaultMutableTreeNode child;
		for(int i=0;i<childs.length;i++) {
			child=new DefaultMutableTreeNode(childs[i]);
			root.add(child);
		}
		return root;
	}
	TreeSelectionListener selectionListener=new TreeSelectionListener() {
		public void valueChanged(TreeSelectionEvent event) {
			TreePath leadPath=event.getNewLeadSelectionPath();
			leadSelection=(DefaultMutableTreeNode)leadPath.getLastPathComponent();
			System.out.println(leadSelection+":"+root.getIndex(leadSelection));

			if((String.valueOf(leadSelection)).compareToIgnoreCase(childs[0])==0) {
//myBook
				mtb.refreshTable("select * from cyklo order by date",root.getIndex(leadSelection));
			} else if((String.valueOf(leadSelection)).compareToIgnoreCase(childs[1])==0) {
//address
				mtb.refreshTable("select * from address",root.getIndex(leadSelection));
			}

		}
	};
}
