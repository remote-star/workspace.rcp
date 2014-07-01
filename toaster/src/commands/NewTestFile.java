package commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.sources.Project;
import toaster.sources.Projects;
import toaster.tools.EditorTools;
import toaster.views.BasicTreeView;
import toaster.views.ProjectsSourceCodeView;

public class NewTestFile extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;

			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			IViewPart sourcViewPart = (IViewPart) window.getActivePage().getActivePart();
			ProjectsSourceCodeView sview = (ProjectsSourceCodeView)sourcViewPart;
			
			Project project = sview.getRoot(strucSelection.getFirstElement());
			if(project != null){
				InputDialog input = new InputDialog(shell,
						"输入文件名",
						"请输入文件名:",
						"", null);
				if(input.open()== Window.OK){
					boolean duplicated = false;
					for(File alreadyExist : project.getTestFolderFile().listFiles()){
						if(alreadyExist.getName().equals(input.getValue())){
							MessageDialog.openError(shell, "已有同名文件", "测试文件夹中已存在同名文件");
							duplicated = true;
							break;
						}
					}
					if(duplicated){
						return null;
					}
					File toNew = new File(project.getTestFolderPath() + File.separator + input.getValue());
					try {
						toNew.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					EditorTools.openEditor(toNew);
					sview.refresh();
				}
			}

		}
		return null;
	}

}
