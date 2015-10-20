package com.gnt.worklogger.uicomponents;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.gnt.worklogger.CustomStringToBigDecimalConverter;
import com.gnt.worklogger.Misc;
import com.gnt.worklogger.WorkloggerUI;
import com.gnt.worklogger.entities.Project;
import com.gnt.worklogger.entities.User;
import com.gnt.worklogger.entities.UsersProject;
import com.gnt.worklogger.entities.WorkCategory;
import com.gnt.worklogger.entities.WorkPerformed;
import com.gnt.worklogger.session.CurrentUser;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.util.converter.StringToFloatConverter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.FloatRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class WorkWindow extends Window implements ValueChangeListener {
	
	private WorkPerformed workPerformed;
	private WorkTable workTable;
	private VerticalLayout layout = new VerticalLayout();
    private NativeSelect projectSelect;
    private NativeSelect wcSelect;
    private TextField comments;
    private TextField hours;
    private DateField dateField;
//    private FieldGroup group;
	private boolean isEditWindow;
	private User user;

	public WorkWindow(WorkPerformed workPerformed, WorkTable workTable, boolean isEditWindow) {
		this.isEditWindow = isEditWindow;
		this.workPerformed = workPerformed;
		this.workTable = workTable;
		this.user = (User) WorkloggerUI.get().getUiEm().find(User.class, Long.valueOf(CurrentUser.get()));
//		BeanItem<WorkPerformed> bean = new BeanItem<WorkPerformed>(workPerformed);
//		group = new FieldGroup(bean);
		setLocale(Locale.US);
	}
	
	@Override
	public void attach() {
		super.attach();		
        setModal(true);
        setResizable(false);
        center();		
		buildFormLayout();
		buildFooter();
        setContent(layout);
        layout.setMargin(true);
        layout.setSpacing(true);	
	}
	
    @SuppressWarnings("unchecked")
	private void buildFormLayout() {
    	if(workPerformed.getWpDate()!=null)
    		dateField = new DateField("Ημερομηνία", new Date(workPerformed.getWpDate().getTime()));
    	else
    		dateField = new DateField("Ημερομηνία", Misc.todayWithZeroTime());
        dateField.setDateFormat("dd/MM/yyyy");
        dateField.setRangeEnd(Misc.todayWithZeroTime());

        List<Project> availableProjects = (List<Project>) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("UsersProject.findProjectsByUser", user);
        BeanItemContainer<Project> projectContainer = new BeanItemContainer<Project>(Project.class, availableProjects);        
        projectContainer.sort(new Object[]{"pjDescr"}, new boolean[]{true});
        projectSelect = new NativeSelect("Project", projectContainer);
        projectSelect.setItemCaptionPropertyId("pjDescr");
        projectSelect.setImmediate(true);
        projectSelect.addValidator(new NullValidator("Πρέπει να επιλέξετε Project", false)); 
        if (workPerformed.getProject() != null)
            projectSelect.setValue((Project) workPerformed.getProject());
        projectSelect.addValueChangeListener(this);

        List<WorkCategory> categories = (List<WorkCategory>) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("WorkCategory.findAll");
        BeanItemContainer<WorkCategory> workCategoryContainer = new BeanItemContainer<WorkCategory>(WorkCategory.class, categories);
        workCategoryContainer.sort(new Object[]{"wcDescr"}, new boolean[]{true});
        wcSelect = new NativeSelect("Τύπος Εργασίας :", workCategoryContainer);
        wcSelect.setItemCaptionPropertyId("wcDescr");
        wcSelect.setImmediate(true);
        wcSelect.addValidator(new NullValidator("Πρέπει να επιλέξετε τύπο εργασίας", false));
        if (workPerformed.getWorkCategory() != null)
            wcSelect.setValue(workPerformed.getWorkCategory());
        wcSelect.addValueChangeListener(this);

        this.setLocale(Locale.US);
        hours = new TextField("Ώρες εργασίας :");
        hours.removeAllValidators();
        hours.setImmediate(true);
        hours.setNullRepresentation("");
        hours.setConverter(new StringToFloatConverter());
        hours.setConvertedValue(workPerformed.getWpMhrs());       
        hours.addValidator(new FloatRangeValidator("Οι αποδεκτές τιμές είναι μεταξύ 0.5 και 12 ωρών.", Float.valueOf(0.5F) ,Float.valueOf(12F) ));
        hours.addValidator(new NullValidator("Πρέπει να εισάγετε κάποια τιμή.", false));
        
        comments = new TextField("Σχόλια :");
        comments.setImmediate(true);
        comments.setStyleName("comments-field");
        //comments.setWidth("100%");
        comments.setWidthUndefined();
        comments.setValue(workPerformed.getWpComments() != null ? workPerformed.getWpComments() : "");
        comments.addValidator(new StringLengthValidator("Τα σχόλια πρέπει να είναι μέχρι 250 χαρακτήρες.", 0, 250, true));
        comments.addValueChangeListener(this);

        FormLayout formLayout = new FormLayout(dateField, projectSelect, wcSelect, hours, comments);
        formLayout.setMargin(true);
        formLayout.setSizeUndefined();
        layout.addComponent(formLayout);
    }
    
//    public class CustomStringToFloatConverter extends StringToFloatConverter{ 	
//    	@Override
//    	public Float convertToModel(String value, Class<? extends Float> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
//    		if (value == null || value.isEmpty())
//                return new Float(0);
//    		else
//                return super.convertToModel(value, targetType, locale);
//    	}    	
//    	@Override
//    	public String convertToPresentation(Float value,Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {   		
//    		if (value == null)
//                return "";
//            else 
//            	return super.convertToPresentation(value, targetType, locale); 
//    	}
//    	@Override
//    	protected NumberFormat getFormat(Locale locale) {
//    		return super.getFormat(locale);
//    	}
//    }
	
	private void buildFooter() {
        Button saveButton = new Button("Αποθήκευση");
       saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                workPerformed.setWpDate(dateField.getValue());
                workPerformed.setProject((Project) projectSelect.getValue());//.setPjId((Integer) projectSelect.getValue());                       
                workPerformed.setWorkCategory((WorkCategory) wcSelect.getValue());//.setWcId((Integer) wcSelect.getValue());
//                workPerformed.setWpMhrs(new BigDecimal(hours.getValue()));
//                workPerformed.setWpMhrs(new Float(hours.getValue()));
                workPerformed.setWpMhrs(Float.valueOf(hours.getValue()));
                workPerformed.setWpComments(comments.getValue());

                if (dateField.isValid() && projectSelect.isValid() && wcSelect.isValid() && hours.isValid() && comments.isValid()) {
//                    workPerformedContainer.addEntity(workPerformed);
                	//TODO ADD TO TABLE & DB
                	try {
                		if(isEditWindow){
                			WorkloggerUI.get().getUiEm().merge(workPerformed, null);
//							workTable.updateTable(dateField.getValue());
                		}else{
							WorkloggerUI.get().getUiEm().persist(workPerformed, null);
//							workTable.updateTable(dateField.getValue());
                		}
                		workTable.updateTable(dateField.getValue());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//.find(User.class, Long.valueOf(userId) );
                    close();
                } else {
                    Notification.show("Δεν έχετε εισάγει σωστά στοιχεία.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        Button cancelButton = new Button("Ακύρωση");
        cancelButton.setStyleName(ValoTheme.BUTTON_QUIET);
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

        HorizontalLayout footer = new HorizontalLayout(saveButton, cancelButton);
        footer.setSpacing(true);
       // footer.setSizeFull();
        footer.setSizeUndefined();
        footer.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
        footer.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
        layout.addComponent(footer);
    }

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty().equals(projectSelect)){
		     try {
               projectSelect.validate();
           } catch (Exception e) {
               projectSelect.setValidationVisible(true);
           }
		}else if(event.getProperty().equals(comments)){
			try {
                comments.validate();
            } catch (Exception e) {
                comments.setValidationVisible(true);
            }        
		}else if(event.getProperty().equals(wcSelect)){
			try {
                wcSelect.validate();
            } catch (Exception e) {
                wcSelect.setValidationVisible(true);
            }
		}else if(event.getProperty().equals(hours)){
			try {
				hours.validate();
            } catch (Exception e) {
            	hours.setValidationVisible(true);
            }
		}
		
	}




}
