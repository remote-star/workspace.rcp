package commands;

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

import toaster.views.ProjectsView;

public class InputHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();

		DirectoryDialog dialog = new DirectoryDialog(shell,SWT.OPEN);    
		dialog.setFilterPath("c:\\windows");//设置初始路径    
		String fileName = dialog.open();
		
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IViewPart viewPart = window.getActivePage().findView(ProjectsView.ID);
		ProjectsView view = (ProjectsView)viewPart;
		view.input(fileName);
		
		return null;
	}

}
