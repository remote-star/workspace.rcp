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

public class FileTreeContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object arg0) { 
		//返回树的下一级节点
		if(arg0 instanceof Project){
			return ((Project)arg0).listFiles();
		}
		return ((File) arg0).listFiles();
	}
	
	public Object getParent(Object arg0) {

		// 返回树的上一级节点  
		if(arg0 instanceof Project){
			return "root";
		}
		File child = (File)arg0;
		for(Project p : Projects.getInstance().getProjectList()){
			if(p.getSourceFolderFile().equals(child.getParentFile()) || p.getTestFolderFile().equals(child)){
				return p;
			}
		}
		return ((File) arg0).getParentFile(); 
	}

	public boolean hasChildren(Object arg0) { 
		Object[] obj = getChildren(arg0);
		// 判断树是否有下一级节点，true为在节点显示"+"信息 
		return obj == null ? false : obj.length > 0;
	}

	public Object[] getElements(Object arg0) { 
		return Projects.getInstance().getProjectList().toArray();
	}
	
	public void dispose() { 
	}
	
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) { 

	}
}
