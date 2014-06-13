package toaster.sources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import toaster.tools.FileTools;
import toaster.views.ProjectsSourceCodeView;
import toaster.views.ProjectsTestView;

public class Projects {

	private ArrayList<Project> projectList = new ArrayList<>();
	static Projects projects;
	ProjectsSourceCodeView sview;
	ProjectsTestView tview;
	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	
	
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
				File projectFile = new File(line);
				line = reader.readLine();  
				if(!projectFile.exists()){
					continue;
				}
				projectList.add(new Project(projectFile));
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		File testFolder = new File(Configuration.getWorkspacePath() + "source");
		for(File f : testFolder.listFiles()){
			if(f.isDirectory()){
				projectList.add(new Project(f));
			}
		}
		
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		IViewPart sourcViewPart = window.getActivePage().findView(ProjectsSourceCodeView.ID);
		sview = (ProjectsSourceCodeView)sourcViewPart;

		IViewPart testVviewPart = window.getActivePage().findView(ProjectsTestView.ID);
		tview = (ProjectsTestView)testVviewPart;
	}

	public ArrayList<Project> getProjectList(){
		return projectList;
	}

	public void input(String path){
		File newProject = new File(path);
		if(!newProject.exists()){
			return;
		}
		String projectName = newProject.getName();
		for(Project o : projectList){
			if(o.getName().equals(projectName)){
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("已有同名项目，不能重复导入");
				mb.setText("不能导入");
				mb.open();
				return;
			}
		}
		
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.CANCEL);
		mb.setMessage("是否复制到工作空间");
		mb.setText("确认");
		boolean copy = mb.open() == SWT.OK;
		
		projectList.add(new Project(newProject, copy));
		
		refreshProjectTreeView();
			
		//如果复制到工作空间 则不需要向配置文件中记录项目路径
		if(copy){
			return;
		}
		File configFile = new File("config\\ProjectsList.txt");  
		try {  
			BufferedWriter writer  = new BufferedWriter(new FileWriter(configFile, true));
			writer.write(path + "\r\n");  
			writer.flush();  
			writer.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	public void removeProject(ArrayList<Project> toRemove) {
		projectList.removeAll(toRemove);
		ArrayList<String> PathToRemove = new ArrayList<>();
		for(Project p : toRemove){
			PathToRemove.add(p.getPath());
			System.out.println(p.copied);
			if(p.copied == true){
				FileTools.getInstance().deleteDirectory(p.sourceProjectFolder.getAbsolutePath());
			}
			FileTools.getInstance().deleteDirectory(p.testProjectFolder.getAbsolutePath());
		}
		try {             
			File file = new File("config\\ProjectsList.txt");     
			FileInputStream fis = new FileInputStream(file);  
			InputStreamReader isr = new InputStreamReader(fis);         
			BufferedReader br = new BufferedReader(isr);           
			StringBuffer buf = new StringBuffer();    
			String temp;
			// 保存该行前面的内容             
			while((temp = br.readLine()) != null){ 
				if(!PathToRemove.contains(temp)){
					buf = buf.append(temp);              
					buf = buf.append(System.getProperty("line.separator")); 
				}
			}                     
			br.close();             
			FileOutputStream fos = new FileOutputStream(file);    
			PrintWriter pw = new PrintWriter(fos);        
			pw.write(buf.toString().toCharArray());          
			pw.flush();   
			pw.close();    
		} catch (IOException e) {    
			e.printStackTrace();       
		}
		refreshProjectTreeView();
	}
	
	public void refreshProjectTreeView(){
		sview.refresh();
		tview.refresh();
	}
}
