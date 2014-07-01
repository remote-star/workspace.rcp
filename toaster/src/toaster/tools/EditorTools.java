package toaster.tools;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import toaster.editors.PathEditorInput;

public class EditorTools {

	static IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
	
	public static boolean openEditor(File file){
		IEditorInput input= createEditorInput(file);
		String editorId= getEditorId(file);
		IWorkbenchPage page= window.getActivePage();
		try {
			page.openEditor(input, editorId);
			return true;
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static IEditorInput createEditorInput(File file) {
		IPath location= new Path(file.getAbsolutePath());
		PathEditorInput input= new PathEditorInput(location);
		return input;
	}
	
	private static String getEditorId(File file) {
		IWorkbench workbench= window.getWorkbench();
		IEditorRegistry editorRegistry= workbench.getEditorRegistry();
		IEditorDescriptor descriptor= editorRegistry.getDefaultEditor(file.getName());
		if (descriptor != null)
			return descriptor.getId();
		return "org.eclipse.ui.examples.rcp.texteditor.editors.SimpleEditor"; //$NON-NLS-1$
	}
}
