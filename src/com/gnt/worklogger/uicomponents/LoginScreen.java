package com.gnt.worklogger.uicomponents;

import com.gnt.worklogger.access.AccessControl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 */
@SuppressWarnings("serial")
public class LoginScreen extends CssLayout 
{

    private TextField username;
    private PasswordField password;
    private Button login;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener)
    {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        this.setHeight(700,Sizeable.UNITS_PIXELS);
        //this.setWidth(75,Sizeable.UNITS_PERCENTAGE);
        setWidthUndefined();
       // this.setWidth(980,Sizeable.UNITS_PIXELS);
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);

        addComponent(centeringLayout);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(true);

        loginForm.addComponent(username = new TextField("Username"));
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                 //   login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        return loginForm;
    }
    
    private void showNotification(Notification notification) 
    {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable 
    {
        void loginSuccessful();
    }
    
    /* private void login() 
     * {
         if (accessControl.signIn(username.getValue(), password.getValue())) {
             loginListener.loginSuccessful();
         } else {
             showNotification(new Notification("Η είσοδος απέτυχε!",
                     "Παρακαλώ ελέγξτε το username και password και ξαναπροσπαθήστε.",
                     Notification.Type.ERROR_MESSAGE));
             username.focus();
         }
     }*/
}
