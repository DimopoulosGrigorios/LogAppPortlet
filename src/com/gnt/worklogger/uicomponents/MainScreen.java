package com.gnt.worklogger.uicomponents;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gnt.worklogger.Misc;
import com.gnt.worklogger.WorkloggerUI;
import com.gnt.worklogger.entities.User;
import com.gnt.worklogger.entities.WorkPerformed;
import com.gnt.worklogger.session.CurrentDate;
import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class MainScreen extends VerticalLayout 
{

    private User user;
    private Label userLabel = new Label();
    private DateField dateField = new DateField();
    private Button logoutButton = new Button("Logout");
    private Button addNewButton = new Button("Εισαγωγή");
    private WorkTable table;
    private int screenresolution;

    public MainScreen(Integer userId,int ScRes) {

    	screenresolution=ScRes;
        user = (User) WorkloggerUI.get().getUiEm().find(User.class, Long.valueOf(userId));
        table = new WorkTable(user,screenresolution);
        
    }
    
    @Override
    public void attach()
    {
    	super.attach();
        setStyleName("crud-view");
        //setSizeFull();
        //setSizeUndefined();
        //setWidth(screenresolution*78/100,Sizeable.UNITS_PIXELS);
       // setHeight(700,Sizeable.UNITS_PIXELS);
        VerticalLayout viewContainer = new VerticalLayout();
       // viewContainer.setSizeFull();
        viewContainer.setSizeUndefined();
        viewContainer.setMargin(true);
        HorizontalLayout topBar = buildTopBar();
        viewContainer.addComponent(topBar);
        viewContainer.addComponent(table);
        viewContainer.setExpandRatio(table, 1);
        addComponent(viewContainer);      
        table.updateTable(dateField.getValue());
    }
    
    @SuppressWarnings("serial")
	private HorizontalLayout buildTopBar() {

        HorizontalLayout topBar = new HorizontalLayout();
        //topBar.setWidth(this.getWidth(),Sizeable.UNITS_PIXELS);
        topBar.setSizeFull();
        topBar.setMargin(true);
        topBar.setStyleName("top-bar");

        userLabel.setValue("Εργαζόμενος : " + this.user.getUsrName());
        userLabel.setStyleName(ValoTheme.LABEL_LIGHT);
        topBar.addComponent(userLabel);

        dateField.setValue(CurrentDate.get());
        dateField.setDateFormat("dd/MM/yyyy");
        dateField.setRangeStart(user.getUsrHireDate());
        dateField.setRangeEnd(Misc.todayWithZeroTime());
        dateField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                CurrentDate.set(dateField.getValue());
                table.updateTable(dateField.getValue());
            }
        });
        topBar.addComponent(dateField);

        addNewButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addNewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final WorkPerformed wp = new WorkPerformed();
//                wp.setUsrId(user.getUsrId());
                wp.setUser(user);
                wp.setWpDate(dateField.getValue());
//                final WorkPerformedWindow window = new WorkPerformedWindow(wp, table.getContainerDataSource());
                final WorkWindow window = new WorkWindow(wp, table, false);//create new work window
                UI.getCurrent().addWindow(window);
            }
        });
        topBar.addComponent(addNewButton);

        Label spaceHolder = new Label("");
        topBar.addComponent(spaceHolder);

        logoutButton.setVisible(false);
        logoutButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });
        topBar.addComponent(logoutButton);

        topBar.setExpandRatio(userLabel, 0.2f);
        topBar.setExpandRatio(dateField, 0.2f);
        topBar.setExpandRatio(addNewButton, 0.2f);
        topBar.setExpandRatio(spaceHolder, 0.2f);
        topBar.setExpandRatio(logoutButton, 0.2f);
        topBar.setComponentAlignment(userLabel, Alignment.MIDDLE_CENTER);
        topBar.setComponentAlignment(dateField, Alignment.MIDDLE_CENTER);
        topBar.setComponentAlignment(addNewButton, Alignment.MIDDLE_CENTER);
        topBar.setComponentAlignment(spaceHolder, Alignment.MIDDLE_CENTER);
        topBar.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
        return topBar;
    }
    


}
