package com.acme.rcp.action;

import huseyin.Activator;
import huseyin.ICommandIds;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;


public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;
    private final String viewId;
    private int instanceNum = 0;
    /*
    public MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_OPEN_MESSAGE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(huseyin.Activator.getImageDescriptor("/icons/sample3.gif"));
    }
    */
    public MessagePopupAction(IWorkbenchWindow window, String label, String viewId) {
    	this.window = window;
		this.viewId = viewId;
        setText(label);
        // The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN);
        // Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(huseyin.Activator.getImageDescriptor("/icons/sample2.gif"));
    }

    public void run() {
        //MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
        
        if(window != null) {	
			try {
				window.getActivePage().showView(viewId, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
			}
		}
    }
}