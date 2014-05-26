package toaster.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CodeView extends ViewPart {
	private Text text;//文本框
	public static final String ID = "toaster.views.CodeView";
	public CodeView() {
	   super();
	}
	public void createPartControl(Composite parent) {
	   text = new Text(parent, SWT.NONE);
	}
	public void setFocus() {
	   text.setFocus();
	}
	//设置文本框的内容
	public void setContent(String content) {
	   text.setText(content);
	}
}
