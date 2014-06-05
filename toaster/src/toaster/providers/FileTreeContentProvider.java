package toaster.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FileTreeContentProvider implements ITreeContentProvider {

	Object[] roots;
	
	public FileTreeContentProvider(){
		ArrayList<File> projects = new ArrayList<>();
		File file = new File("config\\ProjectsList.txt");  
		try {  
			BufferedReader reader = new BufferedReader(new FileReader(file));  
			String line = reader.readLine();  
			while(line!=null){  
				projects.add(new File(line));
				line = reader.readLine();  
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		roots = projects.toArray();
	}
	
	public Object[] getChildren(Object arg0) { 
		//返回树的下一级节点
		return ((File) arg0).listFiles();
	}
	
	public Object getParent(Object arg0) {

		// 返回树的上一级节点  
		return ((File) arg0).getParentFile(); 
	}

	public boolean hasChildren(Object arg0) { 
		Object[] obj = getChildren(arg0);
		// 判断树是否有下一级节点，true为在节点显示"+"信息 
		return obj == null ? false : obj.length > 0;
	}

	public Object[] getElements(Object arg0) { 
		return roots;
	}
	
	public void dispose() { 
	}
	
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) { 

	}
	
	public void setRoots(Object[] newRoots){
		this.roots = newRoots;
	}

}
