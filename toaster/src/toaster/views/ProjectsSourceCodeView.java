package toaster.views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
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


public class ProjectsSourceCodeView extends ViewPart {

	// 该视图的id，在plugin.xml文件中定义，通常使用字符串常量的方式
	public static final String ID = "toaster.views.ProjectsSourceCodeView";

	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	Tree tree;
	
	public ProjectsSourceCodeView() {

	}
	// 为父类中的抽象方法，创建视图中的各种控件
	public void createPartControl(Composite parent) {
		System.out.println("partControl");
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
		
//		Menu menu = new Menu(tree);
//
//		    // 创建谈出菜单的菜单项 
//		    MenuItem newItem = new MenuItem(menu, SWT.CASCADE); 
//		    newItem.setText("New"); 
//		    MenuItem refreshItem = new MenuItem(menu, SWT.NONE); 
//		    refreshItem.setText("Refresh"); 
//		    MenuItem deleteItem = new MenuItem(menu, SWT.NONE); 
//		    deleteItem.setText("Delete"); 
//
//		    new MenuItem(menu, SWT.SEPARATOR); 
//		    // 添加复选菜单项 
//		    MenuItem checkItem = new MenuItem(menu, SWT.CHECK); 
//		    checkItem.setText("Check"); 
//		    checkItem.setSelection(true); 
//
//		    // 添加一个push菜单项 
//		    MenuItem pushItem = new MenuItem(menu, SWT.PUSH); 
//		    pushItem.setText("Push"); 
//
//		    new MenuItem(menu, SWT.SEPARATOR); 
//
//		    // 创建一些单选菜单项 
//		    MenuItem item1 = new MenuItem(menu, SWT.RADIO); 
//		    item1.setText("Radio One"); 
//		    MenuItem item2 = new MenuItem(menu, SWT.RADIO); 
//		    item2.setText("Radio Two"); 
//		    MenuItem item3 = new MenuItem(menu, SWT.RADIO); 
//		    item3.setText("Radio Three"); 
//
//		    //创建一个单选的菜单项组 
//		    new MenuItem(menu, SWT.SEPARATOR); 
//
//		    // 创建一些单选项 
//		    MenuItem itema = new MenuItem(menu, SWT.RADIO); 
//		    itema.setText("Radio A"); 
//		    MenuItem itemb = new MenuItem(menu, SWT.RADIO); 
//		    itemb.setText("Radio B"); 
//		    MenuItem itemc = new MenuItem(menu, SWT.RADIO); 
//		    itemc.setText("Radio C"); 
//
//		    //创建一个新的下拉菜单 
//		    Menu newMenu = new Menu(menu); 
//		    newItem.setMenu(newMenu); 
//
//		    // 创建此新的下拉菜单的菜单项 
//		    MenuItem shortcutItem = new MenuItem(newMenu, SWT.NONE); 
//		    shortcutItem.setText("Shortcut"); 
//		    MenuItem iconItem = new MenuItem(newMenu, SWT.NONE); 
//		    iconItem.setText("Icon"); 
//
//		    // 设置此菜单与Label关联 
//		    tree.setMenu(menu); 
//		
		
		
		
		
		
		
		
		
//		
//		MenuManager menuManager = new MenuManager();
//		Menu menu = menuManager.createContextMenu(parent);
//		MenuItem item = new MenuItem (menu, SWT.PUSH);
//		item.setText ("Popup");
//		parent.setMenu(menu);
//		getSite().registerContextMenu(menuManager, (ISelectionProvider) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorSite().getSelectionProvider());
	}

	public void setFocus() {
//		viewer.getControl().setFocus();
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
		
		refreshFileList(directory, root);

		tree.setSelection(root);
		
		return true;
	}
	
    public void refreshFileList(String strPath, TreeItem root) { 
        File dir = new File(strPath); 
        File[] files = dir.listFiles(); 
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
        	String filePath = files[i].getAbsolutePath();
        	String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
        	TreeItem newRoot = new TreeItem(root, SWT.NONE);
        	newRoot.setText(fileName);
        	newRoot.setData(filePath);
            if (files[i].isDirectory()) { 
                refreshFileList(files[i].getAbsolutePath(), newRoot); 
                newRoot.setImage(ImageShop.get(ImageShop.FOLDER_IMAGE));
            } else {
                newRoot.setImage(ImageShop.get(ImageShop.FILE_IMAGE));
            }
        } 
    }
}
