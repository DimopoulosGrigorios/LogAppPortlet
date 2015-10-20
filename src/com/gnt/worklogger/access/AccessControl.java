package com.gnt.worklogger.access;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface AccessControl {

    public boolean signIn(Long id);

    public boolean isUserSignedIn();

    public boolean isUserInRole(String role);

}
