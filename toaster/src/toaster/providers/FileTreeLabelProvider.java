package toaster.providers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import toaster.sources.ImageShop;

public class FileTreeLabelProvider implements ILabelProvider {

	private List listeners;

	public FileTreeLabelProvider() { 
		listeners = new ArrayList();
	}

	public Image getImage(Object arg0) { 
		//返回目录或文件的图标 
		return ((File) arg0).isDirectory() ? ImageShop.get(ImageShop.FOLDER_IMAGE) : ImageShop.get(ImageShop.FILE_IMAGE); 
	}

	public String getText(Object arg0) { 
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
