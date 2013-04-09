import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.examples.rcp.texteditor.editors.PathEditorInput;
import org.eclipse.ui.examples.rcp.texteditor.editors.SimpleEditor;


public class handler implements IHandler {
	
	private IWorkbenchWindow fWindow;

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	private File queryFile() {
		FileDialog dialog= new FileDialog(fWindow.getShell(), SWT.OPEN);
		dialog.setText("Open File"); //$NON-NLS-1$
		String path= dialog.open();
		if (path != null && path.length() > 0)
			return new File(path);
		return null;
		}
	
	private String getEditorId(File file) {
		return SimpleEditor.ID; //$NON-NLS-1$
	}

	private IEditorInput createEditorInput(File file) {
		IPath location= new Path(file.getAbsolutePath());
		PathEditorInput input= new PathEditorInput(location);
		return input;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		File file= queryFile();
		if (file != null) {
			IEditorInput input= createEditorInput(file);
			String editorId= getEditorId(file);
			IWorkbenchPage page= fWindow.getActivePage();
			try {
				page.openEditor(input, editorId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else {
			MessageDialog.openWarning(fWindow.getShell(), "Problem", "File is 'null'"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
