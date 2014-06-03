package toaster.sources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class ImageShop {
	  private static ImageRegistry register = new ImageRegistry();
	  
//      private static Set keys = new HashSet();
      
      public static String PROJECT_IMAGE = "project_obj";
      
      public static String FOLDER_IMAGE = "fldr_obj";
      
      public static String FILE_IMAGE = "file_obj";
      
      static {
             try {
				initial();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
      
//      public static ImageDescriptor getDescriptor(String key) {
//             ImageDescriptor image = register.getDescriptor(key);
//             if (image == null) {
//                    image = ImageDescriptor.getMissingImageDescriptor();
//             }
//             return image;
//      }
      
      public static Image get(String key) {
             Image image = register.get(key);
             if (image == null) {
                    image = ImageDescriptor.getMissingImageDescriptor().createImage();
             }
             return image;
      }
      
//      public static String[] getImageKey() {
//             return (String[]) keys.toArray(new String[keys.size()]);
//      }
      
      private static void initial() throws MalformedURLException {
//             Bundle bundle = Platform.getBundle(PwdgatePlugin.ID);
//             URL url = bundle.getEntry("icons");
//             try {
//                    url = Platform.asLocalURL(url);
//             } catch (Exception e) {
//                    PwdgatePlugin.log("get root path", e);
//             }
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
//                    keys.add(key);
             }
      }
}
