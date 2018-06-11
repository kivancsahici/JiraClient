package com.acme.rcp.action;

import huseyin.ICommandIds;
import huseyin.LoginDialog;
import huseyin.NavigationView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.acme.service.AgileRestServiceClient;

public class LoginDialogAction extends Action {
	private final IWorkbenchWindow window;
  public LoginDialogAction(IWorkbenchWindow window) {
	  this.window = window;
	  setId(ICommandIds.CMD_OPEN_LOGIN);
	  setImageDescriptor(huseyin.Activator.getImageDescriptor("/icons/cls16.gif"));
  }
  
  public void run() {
	  LoginDialog dialog =
			    new LoginDialog(window.getShell());
	  //dialog.open();
	  if (dialog.open() != Window.OK) {
	        System.out.println("user pressed cancel");
	  }
	  else {
		  IWorkbenchPage page = window.getActivePage();
		  //IViewPart view = page.showView(MainView.ID)  //id de la view in plugin.xml
		  //page.hideView(page.findView(SitesView.ID));
		  NavigationView view = (NavigationView)page.findView(NavigationView.ID);
		  view.setInput(AgileRestServiceClient.callService());
		 
	  }
      //.openInformation(window.getShell(), "Open", "Open Message Dialog!");
  }
}
