package commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class CreateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*
		 * 通过display来创建shell Shell(Display display)
		 */
		// 新建一个Display对象
//		Display display = new Display();
		// 根据已创建的Display对象新建一个Shell对象
		// Shell的实例表示当前受Windows所管理的窗口
		Shell shell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		// 设置shell的大小（宽度和高度）
		shell.setSize(500, 500);
		// 设置shell的窗口名
		shell.setText("shell demo");
		// 打开shell窗口
		shell.open();
		return null;
	}
}
