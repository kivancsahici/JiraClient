package com.acme.rpc.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;

public class AgileIssueView extends ViewPart {

	public static final String ID = "com.acme.rpc.view.AgileIssueView"; //$NON-NLS-1$

	public AgileIssueView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{

			Canvas canvas = new Canvas(container, SWT.NONE);
			canvas.setBounds(43, 39, 100, 100);

			canvas.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					Image image = huseyin.Activator.getImageDescriptor(
							"/icons/useravatar.png").createImage();
					// Image image = new Image(myparent.getDisplay(),
					// "/icons/useravatar.png");

					e.gc.drawImage(image, 40, 40);

					image.dispose();
				}
			});
		}
		final Composite myparent = parent;

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
