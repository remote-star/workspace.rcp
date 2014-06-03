package toaster.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

import toaster.editor.CodeEditor;
import toaster.editor.CodeEditorInput;
import toaster.sources.ImageShop;
import toaster.workers.Worker;

public class ProjectsTestView extends ViewPart{

	public static final String ID = "toaster.views.ProjectsTestView";
	Tree tree;
	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	
	@Override
	public void createPartControl(Composite parent) {
		tree = new Tree(parent, SWT.BORDER | SWT.SINGLE);
		tree.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(event.button != 1) { //按键不是左键跳出. 1左键,2中键,3右键  
		            return;  
		        }  
				Worker.openEditor(tree);
			}
		});
		tree.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) { 
					case SWT.KEYPAD_CR: 
					case SWT.CR:
						Worker.openEditor(tree);
						break; 
				}
			}
		});
		

		File file = new File("config\\ProjectsList.txt");  
		try {  
			BufferedReader reader = new BufferedReader(new FileReader(file));  
			String line = reader.readLine();  
			while(line!=null){  
				input(line);
				line = reader.readLine();  
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public boolean input(String directory) {
		
		String ProjectName = directory.substring(directory.lastIndexOf("\\")+1);
		
		for ( TreeItem it : tree.getItems()){
			if(it.getText().equals(ProjectName)){
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("已有同名项目，不能重复导入");
				mb.setText("不能导入");
				mb.open();
				tree.setSelection(it);
				return false;
			}
		}
		
		TreeItem root = new TreeItem(tree, SWT.NONE);
		root.setText(ProjectName);
		root.setData(directory);
		root.setImage(ImageShop.get(ImageShop.PROJECT_IMAGE));
		

		tree.setSelection(root);
		
		return true;
	}

}
