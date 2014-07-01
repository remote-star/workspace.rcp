package toaster2.providers;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import toaster2.resources.FunctionInvocationInformation;
import toaster2.resources.FunctionInvocationTreeObject;
import toaster2.resources.FunctionRecord;
import toaster2.resources.ThreadRecord;
import toaster2.resources.TreeObject;

public class ViewContentProvider implements IStructuredContentProvider,
ITreeContentProvider {

	FunctionInvocationInformation root;
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent instanceof FunctionInvocationTreeObject) {
			return ((FunctionInvocationTreeObject)parent).getChildren();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object child) {
		if (child instanceof FunctionRecord) {
			return ((FunctionRecord)child).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object parent) {
		if (parent instanceof FunctionInvocationTreeObject)
			return ((FunctionInvocationTreeObject)parent).hasChildren();
		return false;
	}

	@Override
	public Object[] getElements(Object parent) {

//		if (parent.equals(getViewSite())) {
//			if (invisibleRoot==null) initialize();
//			return getChildren(invisibleRoot);
//		}
//		return getChildren(parent);
		if(root == null) {
			initialize();
		}
		return root.getThreads().toArray();
	}
	
	private void initialize() {
		FunctionRecord to1 = new FunctionRecord("Leaf 1");
		FunctionRecord to2 = new FunctionRecord("Leaf 2");
		FunctionRecord to3 = new FunctionRecord("Leaf 3");
		ThreadRecord p1 = new ThreadRecord("Parent 1");
		p1.addChild(to1);
		p1.addChild(to2);
		p1.addChild(to3);

		FunctionRecord to4 = new FunctionRecord("Leaf 4");
		ThreadRecord p2 = new ThreadRecord("Parent 2");
		p2.addChild(to4);

		root = new FunctionInvocationInformation("Root");
		root.addThread(p1);
		root.addThread(p2);
	}

}
