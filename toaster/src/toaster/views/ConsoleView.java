package toaster.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ConsoleView extends ViewPart {

	public static final String ID = "toaster.views.ConsoleView";
	private Text text;
	@Override
	public void createPartControl(Composite parent) {
		text = new Text(parent, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
