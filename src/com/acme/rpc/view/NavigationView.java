package com.acme.rpc.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import com.acme.service.entity.AgileIssue;

public class NavigationView extends ViewPart {
	public static final String ID = "jiraClient.navigationView";
	private int instanceNum = 0;
	private TreeViewer viewer;

	public void setInput(List<AgileIssue> issues) {
		Collections.sort(issues);
		List<AgileIssue> todo = new ArrayList<>();
		List<AgileIssue> inprog = new ArrayList<>();
		List<AgileIssue> completed = new ArrayList<>();
		for (AgileIssue issue : issues) {
			if(issue.getFields().getStatus().getStatusCategory().getId() == 2) {
				todo.add(issue);	
			}
			else if(issue.getFields().getStatus().getStatusCategory().getId() == 4) {
				inprog.add(issue);	
			}
			else {
				completed.add(issue);	
			}
		}
		TreeParent root = new TreeParent("");
		
		if(todo.size() > 0) {
			TreeParent parent = new TreeParent("TO-DO");
			for(AgileIssue issue : todo) {
				//parent.addChild(new TreeObject(issue.getFields().getSummary()));
				parent.addChild(new TreeObject(issue));
			}
			root.addChild(parent);
		}
		
		if(inprog.size() > 0) {
			TreeParent parent = new TreeParent("IN-PROGRESS");
			for(AgileIssue issue : inprog) {
				//parent.addChild(new TreeObject(issue.getFields().getSummary()));
				parent.addChild(new TreeObject(issue));
			}
			root.addChild(parent);
		}
		
		if(completed.size() > 0) {
			TreeParent parent = new TreeParent("COMPLETED");
			for(AgileIssue issue : completed) {
				//parent.addChild(new TreeObject(issue.getFields().getSummary()));
				parent.addChild(new TreeObject(issue));
			}
			root.addChild(parent);
		}
			
		viewer.setInput(root);

		
		//viewer.setInput(createDummyModel());

	}

	class TreeObject {
		public AgileIssue getIssue() {
			return issue;
		}

		public void setIssue(AgileIssue issue) {
			this.issue = issue;
		}
		
		private TreeParent parent;
		private AgileIssue issue;
		private String name;

		public TreeObject(AgileIssue issue) {
			this.issue = issue;
		}
		
		public TreeObject(String name) {
			this.name = name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		public String toString() {
			return issue.getKey();
		}
	}

	class TreeParent extends TreeObject 
	{
		private ArrayList children;
		private String name;
		public TreeParent(String name) {
			super(name);
			this.name = name;
			children = new ArrayList();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
		public String toString() {
			return this.name;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

	/**
     * We will set up a dummy model to initialize tree heararchy. In real
     * code, you will connect to a real model and expose its hierarchy.
	 */
	private TreeObject createDummyModel() {
		TreeObject to1 = new TreeObject("Inbox");
		TreeObject to2 = new TreeObject("Drafts");
		TreeObject to3 = new TreeObject("Sent");
		TreeParent p1 = new TreeParent("me@this.com");
		p1.addChild(to1);
		p1.addChild(to2);
		p1.addChild(to3);

		TreeObject to4 = new TreeObject("Inbox");
		TreeParent p2 = new TreeParent("other@aol.com");
		p2.addChild(to4);

		TreeParent root = new TreeParent("");
		root.addChild(p1);
		root.addChild(p2);
		return root;
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		final ViewContentProvider contentProvider = new ViewContentProvider(); 
		viewer.setContentProvider(contentProvider);
		final ViewLabelProvider labelProvider = new ViewLabelProvider();
		viewer.setLabelProvider(labelProvider);
		getSite().setSelectionProvider(viewer);
		// viewer.setInput(createDummyModel());
/*
		viewer.addDoubleClickListener(new IDoubleClickListener() {
		    @Override
		    public void doubleClick(DoubleClickEvent event) {
		    	
		    	//try {
				//	AgileIssueView view = (AgileIssueView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().
				//			getActivePage().showView(AgileIssueView.ID, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
					
				//	views.updateModel(list.get(0));
					
				//} catch (PartInitException e) {					
				//	e.printStackTrace();
				//}
		    	
		    	

		    	IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
		        TreeViewer viewer = (TreeViewer) event.getViewer();
		        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		        //Object selectedNode = thisSelection.getFirstElement();
		        //viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
		        StringBuffer toShow = new StringBuffer();
				for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
					Object domain = (TreeObject) iterator.next();
					String value = labelProvider.getText(domain);
					if(!(domain instanceof TreeParent)) {
						TreeObject obj = (TreeObject) domain;
						AgileIssue issue = obj.getIssue();
						System.out.println(issue.getKey());
						
						AgileIssueView view;
						try {
							view = (AgileIssueView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().
									getActivePage().showView(AgileIssueView.ID, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
							view.updateModel(issue);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
					
					//Object object = contentProvider.getParent(domain);
					//Object[] objects = contentProvider..getElements(object);
					
					toShow.append(value);
					toShow.append(", ");
				}
				// remove the trailing comma space pair
				if (toShow.length() > 0) {
					toShow.setLength(toShow.length() - 2);
				}
				// text.setText(toShow.toString());
				manager.setMessage(toShow.toString());
		    }
		});
		*/
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}