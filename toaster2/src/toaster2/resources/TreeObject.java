package toaster2.resources;

import org.eclipse.core.runtime.IAdaptable;


public class TreeObject implements IAdaptable {

	private String name;
	private TreeObject parent;
	
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TreeObject(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setParent(TreeObject parent) {
		this.parent = parent;
	}
	public TreeObject getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}

}
