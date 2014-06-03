package commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.views.ProjectsSourceCodeView;
import toaster.views.ProjectsTestView;

public class InputHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();

		DirectoryDialog dialog = new DirectoryDialog(shell,SWT.OPEN);    
		String fileName = dialog.open();
		
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IViewPart sourcViewPart = window.getActivePage().findView(ProjectsSourceCodeView.ID);
		ProjectsSourceCodeView sview = (ProjectsSourceCodeView)sourcViewPart;
		
		IViewPart testVviewPart = window.getActivePage().findView(ProjectsTestView.ID);
		ProjectsTestView tview = (ProjectsTestView)testVviewPart;
		
		if(sview.input(fileName) && tview.input(fileName)){
			File file = new File("config\\ProjectsList.txt");  
			try {  
				BufferedWriter writer  = new BufferedWriter(new FileWriter(file, true));
				writer.write(fileName + "\r\n");  
				writer.flush();  
				writer.close();  
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
		
		return null;
	}

}
