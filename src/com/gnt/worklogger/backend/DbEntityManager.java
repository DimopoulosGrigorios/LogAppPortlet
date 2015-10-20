package com.gnt.worklogger.backend;

import java.util.List;

/**
 * This interface defines the minimum functionality that a DbEntityManager should have.
 * 
 * @author pkar
 *
 */
public interface DbEntityManager {
	
	/**
	 * Store an object to the database.
	 * This method is used the first time the object is created.
	 * Also logs the user who created the object.
	 * 
	 * @param obj
	 * 		The object that need to be stored in the database
	 * @param user
	 * 		The user who created the object, could be null 
	 * @throws Exception 
	 */
	public void persist(Object obj, Object user) throws Exception;
	
	/**
	 * Store to the database and object (child) that has a Many-to-One relationship with
	 * an other object (parent). This method should not only store child object but also 
	 * add it to the parent object's appropriate list.  
	 * Also logs the user who created the object.
	 * 
	 * @param child
	 * 		The object to be stored to the database
	 * @param parent
	 * 		The object whose lists should be updated
	 * @param user
	 * 		The user who created the child, could be null 
	 * @throws Exception 
	 */
	public void persistChild(Object child, Object parent, Object user) throws Exception;
	
	/**
	 * Stores the changes of an object to the database. 
	 * This method is used when an existing object is updated.
	 * Also logs the user who edited the object.
	 * 
	 * @param obj
	 * 		The object that need to be stored in the database
	 * @param user
	 * 		The user who edited the object, could be null 
	 */
	public Object merge(Object obj, Object user) throws Exception;
	
	/**
	 * Delete an object from the database. 
	 * This method is used when an existing object is deleted.
	 * Also logs the user who deleted the object.
	 * 
	 * @param obj
	 * 		The object that need to be deleted from the database
	 * @param user
	 * 		The user who deleted the object, could be null 
	 * @throws Exception 
	 */
	public void delete(Object obj, Object user) throws Exception;
	
	/**
	 * Delete from the database an object  (child) that has a Many-to-One relationship with
	 * an other object (parent). This method should not only delete child object but also 
	 * remove it from the parent object's appropriate list.  
	 * Also logs the user who deleted the child.
	 * 
	 * @param child
	 * 		The object to be deleted from the database
	 * @param parent
	 * 		The object whose lists should be updated
	 * @param user
	 * 		The user who deleted the child, could be null 
	 */
	public void deleteChild(Object child, Object parent, Object user) throws Exception;
	
	/**
	 * Lock an object for edit. 
	 * While it is locked, no other process will be able to edit/delete it.
	 * 
	 * @param obj
	 * 		The object to be locked.
	 */
	public void lockForEdit(Object obj) throws Exception;
	
	/**
	 * Release the lock of the object.
	 * Other processes will now be allowed to edit/delete it. 
	 * 
	 * @param obj
	 * 		The object to be unlocked.
	 */
	public void releaseLock(Object obj);
	
	/**
	 * Check if the object is locked for edit
	 * 
	 * @param obj
	 * 		The object to be checked if locked for edit.
	 */
	public boolean isLockedForEdit(Object obj);
	
	/**
	 * Find an object from the database.
	 * 
	 * @param c
	 * 		The Java class of the object to be found
	 * @param id
	 * 		The id/primary key of the object
	 * 
	 * @return
	 * 		The object found from database or null if not found.
	 */
	public Object find(Class<?> c, Object id);
	
	/**
	 * Find an object from the database and also lock it for edit.
	 * 
	 * @param c
	 * 		The Java class of the object to be found
	 * @param id
	 * 		The id/primary key of the object
	 * 
	 * @return
	 * 		The object found from database or null if not found.
	 * @throws EntityLockedException 
	 */
	public Object findForEdit(Class<?> c, Object id) throws Exception;
	
	/**
	 * Find all objects of this class type from the database.
	 * 
	 * @param c
	 * 		The Java class of the objects to be found
	 * 
	 * @return
	 * 		The object found from database or null if not found.
	 */
	public List<? extends Object> findAll(Class<?> c);
}
