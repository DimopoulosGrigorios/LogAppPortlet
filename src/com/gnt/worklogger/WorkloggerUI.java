package com.gnt.worklogger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.annotation.WebServlet;

import com.gnt.worklogger.access.AccessControl;
import com.gnt.worklogger.access.LogAppAccessControl;
import com.gnt.worklogger.backend.UiEntityManager;
import com.gnt.worklogger.session.CurrentUser;
import com.gnt.worklogger.uicomponents.LoginScreen;
import com.gnt.worklogger.uicomponents.MainScreen;
import com.gnt.worklogger.uicomponents.LoginScreen.LoginListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinPortletService;
import com.vaadin.server.VaadinPortletSession.PortletListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings({ "serial", "deprecation" })
@Theme("worklogger")
public class WorkloggerUI extends UI   
{

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = WorkloggerUI.class)
	public static class Servlet extends VaadinServlet
	{
	}
	/*@Inject*/ private UiEntityManager uiEntityManager=new UiEntityManager();	
	private AccessControl accessControl = new LogAppAccessControl();
	private  User currentUser = null;


	@Override
	protected void init(VaadinRequest request)
	{


		PortletRequest req = VaadinPortletService.getCurrentPortletRequest();
		try 
		{
			currentUser = PortalUtil.getUser(req);
		}
		catch (PortalException | SystemException e) 
		{
			e.printStackTrace();
		}
//		setLocale(Locale.US);
    	if (WorkloggerUI.get().getUiEm()==null)
    	{
    	}
		setLocale(request.getLocale());
		//setSizeFull();
		if (currentUser==null)
		{
			setContent(new Label("Δεν έχεις άδεια να δείς το portlet."));
		}
		else
		{			
			accessControl.signIn(currentUser.getUserId());
		           showMainView(CurrentUser.get());
		}
		
	}
	
    protected void showMainView(Integer currentUser) 
    {
        setContent(new MainScreen(currentUser,getUI().getPage().getWebBrowser().getScreenWidth()));
    	//Notification.show("Logged in '" + currentUser.toString() + "'");
    }
	
   public UiEntityManager getUiEntityManager() 
   {
	   return uiEntityManager;
	}

    public static WorkloggerUI get() 
    {
        return (WorkloggerUI) UI.getCurrent();
    }
    
    public UiEntityManager getUiEm()
    {
    	return  (UiEntityManager) get().getUiEntityManager();
    }
    
    public AccessControl getAccessControl() 
    {
        return accessControl;
    }

	@Override
	protected void refresh(VaadinRequest request) 
	{
		super.refresh(request);
		init(request);
	}
	
}