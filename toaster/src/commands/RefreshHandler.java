package commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import toaster.views.BasicTreeView;

public class RefreshHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		IViewPart sourcViewPart = (IViewPart) window.getActivePage().getActivePart();
		BasicTreeView sview = (BasicTreeView)sourcViewPart;
		
		sview.refresh();
		return null;
	}

}
