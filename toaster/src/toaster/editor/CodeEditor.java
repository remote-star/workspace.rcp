package toaster.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class CodeEditor extends EditorPart {

	public static final String ID = "toaster.editor.CodeEditor";
	Text text;
	private String path;
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		text = new Text(parent,  SWT.BORDER |SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		this.text.setFocus();
	}

	public void readFile(File file) {
	       BufferedReader reader = null;
	       String string = "";
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                string += (tempString + "\n\r");
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        text.setText(string);
	}
	
	public void setMyTitle(String title, String path){
		this.setPartName(title);
		this.setMyTitleToolTip(path);
		firePropertyChange(PROP_TITLE);
	}
	

	private void setMyTitleToolTip(String path) {
		// TODO Auto-generated method stub
		this.path = path;
	}

	@Override
	public String getTitleToolTip(){
		return path;
	}
}
