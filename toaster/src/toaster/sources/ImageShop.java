package toaster.sources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class ImageShop {
	  private static ImageRegistry register = new ImageRegistry();
	  
      public static String PROJECT_ICON = "project_obj";
      
      public static String FOLDER_ICON = "fldr_obj";
      
      public static String FILE_ICON = "file_obj";
      
      public static String PIC_ICON = "image_obj";
      
      static {
             try {
				initial();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
      
      public static Image get(String key) {
             Image image = register.get(key);
             if (image == null) {
                    image = ImageDescriptor.getMissingImageDescriptor().createImage();
             }
             return image;
      }
      
      private static void initial() throws MalformedURLException {
             File file = new File("icons");
             File[] images = file.listFiles();
             for (int i = 0; i < images.length; i++) {
                    File f = images[i];
                    if (!f.isFile()) {
                           continue;
                    }
                    String name = f.getName();
                    if (!name.endsWith(".gif")) {
                    	continue;
                    }
                    String key = name.substring(0, name.indexOf('.'));
                    URL fullPathString = new URL("file:icons/" + name);
                    ImageDescriptor des = ImageDescriptor.createFromURL(fullPathString);
                    register.put(key, des);
             }
      }
}
