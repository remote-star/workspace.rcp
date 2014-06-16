package toaster.editors;

import org.eclipse.ui.texteditor.AbstractTextEditor;


public class SimpleEditor extends AbstractTextEditor {

	public static final String ID = "org.eclipse.ui.examples.rcp.texteditor.editors.SimpleEditor";
	public SimpleEditor() {
		super();
		internal_init();
	}

	/**
	 * Initializes the document provider and source viewer configuration.
	 * Called by the constructor. Subclasses may replace this method.
	 */
	protected void internal_init() {
		configureInsertMode(SMART_INSERT, false);
		setDocumentProvider(new SimpleDocumentProvider());
	}
	
}
