package com.gnt.worklogger.session;

import com.gnt.worklogger.Misc;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;

import java.util.Date;

/**
 * Class for retrieving and setting the id of the current user of the current
 * session (without using JAAS). All methods of this class require that a
 * {@link com.vaadin.server.VaadinRequest} is bound to the current thread.
 *
 * @see com.vaadin.server.VaadinService#getCurrentRequest()
 */
public final class CurrentDate {

    /**
     * The attribute key used to store the username in the session.
     */
    public static final String CURRENT_DATE_SESSION_ATTRIBUTE_KEY = CurrentDate.class
            .getCanonicalName();

    private CurrentDate() {
    }

    /**
     * Sets the current date and stores it in the current session.
     * Using a {@code null} userId will remove the date from the session.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static void set(Date currentDate) {
        if (currentDate == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                    CURRENT_DATE_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(
                    CURRENT_DATE_SESSION_ATTRIBUTE_KEY, currentDate);
        }
    }

    /**
     * Returns the current date stored in the current session, or today's date
     * if no date is stored.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static Date get() {
        Date currentDate = (Date) getCurrentRequest().getWrappedSession()
                .getAttribute(CURRENT_DATE_SESSION_ATTRIBUTE_KEY);
        if (currentDate == null) {
            return Misc.todayWithZeroTime();
        } else {
            return currentDate;
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
