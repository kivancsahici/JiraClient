package huseyin;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.acme.rpc.view.AgileIssueView;
import com.acme.rpc.view.NavigationView;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "huseyin.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 0.25f, editorArea);
		IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.75f, editorArea);
		/*
		folder.addPlaceholder(View.ID + ":*");
		folder.addView(View.ID);
		*/
		
		//folder.addPlaceholder(TableView.ID + ":*");
		//folder.addView(TableView.ID);
		
		folder.addPlaceholder(AgileIssueView.ID + ":*");
		folder.addView(AgileIssueView.ID);
		
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}
}
