package com.gnt.worklogger.access;

import javax.persistence.Query;

import com.gnt.worklogger.WorkloggerUI;
import com.gnt.worklogger.entities.User;
import com.gnt.worklogger.session.CurrentUser;


/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class LogAppAccessControl implements AccessControl {

    @Override
    public boolean signIn(Long id) {
        boolean successfulLogin = true;
        if (id==null) {
            successfulLogin = false;
        } else {
            try {                
                User user = (User) WorkloggerUI.get().getUiEm().executeNamedQuerySelect("User.findUserToLogin",id).get(0);                 
                CurrentUser.set((int) user.getUsrId());
            } catch (Exception e) {
                successfulLogin = false;
            }
        }
        return successfulLogin;
    }

    @Override
    public boolean isUserSignedIn() {
        return !(CurrentUser.get() == 0);
    }

    @Override
    public boolean isUserInRole(String role) {
        // TODO : implement this
        return true;
    }
}
