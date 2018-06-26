package com.acme.rcp.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class LoginDialog extends Dialog {
	private Text text;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Username :");
		
		text = new Text(container, SWT.BORDER);
		fd_lblNewLabel.top = new FormAttachment(text, 3, SWT.TOP);
		fd_lblNewLabel.right = new FormAttachment(text, -30);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 92);
		fd_text.right = new FormAttachment(100, -107);
		text.setLayoutData(fd_text);
		
		Label lblPassword = new Label(container, SWT.NONE);
		fd_lblNewLabel.bottom = new FormAttachment(lblPassword, -20);
		fd_lblNewLabel.left = new FormAttachment(0, 151);
		lblPassword.setText("Password :");
		lblPassword.setAlignment(SWT.CENTER);
		FormData fd_lblPassword = new FormData();
		fd_lblPassword.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_lblPassword.bottom = new FormAttachment(100, -75);
		lblPassword.setLayoutData(fd_lblPassword);
		
		text_1 = new Text(container, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.top = new FormAttachment(text, 14);
		fd_text_1.left = new FormAttachment(text, 0, SWT.LEFT);
		text_1.setLayoutData(fd_text_1);
		
		CLabel lblNewLabel_1 = new CLabel(container, SWT.NONE);
		lblNewLabel_1.setImage(SWTResourceManager.getImage(LoginDialog.class, "/com/acme/rcp/app/logo.png"));
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.bottom = new FormAttachment(text, -14);
		fd_lblNewLabel_1.right = new FormAttachment(100, -140);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("");

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
