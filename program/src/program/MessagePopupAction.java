package program;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import program.editors.MultiPageEditor;


public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;

    MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_OPEN_MESSAGE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(program.Activator.getImageDescriptor("/icons/sample3.gif"));
    }

    private File queryFile() {
		FileDialog dialog= new FileDialog(window.getShell(), SWT.OPEN);
		dialog.setText("Open File"); //$NON-NLS-1$
		String path= dialog.open();
		if (path != null && path.length() > 0)
			return new File(path);
		return null;
	}

	@Override
    public void run() {
		File file= queryFile();
		if (file != null) {
			IEditorInput input= createEditorInput(file);
			String editorId= getEditorId(file);
			IWorkbenchPage page= window.getActivePage();
			try {
				page.openEditor(input, editorId);
			} catch (PartInitException e) {
				e.printStackTrace();
				MessageDialog.openWarning(window.getShell(), "Problem", "File is 'null'");
			}
		}
	}

	private String getEditorId(File file) {
//		IWorkbench workbench= window.getWorkbench();
//		IEditorRegistry editorRegistry= workbench.getEditorRegistry();
//		IEditorDescriptor descriptor= editorRegistry.getDefaultEditor(file.getName());
//		if (descriptor != null)
//			return descriptor.getId();
		return MultiPageEditor.ID;
		//return SimpleEditor.ID; 
	}

	private IEditorInput createEditorInput(File file) {
		IPath location= new Path(file.getAbsolutePath());
		PathEditorInput input= new PathEditorInput(location);
		return input;
	}

}