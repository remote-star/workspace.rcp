package toaster.views;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import toaster.editor.CodeEditor;
import toaster.editor.CodeEditorInput;


public class ProjectsView extends ViewPart {

	// 该视图的id，在plugin.xml文件中定义，通常使用字符串常量的方式
	public static final String ID = "toaster.views.ProjectsView";
	private TableViewer viewer; // 视图中显示的表格对象

	// 操作对象
	private Action action1;
	private Action action2;
	public ProjectsView() {
	}
	// 为父类中的抽象方法，创建视图中的各种控件
	public void createPartControl(Composite parent) {
		// 创建一个表格
		viewer = new TableViewer(parent, SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		hookDoubleClickAction(); // 添加表格双击事件
		makeActions(); // 创建操作对象
		hookContextMenu(); // 添加上下文菜单
		contributeToActionBars(); // 添加视图工具栏操作
	}

	private void hookContextMenu() {
		//创建菜单管理器对象
		MenuManager menuMgr = new MenuManager("#PopupMenu"); 
		menuMgr.add(action1);
		menuMgr.add(action2);
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		//为列表对象创建上下文菜单
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		//设置菜单
		viewer.getControl().setMenu(menu);
		//注册上下文菜单
		getSite().registerContextMenu(menuMgr, viewer);
		getSite().setSelectionProvider(viewer); 
	}
	private void contributeToActionBars() {
		//获得视图的操作栏对象
		IActionBars bars = getViewSite().getActionBars();
		//添加下拉菜单
		fillLocalPullDown(bars.getMenuManager());
		//添加工具栏
		fillLocalToolBar(bars.getToolBarManager());
	}
	//添加下拉菜单
	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	//添加工具栏
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	private void hookDoubleClickAction() {
		//注册双击事件监听器
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			//当双击时
			public void doubleClick(DoubleClickEvent event) {
				//获得当前选中的一项
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();

				IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 

				CodeEditorInput editor = new CodeEditorInput();
				// 打开该编辑器
				try {
					window.getActivePage().openEditor(editor, CodeEditor.ID);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
				viewer.getControl().getShell(),
				"样本视图",
				message);
	}
	// 父类中的抽象方法，该视图获得焦点时，该焦点设置为表格
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	// 表格的内容提供器
	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			return new String[] {"One", "Two", "Three"};
		}
		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return getImage(element);
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			return getText(element);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	class NameSorter extends ViewerSorter {

	}
}
