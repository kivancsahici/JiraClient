package com.acme.rpc.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import com.acme.service.entity.AgileIssue;
import com.acme.service.entity.Assignee;
import com.acme.service.entity.Fields;
import com.acme.service.entity.Status;
import com.acme.service.entity.StatusCategory;


public class AgileIssueView extends ViewPart {
	public static final String ID = "com.acme.rpc.view.AgileIssueView"; //$NON-NLS-1$
	private Text textName;
	
	private AgileIssue agileIssue;
	private Text textStatus;
	
	private WritableValue agileIssueValue = new WritableValue();
	
	public void updateModel(AgileIssue issue) {		
		this.agileIssueValue.setValue(issue);
	}
	
	public AgileIssueView() {				
		this.agileIssue = new AgileIssue();
		this.agileIssue.setKey("ACME-1522");
		Fields fields = new Fields();
		Assignee assignee = new Assignee();
		assignee.setName("roadrunner");
		fields.setAssignee(assignee);
		
		Status status = new Status();
		StatusCategory statusCategory = new StatusCategory();
		statusCategory.setName("Closed");
		status.setStatusCategory(statusCategory);
		fields.setStatus(status);
		this.agileIssue.setFields(fields);
		this.agileIssueValue.setValue(this.agileIssue);
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(null);
		
		Canvas canvas = new Canvas(parent, SWT.NONE);
		canvas.setBounds(55, 30, 208, 208);
		{
			textName = new Text(parent, SWT.BORDER);
			textName.setBounds(368, 59, 147, 18);
		}
		
		Label lblAssignee = new Label(parent, SWT.NONE);
		lblAssignee.setBounds(297, 62, 55, 15);
		lblAssignee.setText("Assignee");
		
		Label lblStatus = new Label(parent, SWT.NONE);
		lblStatus.setBounds(297, 100, 55, 15);
		lblStatus.setText("Status");
		
		textStatus = new Text(parent, SWT.BORDER);
		textStatus.setBounds(368, 97, 147, 18);
			
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image image = huseyin.Activator.getImageDescriptor("/icons/useravatar.png").createImage();					
				e.gc.drawImage(image, 40, 40);
				image.dispose();
			}
		});

		createActions();
		initializeToolBar();
		initializeMenu();
		initDataBindings();
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
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();				
		IObservableValue target1 = WidgetProperties.text(SWT.Modify).observe(textName);
		IObservableValue model1 = PojoProperties.value("fields", Fields.class).value("assignee", Assignee.class).value("name", String.class).observeDetail(agileIssueValue);
				
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(textStatus);
		IObservableValue model = PojoProperties.value("fields", Fields.class).value("status", Status.class)
				.value("statusCategory", StatusCategory.class).value("name", String.class).observeDetail(agileIssueValue);

		bindingContext.bindValue(target, model, null, null);
		bindingContext.bindValue(target1, model1, null, null);
		return bindingContext;
	}
}
