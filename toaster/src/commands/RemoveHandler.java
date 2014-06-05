package commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.views.ProjectsSourceCodeView;
import toaster.views.ProjectsTestView;

public class RemoveHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
System.out.println("remove");
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();
		System.out.println(selection);
		if (selection != null & selection instanceof IStructuredSelection) {

			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			IViewPart sourcViewPart = window.getActivePage().findView(ProjectsSourceCodeView.ID);
			ProjectsSourceCodeView sview = (ProjectsSourceCodeView)sourcViewPart;
			
			IViewPart testVviewPart = window.getActivePage().findView(ProjectsTestView.ID);
			ProjectsTestView tview = (ProjectsTestView)testVviewPart;
						
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			System.out.println("is IStructuredSelection");
			
			ArrayList<Object> toDelete = new ArrayList<Object>();
			for (Iterator<Object> iterator = strucSelection.iterator(); iterator.hasNext();) {
				toDelete.add(iterator.next());
			}
			sview.remove(toDelete);
			tview.remove(toDelete);
		}
		return null;
	}
}
