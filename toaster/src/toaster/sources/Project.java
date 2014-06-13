package toaster.sources;

import java.io.File;
import java.io.IOException;

import toaster.tools.FileTools;


public class Project {
	
	String projectName;
	File sourceProjectFolder;
	File testProjectFolder;
	boolean copied;
	
	public Project(File sourceProject, boolean copy){
		this.copied = copy;
		projectName = sourceProject.getName();
		if(copied){
			sourceProjectFolder = new File(Configuration.getWorkspacePath() + "source" + File.separator + projectName);
			try {
				FileTools.getInstance().copyDirectiory(sourceProject, sourceProjectFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sourceProjectFolder = sourceProject;
		}
		testProjectFolder = new File(Configuration.getWorkspacePath() + "test" + File.separator + projectName);
		if(!testProjectFolder.exists()){
			testProjectFolder.mkdirs();
		}
	}
	
	public Project(File sourceProject){
		//显示初始项目列表时用此方法创建项目类
		projectName = sourceProject.getName();
		sourceProjectFolder = sourceProject;
		testProjectFolder = new File(Configuration.getWorkspacePath() + "test" + File.separator + projectName);
		if(!testProjectFolder.exists()){
			testProjectFolder.mkdirs();
		}
		if(sourceProjectFolder.getPath().indexOf(Configuration.getWorkspacePath()) >= 0){
			copied = true;
		} else {
			copied = false;
		}
	}
	
	public String getName(){
		return projectName;
	}
	
	public File[] listSourceFiles(){
		return sourceProjectFolder.listFiles();
	}
	
	public File[] listTestFiles(){
		return testProjectFolder.listFiles();
	}
	
	public Object getParentFile(){
		return "root";
	}

	public String getPath(){
		return sourceProjectFolder.getAbsolutePath();
	}
}
