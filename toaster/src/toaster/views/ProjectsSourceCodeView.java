package toaster.views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
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
		final TreeViewer tv = new TreeViewer(parent);
		// 添加内容管理器
		tv.setContentProvider(new FileTreeContentProvider()); 
		// 添加标签管理器
		tv.setLabelProvider(new FileTreeLabelProvider()); 
		// 设置treeviewer的输入
		tv.setInput("root"); // pass a non-null that will be ignored

		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(tv.getControl());
		MenuItem item = new MenuItem (menu, SWT.PUSH);
		item.setText ("Popup");
		tv.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tv);

		tv.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				System.out.println("a");
				if (event.getSelection() instanceof File){
					System.out.println("b");
					File file = (File)event.getSelection();
					if(!file.isDirectory()){
						IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
						CodeEditorInput editorInput = new CodeEditorInput();
//						String relativePath = getPath(item);
//						
//						IEditorReference[] ies = window.getActivePage().getEditorReferences();
//						for(IEditorReference ie : ies){
//							if(ie.getTitleToolTip().equals(relativePath)){
//								window.getActivePage().activate(ie.getEditor(false));
//								ie.getEditor(false).setFocus();
//								return;
//							}
//						}
						
//						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
//						//检测选择文件是否适宜打开显示，如果不是则弹出提示并拒绝打开
//						if(!openable(file)){
//							MessageBox mb = new MessageBox(shell);
//							mb.setMessage("此文件类型不适宜用编辑器打开");
//							mb.setText("不能打开");
//							mb.open();
//							return;
//						}
						
						// 打开该编辑器
						try {
							CodeEditor editor = (CodeEditor)window.getActivePage().openEditor(editorInput, CodeEditor.ID);
							editor.readFile(file);
//							editor.setMyTitle(item.getText(), relativePath);
							editor.setFocus();
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	public void setFocus() {
	}
}

class FileTreeContentProvider implements ITreeContentProvider { 

	public Object[] getChildren(Object arg0) { 
		//返回树的下一级节点
		return ((File) arg0).listFiles();
	}
	public Object getParent(Object arg0) {

		// 返回树的上一级节点  
		return ((File) arg0).getParentFile(); 
	}

	public boolean hasChildren(Object arg0) { 
		Object[] obj = getChildren(arg0);
		// 判断树是否有下一级节点，true为在节点显示"+"信息 
		return obj == null ? false : obj.length > 0;
	}

	public Object[] getElements(Object arg0) { 
		ArrayList<File> projects = new ArrayList<>();
		File file = new File("config\\ProjectsList.txt");  
		try {  
			BufferedReader reader = new BufferedReader(new FileReader(file));  
			String line = reader.readLine();  
			while(line!=null){  
				projects.add(new File(line));
				line = reader.readLine();  
			}  
			reader.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return projects.toArray();
	}

	public void dispose() { 

	}
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) { 

	}
}
// 定义标签提供器
class FileTreeLabelProvider implements ILabelProvider { 
	private List listeners;
	private Image file;
	private Image dir;
	
	public FileTreeLabelProvider() { 
		listeners = new ArrayList();
	}

	public Image getImage(Object arg0) { 
		//返回目录或文件的图标 
		return ((File) arg0).isDirectory() ? ImageShop.get(ImageShop.FOLDER_IMAGE) : ImageShop.get(ImageShop.FILE_IMAGE); 
	}
	
	public String getText(Object arg0) { 
		return ((File) arg0).getName();
	}
	
	public void dispose() {
	}
	
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false; 
	} 

	public void removeListener(ILabelProviderListener arg0) { 
		//删除监听器 
		listeners.remove(arg0); 
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
}


