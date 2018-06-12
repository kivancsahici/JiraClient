package com.acme.rcp.action;

import huseyin.ICommandIds;
import huseyin.LoginDialog;
import java.util.List;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import com.acme.rpc.view.AgileIssueView;
import com.acme.rpc.view.NavigationView;
import com.acme.service.AgileRestServiceClient;
import com.acme.service.entity.AgileIssue;

public class LoginDialogAction extends Action {
	private final IWorkbenchWindow window;

	public LoginDialogAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ICommandIds.CMD_OPEN_LOGIN);
		setImageDescriptor(huseyin.Activator
				.getImageDescriptor("/icons/cls16.gif"));
	}

	public void run() {
		LoginDialog dialog = new LoginDialog(window.getShell());
		// dialog.open();
		if (dialog.open() != Window.OK) {
			System.out.println("user pressed cancel");
		} else {
			IWorkbenchPage page = window.getActivePage();
			// IViewPart view = page.showView(MainView.ID) //id de la view in
			// plugin.xml
			// page.hideView(page.findView(SitesView.ID));
			NavigationView view = (NavigationView) page
					.findView(NavigationView.ID);
			List<AgileIssue> list = AgileRestServiceClient.callService();
			view.setInput(list);

			IViewReference[] refs = page.getViewReferences();
			for (IViewReference ref : refs) {
				if (ref.getView(false).getClass().equals(AgileIssueView.class)) {
					// Fields fields = new Fields("mehmet");
					// AgileIssue issue = new AgileIssue("pik32", fields);
					AgileIssueView views = (AgileIssueView) ref.getView(false);
					views.updateModel(list.get(0).getFields().getAssignee()
							.getName());

				}
			}
		}
		// .openInformation(window.getShell(), "Open", "Open Message Dialog!");
	}
}
