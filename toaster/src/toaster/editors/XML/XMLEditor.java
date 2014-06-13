package toaster.editors.XML;

import toaster.editors.SimpleEditor;



public class XMLEditor extends SimpleEditor {

	private ColorManager colorManager;

	protected void internal_init() {
		configureInsertMode(SMART_INSERT, false);
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
