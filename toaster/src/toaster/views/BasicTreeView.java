package toaster.views;

import java.io.File;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import toaster.editor.CodeEditor;
import toaster.editor.CodeEditorInput;
import toaster.providers.FileTreeContentProvider;

public class BasicTreeView extends ViewPart {

	

	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	TreeViewer tv;
	
	@Override
	public void createPartControl(Composite parent) {
		tv = new TreeViewer(parent);
		// 添加内容管理器

		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(tv.getControl());
		//		MenuItem item = new MenuItem (menu, SWT.PUSH);
		//		item.setText ("Popup");
		tv.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tv);
		getSite().setSelectionProvider(tv);
		
		tv.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection =
						(IStructuredSelection) event.getSelection();
				Object selected = selection.getFirstElement();
				if (selected instanceof File){
					File file = (File)selected;
					if(file.isDirectory()){
						tv.setExpandedState(file, !tv.getExpandedState(file));
					} else {
						IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
						CodeEditorInput editorInput = new CodeEditorInput();

						TreeItem selectedItem = tv.getTree().getSelection()[0];
						String relativePath = getPath(selectedItem);

						//检查已所选的文件是否已在编辑器中打开，文件的相对路径（在编辑器中的TitleToolTip中存储）作为判断依据
						IEditorReference[] ies = window.getActivePage().getEditorReferences();
						for(IEditorReference ie : ies){
							if(ie.getTitleToolTip().equals(relativePath)){
								window.getActivePage().activate(ie.getEditor(false));
								ie.getEditor(false).setFocus();
								return;
							}
						}

						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						//检测选择文件是否适宜打开显示，如果不是则弹出提示并拒绝打开
						if(!openable(file)){
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("此文件类型不适宜用编辑器打开");
							mb.setText("不能打开");
							mb.open();
							return;
						}

						// 打开该编辑器
						try {
							CodeEditor editor = (CodeEditor)window.getActivePage().openEditor(editorInput, CodeEditor.ID);
							editor.readFile(file);
							editor.setMyTitle(file.getName(), relativePath);
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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public String getPath(TreeItem it){
		if(it.getParentItem() != null){
			return getPath(it.getParentItem()) + "\\" + it.getText();
		} else {
			return it.getText();
		}

	}

	private boolean openable(File file) {
		if(file.getAbsolutePath().endsWith(".exe")){
			return false;
		}
		return true;
	}
}
