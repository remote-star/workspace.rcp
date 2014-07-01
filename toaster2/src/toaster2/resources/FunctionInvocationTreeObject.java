package toaster2.resources;

import java.util.ArrayList;

public class FunctionInvocationTreeObject extends TreeObject {

	private ArrayList<FunctionRecord> children;
	
	public FunctionInvocationTreeObject(String name) {
		super(name);
		children = new ArrayList<FunctionRecord>();
	}

	public void addChild(FunctionRecord child) {
		children.add(child);
		child.setParent(this);
	}
	
	public void removeChild(FunctionRecord child) {
		children.remove(child);
		child.setParent(null);
	}
	
	public TreeObject [] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}
}
