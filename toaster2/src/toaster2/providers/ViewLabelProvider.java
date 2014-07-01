package toaster2.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import toaster2.constant.ImageShop;
import toaster2.resources.ThreadRecord;

public class ViewLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		if (obj instanceof ThreadRecord){
			return ImageShop.get(ImageShop.THREAD);
		} else {
			return ImageShop.get(ImageShop.FUNCTION);
		}
	}
}
