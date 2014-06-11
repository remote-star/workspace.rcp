package toaster.sources;

import java.io.File;

public class Configuration {

	private static String workspacePath = "ToasterWorkspace" + File.separator;	
	
	static public String getWorkspacePath(){
		return workspacePath;
	}
	
	static public void setWorkspacePath(String path){
		workspacePath = path;
	}
	
}
