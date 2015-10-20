package com.gnt.worklogger.session;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;

/**
 * Class for retrieving and setting the id of the current user of the current
 * session (without using JAAS). All methods of this class require that a
 * {@link com.vaadin.server.VaadinRequest} is bound to the current thread.
 *
 * @see com.vaadin.server.VaadinService#getCurrentRequest()
 */
public final class CurrentUser {

    /**
     * The attribute key used to store the username in the session.
     */
    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class.getCanonicalName();

    private CurrentUser() {
    }

    /**
     * Sets the id of the current user and stores it in the current session.
     * Using a {@code null} userId will remove the userId from the session.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static void set(Integer currentUser) {
        if (currentUser == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser);
        }
    }

    /**
     * Returns the id of the current user stored in the current session, or 0
     * if no user ID is stored.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static Integer get() {
        Integer currentUser = (Integer) getCurrentRequest().getWrappedSession()
                .getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        if (currentUser == null) {
            return 0;
        } else {
            return currentUser;
        }
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException(
                    "No request bound to current thread");
        }
        return request;
    }
}
