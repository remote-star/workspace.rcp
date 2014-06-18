package toaster.editors.XML;

import toaster.editors.ColorManager;
import toaster.editors.SimpleEditor;



public class XMLEditor extends SimpleEditor {

	private ColorManager colorManager;

	protected void internal_init() {
		configureInsertMode(SMART_INSERT, false);
		colorManager = ColorManager.getInstance();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
