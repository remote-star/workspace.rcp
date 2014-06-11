package commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import toaster.sources.Project;
import toaster.sources.Projects;

public class RemoveProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;

			ArrayList<Project> toRemove = new ArrayList<Project>();
			MessageBox mb = new MessageBox(shell, SWT.OK|SWT.CANCEL);
			mb.setMessage("确认移除项目？项目下的测试文件也将被删除。");
			mb.setText("确认");
			if(mb.open() == SWT.CANCEL){
				return null;
			} 
			
			for (@SuppressWarnings("unchecked")Iterator<Project> iterator = strucSelection.iterator(); iterator.hasNext();) {
				toRemove.add(iterator.next());
			}

			Projects.getInstance().removeProject(toRemove);
		}
		return null;
	}
}
