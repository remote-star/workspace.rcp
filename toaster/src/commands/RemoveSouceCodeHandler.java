package commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import toaster.views.ProjectsSourceCodeView;
import toaster.views.ProjectsTestView;

public class RemoveSouceCodeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {

			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();

			IViewPart sourcViewPart = window.getActivePage().findView(ProjectsSourceCodeView.ID);
			ProjectsSourceCodeView sview = (ProjectsSourceCodeView)sourcViewPart;

			IViewPart testVviewPart = window.getActivePage().findView(ProjectsTestView.ID);
			ProjectsTestView tview = (ProjectsTestView)testVviewPart;

			IStructuredSelection strucSelection = (IStructuredSelection) selection;

			ArrayList<File> toDelete = new ArrayList<File>();
			ArrayList<File> projectsToDelete = new ArrayList<File>();

			for (@SuppressWarnings("unchecked")
			Iterator<File> iterator = strucSelection.iterator(); iterator.hasNext();) {
				toDelete.add(iterator.next());
			}

			boolean deleteProjects = false;
			for(File file : toDelete){
				if (file.exists() && file.isDirectory() && sview.isProject(file)){
					if(!deleteProjects){
						MessageBox mb = new MessageBox(shell, SWT.OK|SWT.CANCEL);
						mb.setMessage("确认删除项目？");
						mb.setText("确认");
						if(mb.open() == SWT.CANCEL){
							return null;
						} else {
							deleteProjects = true;
							projectsToDelete.add(file);
						}
					} else {
						projectsToDelete.add(file);
					}
				}
			}


//			for(File file : toDelete){
//				if (file.exists()){
//					if(file.isDirectory() && sview.isProject(file)){
//						File testFolder = new File("testFiles\\" + file.getName());
//						testFolder.delete();
//					}
//					file.delete();
//				}
//			}

			if(deleteProjects){
				sview.remove(projectsToDelete);
				tview.remove(projectsToDelete);

				try {             
					File file = new File("config\\ProjectsList.txt");     
					FileInputStream fis = new FileInputStream(file);  
					InputStreamReader isr = new InputStreamReader(fis);         
					BufferedReader br = new BufferedReader(isr);           
					StringBuffer buf = new StringBuffer();    
					String temp;
					// 保存该行前面的内容             
					for (int j = 1; (temp = br.readLine()) != null; j++) { 
						if(!projectsToDelete.contains(temp)){
							buf = buf.append(temp);              
							buf = buf.append(System.getProperty("line.separator")); 
						}
					}                     
					br.close();             
					FileOutputStream fos = new FileOutputStream(file);    
					PrintWriter pw = new PrintWriter(fos);        
					pw.write(buf.toString().toCharArray());          
					pw.flush();   
					pw.close();    
				} catch (IOException e) {    
					e.printStackTrace();       
				}
			}
			
			sview.refresh();
			tview.refresh();
		}
		return null;
	}
}
