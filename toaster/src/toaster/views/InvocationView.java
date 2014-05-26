package toaster.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class InvocationView extends ViewPart {

	public static final String ID = "toaster.views.InvocationView";
	

	private Text text;//文本框
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		   text = new Text(parent, SWT.NONE);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

		   text.setFocus();
	}

}
