package toaster.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestTreeContentProvider implements ITreeContentProvider {

	Object[] roots;

	public TestTreeContentProvider(){
		//		ArrayList<String> projectNames = new ArrayList<>();
		ArrayList<File> projectFolders = new ArrayList<>();
		File file = new File("config\\ProjectsList.txt");  
		try {  
			BufferedReader reader = new BufferedReader(new FileReader(file));  
			String line = reader.readLine();  
			while(line!=null){  
				String projectName = line.substring(line.lastIndexOf("\\")+1);
				//				projectNames.add(pojectName);
				File projectFolder = new File("testFiles\\" + projectName);
				if(!projectFolder.exists()){
					projectFolder.mkdir();
				}
				projectFolders.add(projectFolder);
				line = reader.readLine();  
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		roots = projectFolders.toArray();
	}


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
		return roots;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		//返回树的下一级节点
		return ((File) parentElement).listFiles();
	}

	@Override
	public Object getParent(Object element) {
		// 返回树的上一级节点  
		return ((File) element).getParentFile(); 
	}

	@Override
	public boolean hasChildren(Object element) {
		Object[] obj = getChildren(element);
		// 判断树是否有下一级节点，true为在节点显示"+"信息 
		return obj == null ? false : obj.length > 0;
	}
	
	public void setRoots(Object[] newRoots){
		this.roots = newRoots;
	}

}
