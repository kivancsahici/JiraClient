package com.acme.rpc.view;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import com.acme.service.entity.Address;


public class AgileIssueView extends ViewPart {
	private Binding agileIssues;
	private DataBindingContext m_bindingContext;

	public static final String ID = "com.acme.rpc.view.AgileIssueView"; //$NON-NLS-1$
	private Text textName;
	private Address address;

	public Address getAddress() {
		return this.address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	public void updateModel(String name) {
		this.address.setName(name);
		//text.setText("suleyman");
	}
	public AgileIssueView() {
		address = new Address();
		address.setName("kazim");
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
			textName.setBounds(279, 28, 147, 42);
		}
			
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
		m_bindingContext = initDataBindings();
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
		//
		IObservableValue textNameValue = WidgetProperties.text(SWT.Modify).observe(textName);
		IObservableValue addressNameValue = BeanProperties.value("name").observe(address);
		bindingContext.bindValue(textNameValue, addressNameValue);//, null, null);
		//
		
		
		return bindingContext;
	}
}
