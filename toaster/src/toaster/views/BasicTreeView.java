package toaster.views;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.ViewPart;

import toaster.editors.PathEditorInput;
import toaster.editors.XML.XMLEditor;
import toaster.providers.FileTreeContentProvider;
import toaster.sources.Project;
import toaster.tools.EditorTools;
import toaster.tools.FileTools;

public class BasicTreeView extends ViewPart {

	

	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
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
				if(selected instanceof Project){
					tv.setExpandedState(selected, !tv.getExpandedState(selected));
					return;
				}
				if (selected instanceof File){
					File file = (File)selected;
					if(file.isDirectory()){
						tv.setExpandedState(file, !tv.getExpandedState(file));
						return;
					} else {
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
						if(!FileTools.openable(file)){
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("此文件类型不适宜用编辑器打开");
							mb.setText("不能打开");
							mb.open();
							return;
						}

						EditorTools.openEditor(file);
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
	
	public void remove(ArrayList<File> toDelete) {
		Object[] roots = ((ITreeContentProvider)tv.getContentProvider()).getElements("root");
		ArrayList<Object> list = new ArrayList();
		for(Object o : roots){
			if(!toDelete.contains(o)){
				list.add(o);
			}
		}
		roots = list.toArray();
	}

	public void refresh() {
		System.out.println("refresh");
		tv.refresh();
	}

}
