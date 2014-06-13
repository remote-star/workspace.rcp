package toaster.workers;

import java.io.File;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import toaster.editors.XML.XMLEditor;

public class Worker {
	
	
	public static void openEditor(Tree tree){
		//获得当前选中的一项
		TreeItem item;
		if(tree.getSelection().length > 0){
			item = tree.getSelection()[0];
		} else {
			return;
		}
		File file = new File((String)item.getData());
		if(!file.isDirectory()){
			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
//			CodeEditorInput editorInput = new CodeEditorInput();
			String relativePath = getPath(item);
			
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
			
//			// 打开该编辑器
//			try {
//////				CodeEditor editor = (CodeEditor)window.getActivePage().openEditor(editorInput, XMLEditor.ID);
////				editor.readFile(file);
////				editor.setMyTitle(item.getText(), relativePath);
////				editor.setFocus();
//			} catch (PartInitException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

	private static boolean openable(File file) {
		if(file.getAbsolutePath().endsWith(".exe")){
			return false;
		}
		return true;
	}
	
	public static String getPath(TreeItem it){
		if(it.getParentItem() != null){
			return getPath(it.getParentItem()) + "\\" + it.getText();
		} else {
			return it.getText();
		}
		
	}
}
