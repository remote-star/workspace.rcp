package commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.sources.Projects;
import toaster.views.ProjectsSourceCodeView;
import toaster.views.ProjectsTestView;

public class InputHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();

		DirectoryDialog dialog = new DirectoryDialog(shell,SWT.OPEN);    
		String path = dialog.open();
		
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		IViewPart sourcViewPart = window.getActivePage().findView(ProjectsSourceCodeView.ID);
		ProjectsSourceCodeView sview = (ProjectsSourceCodeView)sourcViewPart;
		
		IViewPart testVviewPart = window.getActivePage().findView(ProjectsTestView.ID);
		ProjectsTestView tview = (ProjectsTestView)testVviewPart;
		
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.CANCEL);
		mb.setMessage("是否复制到工作空间");
		mb.setText("确认");
		boolean copy = mb.open() == SWT.OK;
		
		if(Projects.getInstance().input(path, copy)){
			sview.refresh();
			tview.refresh();
		}
		return null;
	}

}
