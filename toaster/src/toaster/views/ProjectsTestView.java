package toaster.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import toaster.providers.FileTreeContentProvider;
import toaster.providers.TestTreeContentProvider;
import toaster.providers.TreeLabelProvider;
import toaster.sources.ImageShop;
import toaster.workers.Worker;

public class ProjectsTestView extends BasicTreeView{

	public static final String ID = "toaster.views.ProjectsTestView";
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		tv.setContentProvider(new TestTreeContentProvider()); 
		// 添加标签管理器
		tv.setLabelProvider(new TreeLabelProvider()); 
		// 设置treeviewer的输入
		tv.setInput("root"); // pass a non-null that will be ignored
	}
	
	public boolean isProject(File f ){
		Object[] roots = ((TestTreeContentProvider)(tv.getContentProvider())).getElements("root");
		for(Object o : roots){
			if(o.equals(f)){
				return true;
			}
		}
		return false;
	}

}
