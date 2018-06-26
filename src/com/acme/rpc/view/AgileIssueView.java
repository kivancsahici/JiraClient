package com.acme.rpc.view;

import huseyin.SWTUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.acme.rpc.view.NavigationView.TreeObject;
import com.acme.rpc.view.NavigationView.TreeParent;
import com.acme.service.entity.AgileIssue;
import com.acme.service.entity.Assignee;
import com.acme.service.entity.Fields;
import com.acme.service.entity.Status;
import com.acme.service.entity.StatusCategory;

public class AgileIssueView extends ViewPart {
	public AgileIssueView() {
	}

	// private AgileIssue agileIssue;
	// private WritableValue agileIssueValue = new WritableValue();

	public static final String ID = "com.acme.rpc.view.AgileIssueView";
	private Text textName;
	private Text textStatus;
	private Text textTitle;
	private Image image;
	private Canvas canvas;

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/*
	 * public void updateModel(AgileIssue issue) {
	 * this.agileIssueValue.setValue(issue); this.url =
	 * issue.getFields().getAssignee().getAvatarUrls().getX4848(); }
	 */

	// the listener we register with the selection service
	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we ignore our own selections
			if (sourcepart != AgileIssueView.this && selection instanceof IStructuredSelection) {
				List list = ((IStructuredSelection) selection).toList();
				if (list.size() == 1) {
					Object obj = list.get(0);
					if (obj instanceof TreeParent) {
						//do nothing
					} else if (obj instanceof TreeObject) {
						TreeObject to = (TreeObject) obj;
						textStatus.setText(to.getIssue().getFields().getStatus().getStatusCategory().getName());
						textName.setText(to.getIssue().getFields().getAssignee().getName());
						textTitle.setText(to.getIssue().getFields().getSummary());
						String url = to.getIssue().getFields().getAssignee().getAvatarUrls().getX4848();
						URLConnection urlcon = null;
						try {
							Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
							URL url1 = new URL(url);

							urlcon = url1.openConnection(/* proxy */);
							//urlcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36");

							BufferedImage img1 = ImageIO.read(urlcon.getInputStream());

							ImageData imageData = SWTUtils.convertToSWT(img1);

							setImage(ImageDescriptor.createFromImageData(imageData).createImage());
							canvas.redraw();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	};

	/*
	 * public AgileIssueView() { this.agileIssue = new AgileIssue();
	 * this.agileIssue.setKey("ACME-1522"); Fields fields = new Fields();
	 * Assignee assignee = new Assignee(); assignee.setName("roadrunner");
	 * fields.setAssignee(assignee);
	 * 
	 * Status status = new Status(); StatusCategory statusCategory = new
	 * StatusCategory(); statusCategory.setName("Closed");
	 * status.setStatusCategory(statusCategory); fields.setStatus(status);
	 * this.agileIssue.setFields(fields);
	 * this.agileIssueValue.setValue(this.agileIssue); }
	 */

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(null);
		canvas = new Canvas(parent, SWT.NONE);
		canvas.setBounds(27, 59, 84, 84);

		textName = new Text(parent, SWT.BORDER);
		textName.setBounds(236, 65, 236, 18);

		Label lblAssignee = new Label(parent, SWT.NONE);
		lblAssignee.setBounds(159, 65, 55, 15);
		lblAssignee.setText("Assignee");

		Label lblStatus = new Label(parent, SWT.NONE);
		lblStatus.setBounds(159, 97, 55, 15);
		lblStatus.setText("Status");

		textStatus = new Text(parent, SWT.BORDER);
		textStatus.setBounds(236, 97, 236, 18);

		textTitle = new Text(parent, SWT.BORDER);
		textTitle.setBounds(236, 125, 236, 18);

		Label lblTitle = new Label(parent, SWT.NONE);
		lblTitle.setBounds(159, 125, 55, 15);
		lblTitle.setText("Title");

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (getImage() != null) {
					e.gc.drawImage(getImage(), 0, 0);
					image.dispose();
				}
				return;
			}
		});

		createActions();
		initializeToolBar();
		initializeMenu();
		// initDataBindings();
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
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
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	/*
	 * protected DataBindingContext initDataBindings() { DataBindingContext
	 * bindingContext = new DataBindingContext();
	 * 
	 * IObservableValue target =
	 * WidgetProperties.text(SWT.Modify).observe(textStatus); IObservableValue
	 * model = PojoProperties.value("fields", Fields.class).value("status",
	 * Status.class) .value("statusCategory",
	 * StatusCategory.class).value("name",
	 * String.class).observeDetail(agileIssueValue);
	 * 
	 * 
	 * IObservableValue target1 =
	 * WidgetProperties.text(SWT.Modify).observe(textName); IObservableValue
	 * model1 = PojoProperties.value("fields", Fields.class).value("assignee",
	 * Assignee.class).value("name",
	 * String.class).observeDetail(agileIssueValue);
	 * 
	 * IObservableValue target2 =
	 * WidgetProperties.text(SWT.Modify).observe(textTitle); IObservableValue
	 * model2 = PojoProperties.value("fields", Fields.class).value("summary",
	 * String.class).observeDetail(agileIssueValue);
	 * 
	 * bindingContext.bindValue(target, model, null, null);
	 * bindingContext.bindValue(target1, model1, null, null);
	 * bindingContext.bindValue(target2, model2, null, null); return
	 * bindingContext; }
	 */
}
