package toaster.views;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import toaster.providers.FileTreeContentProvider;
import toaster.providers.FileTreeLabelProvider;


public class ProjectsSourceCodeView extends BasicTreeView {

	// 该视图的id，在plugin.xml文件中定义，通常使用字符串常量的方式
	public static final String ID = "toaster.views.ProjectsSourceCodeView";

	// 为父类中的抽象方法，创建视图中的各种控件
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		tv.setContentProvider(new FileTreeContentProvider()); 
		// 添加标签管理器
		tv.setLabelProvider(new FileTreeLabelProvider()); 
		// 设置treeviewer的输入
		tv.setInput("root"); // pass a non-null that will be ignored
	}

	public boolean input(String path){
		File file = new File(path);
		if(!file.exists()){
			return false;
		}
		Object[] roots = ((FileTreeContentProvider)(tv.getContentProvider())).getElements("root");
		ArrayList<Object> list = new ArrayList<>();
		for(Object o : roots){
			if(o.equals(file)){
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("已有同名项目，不能重复导入");
				mb.setText("不能导入");
				mb.open();
				return false;
			} else {
				list.add(o);
			}
		}
		list.add(file);
		((FileTreeContentProvider)(tv.getContentProvider())).setRoots(list.toArray());
		tv.refresh("root");
		return true;
	}

	public void remove(ArrayList<Object> toDelete) {
		Object[] roots = ((FileTreeContentProvider)(tv.getContentProvider())).getElements("root");
		ArrayList<Object> list = new ArrayList();
		for(Object o : roots){
			if(!toDelete.contains(o)){
				list.add(o);
			} else {
//				tv.
			}
		}
		list.removeAll(toDelete);
		roots = list.toArray();
	}
}


