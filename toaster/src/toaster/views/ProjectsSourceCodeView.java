package toaster.views;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import toaster.providers.FileTreeContentProvider;
import toaster.providers.TreeLabelProvider;
import toaster.sources.Project;

public class ProjectsSourceCodeView extends BasicTreeView {

	// 该视图的id，在plugin.xml文件中定义，通常使用字符串常量的方式
	public static final String ID = "toaster.views.ProjectsSourceCodeView";

	// 为父类中的抽象方法，创建视图中的各种控件
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		tv.setContentProvider(new FileTreeContentProvider()); 
		// 添加标签管理器
		tv.setLabelProvider(new TreeLabelProvider()); 
		// 设置treeviewer的输入
		tv.setInput("root"); // pass a non-null that will be ignored
	}
	
	public boolean isProject(File f ){
		Object[] roots = ((FileTreeContentProvider)(tv.getContentProvider())).getElements("root");
		for(Object o : roots){
			if(o.equals(f)){
				return true;
			}
		}
		return false;
	}
	
	public Project getRoot(Object element) {
		FileTreeContentProvider provider = (FileTreeContentProvider)(tv.getContentProvider());
		while(!(element instanceof Project)){
			element = provider.getParent(element);
		}
		return (Project)element;
	}
}


