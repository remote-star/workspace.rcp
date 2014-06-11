package toaster.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import toaster.sources.Project;
import toaster.sources.Projects;

public class TestTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		return Projects.getInstance().getProjectList().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		//返回树的下一级节点
		if(parentElement instanceof Project){
			return ((Project) parentElement).listTestFiles();
		}
		return ((File) parentElement).listFiles();
	}

	@Override
	public Object getParent(Object element) {
		// 返回树的上一级节点  
		if(element instanceof Project){
			return "root";
		}
		return ((File) element).getParentFile(); 
	}

	@Override
	public boolean hasChildren(Object element) {
		Object[] obj = getChildren(element);
		// 判断树是否有下一级节点，true为在节点显示"+"信息 
		return obj == null ? false : obj.length > 0;
	}
}
