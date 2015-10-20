package com.gnt.worklogger.uicomponents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickListener;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedOnTableFooterEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedOnTableHeaderEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedOnTableRowEvent;

import com.gnt.worklogger.Misc;
import com.gnt.worklogger.WorkloggerUI;
import com.gnt.worklogger.entities.User;
import com.gnt.worklogger.entities.WorkPerformed;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({"serial","unchecked"})
public class WorkTable extends Table implements Handler{
	
	private ContextMenu contextMenu;
    private User user;
    private BeanItemContainer<WorkPerformed> container;
    private List<WorkPerformed> dataList;
	protected static final Action REMOVE_ACTION = new Action("Διαγραφή");
	protected static final Action EDIT_ACTION = new Action("Επεξεργασία");
	private Date date;
	private int screenresolution;

	public WorkTable(User user,int ScRes) {
		this.user = user;	
		screenresolution=ScRes;
//		this.date = date;
		initializeTable();
//		initializeContextMenu();
		addActionHandler(this);
	}
	
	@Override
	public Action[] getActions(Object target, Object sender) {
		List<Action> actions = new ArrayList<Action>();		
		if(target!=null){
			actions.add(EDIT_ACTION);
			actions.add(REMOVE_ACTION);
		}
		return actions.toArray(new Action[actions.size()]);
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == EDIT_ACTION) {
			WorkPerformed wp = (WorkPerformed) target;
			showEditWorkWindow(wp);
		} else if (action == REMOVE_ACTION) {
			WorkPerformed wp = (WorkPerformed) target;
			deleteWork(wp);
		}	
	}
		
	private void deleteWork( WorkPerformed workPerformed){
		ConfirmDialog.show(UI.getCurrent(), "Διαγραφή", "Είστε σίγουρος ότι θέλετε να διαγράψετε τη συγκεκριμένη εγγραφή;", "Ναι", "Όχι", new ConfirmDialog.Listener() {
            @Override
            public void onClose(ConfirmDialog confirmDialog) {
                if (confirmDialog.isConfirmed()) {                	
                	try {
						WorkloggerUI.get().getUiEm().delete(workPerformed, "");
						updateTable(date);
						Notification.show("Η εγγραφή διαγράφηκε επιυχώς!", Notification.Type.TRAY_NOTIFICATION);
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
						Notification.show("Υπήρξε πρόβλημα στη διαγραφή.", Notification.Type.TRAY_NOTIFICATION);
					}
                }
            }
        });
	}
	
	private void showEditWorkWindow(WorkPerformed wp){
    	final WorkWindow window = new WorkWindow(wp, WorkTable.this, true);//edit work window
    	UI.getCurrent().addWindow(window);
	}
	
	
	public void updateTable(Date date){
		this.date = date;
		if(date!=null)
			this.dataList = (List<WorkPerformed>) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("WorkPerformed.findWorkPerformedByUserAndDate", date, user);
		else
			this.dataList = (List<WorkPerformed>) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("WorkPerformed.findWorkPerformedByUser", user);
		container.removeAllItems();
		container.addAll(dataList);
		sort(new Object[]{"project"}, new boolean[]{true});
		refreshRowCache();
		this.setValue(null);
	}
	
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
		if(!(property.getValue() instanceof Date)){
			return super.formatPropertyValue(rowId, colId, property);
		}else{
            Date date = (Date) property.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            return sdf.format(date);
        }		
	}
	
	
	private void initializeTable() {
		this.dataList = (List<WorkPerformed>) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("WorkPerformed.findWorkPerformedByUserAndDate", Misc.todayWithZeroTime(), user);
		container = new BeanItemContainer<WorkPerformed>(WorkPerformed.class, dataList);
		this.setContainerDataSource(container);
		setSelectable(true);
        //setSizeUndefined();
		setWidth(screenresolution*95/100,Sizeable.UNITS_PIXELS);
		setImmediate(true);
       setStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        setVisibleColumns("wpDate", "project", "workCategory", "wpMhrs", "wpComments");
        setColumnHeaders("Ημ/νία", "Project", "Τύπος Εργασίας", "Ώρες", "Σχόλια");        
        setColumnWidth("wpDate", 100);
        setColumnWidth("wpMhrs", 50);        
        setColumnExpandRatio("project", 0.3f);
        setColumnExpandRatio("workCategory", 0.2f);
        setColumnExpandRatio("wpComments", 0.7f);
        setColumnAlignment( "wpMhrs", Table.ALIGN_CENTER);       
        sort(new Object[]{"project"}, new boolean[]{true});
	}


}
