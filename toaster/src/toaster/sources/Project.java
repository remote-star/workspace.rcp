package toaster.sources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


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
				copyDirectiory(sourceProject, sourceProjectFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sourceProjectFolder = sourceProject;
		}
		testProjectFolder = new File(Configuration.getWorkspacePath() + "test" + File.separator + projectName);
	}
	
	public Project(File sourceProject){
		//显示初始项目列表时用此方法创建项目类
		projectName = sourceProject.getName();
		sourceProjectFolder = sourceProject;
		testProjectFolder = new File(Configuration.getWorkspacePath() + "test" + File.separator + projectName);
		if(!testProjectFolder.exists()){
			System.out.println("no exists");
			System.out.println(testProjectFolder.getAbsolutePath());
			System.out.println(testProjectFolder.mkdirs());
		}
		if(sourceProjectFolder.getPath().indexOf(Configuration.getWorkspacePath())>0){
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

    // 复制文件
    public void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    // 复制文件夹
    public void copyDirectiory(File sourceDir, File targetDir) throws IOException {
        // 新建目标目录
        targetDir.mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = sourceDir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(targetDir.getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                File dir1 = new File(sourceDir + "/" + file[i].getName());
                // 准备复制的目标文件夹
                File dir2 = new File(targetDir + "/" + file[i].getName());
                copyDirectiory(dir1, dir2);
            }
        }
    }
	
	
}
