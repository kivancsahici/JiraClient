package com.acme.rcp.action;

import huseyin.ICommandIds;
import huseyin.LoginDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;

public class LoginDialogAction extends Action {
	private final IWorkbenchWindow window;
  public LoginDialogAction(IWorkbenchWindow window) {
	  this.window = window;
	  setId(ICommandIds.CMD_OPEN_LOGIN);
	  setImageDescriptor(huseyin.Activator.getImageDescriptor("/icons/sample3.gif"));
  }
  
  public void run() {
	  LoginDialog dialog =
			    new LoginDialog(window.getShell());
	  //dialog.open();
	  if (dialog.open() != Window.OK) {
	        System.out.println("user pressed cancel");
	  }
      //.openInformation(window.getShell(), "Open", "Open Message Dialog!");
  }
}
