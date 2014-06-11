package toaster.sources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.providers.FileTreeContentProvider;

public class Projects {
	
	private ArrayList<Project> projectList = new ArrayList<>();
	static Projects projects;
	
	public static Projects getInstance(){
		if(projects == null){
			projects = new Projects();
		}
		return projects;
	}

	public Projects(){
		File file = new File("config\\ProjectsList.txt");  
		try {  
			BufferedReader reader = new BufferedReader(new FileReader(file));  
			String line = reader.readLine();  
			while(line!=null){  
				projectList.add(new Project(new File(line)));
				line = reader.readLine();  
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
	
	public ArrayList<Project> getProjectList(){
		return projectList;
	}
	
	public boolean input(String path, boolean copy){
		File newProject = new File(path);
		if(!newProject.exists()){
			return false;
		}
		String projectName = newProject.getName();
		for(Project o : projectList){
			if(o.getName().equals(projectName)){
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("已有同名项目，不能重复导入");
				mb.setText("不能导入");
				mb.open();
				return false;
			}
		}
		projectList.add(new Project(newProject, copy));
		
		File configFile = new File("config\\ProjectsList.txt");  
		try {  
			BufferedWriter writer  = new BufferedWriter(new FileWriter(configFile, true));
			writer.write(projectName + "\r\n");  
			writer.flush();  
			writer.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		return true;
	}
}
