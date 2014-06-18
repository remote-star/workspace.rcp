package toaster.providers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import toaster.sources.Configuration;
import toaster.sources.ImageShop;
import toaster.sources.Project;
import toaster.tools.FileTools;

public class TreeLabelProvider implements ILabelProvider {

	private List listeners;

	public TreeLabelProvider() { 
		listeners = new ArrayList();
	}

	public Image getImage(Object arg0) { 
		//返回目录或文件的图标 
		if(arg0 instanceof Project){
			return ImageShop.get(ImageShop.PROJECT_ICON);
		} else if(((File) arg0).isDirectory()) {
			if(((File)arg0).getParentFile().equals(Configuration.getTestFolder())){
				return ImageShop.get(ImageShop.TEST_FOLDER_ICON);
			}
			return ImageShop.get(ImageShop.FOLDER_ICON);
		} else if(FileTools.isPic((File)arg0)) {
			return ImageShop.get(ImageShop.PIC_ICON);
		} else {
			return ImageShop.get(ImageShop.FILE_ICON); 
		}
	}

	public String getText(Object arg0) { 
		if( arg0 instanceof Project){
			return ((Project)arg0).getName();
		} else if (((File)arg0).getParentFile().equals(Configuration.getTestFolder())){
			return "测试文件";
		}
		return ((File) arg0).getName();
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		return false; 
	} 

	public void removeListener(ILabelProviderListener arg0) { 
		//删除监听器 
		listeners.remove(arg0); 
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
