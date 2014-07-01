package commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.sources.Project;
import toaster.views.ProjectsSourceCodeView;

public class NewFolderHandler extends AbstractHandler {

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
			
		}
		return null;
	}

}
