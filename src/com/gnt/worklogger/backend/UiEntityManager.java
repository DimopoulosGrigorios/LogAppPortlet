package com.gnt.worklogger.backend;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.gnt.idb.business.helpers.LoggingHelper;
//import com.gnt.idb.persistence.entities.AppUser;
//import com.gnt.idb.persistence.enums.UserLogAction;
//import com.gnt.idb.ui.IdbUi;
//import com.gnt.idb.ui.tablePanel.DbEntityManager;
//import com.gnt.idb.utilities.UserLogAppender;

@SuppressWarnings("serial")
public class UiEntityManager implements DbEntityManager, Serializable
{
//	private final static Logger logger = LoggerFactory.getLogger(UiEntityManager.class); 
	
	//@PersistenceContext
	//private EntityManager em;
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("LogAppPortlet");
	private EntityManager em;

	@Override
	public void persist(Object obj, Object user) throws Exception{
		try {
		getEm().persist(obj);
		}
		finally {
			closeEm();
		}
//		if (user!=null)
//		{
//			AppUser appuser = (AppUser) user;
//			String userActionDescription = "Created "+obj.getClass().getSimpleName()+": "+getEntityFullDescription(obj);
//			logInDB(appuser, UserLogAction.CR, userActionDescription, obj);
//		}
	}
	
	public Object findID(Object obj) throws Exception
	{
//		return obj;
//		if (obj instanceof DbEntity)
//		{
//			return ((DbEntity)obj).getUniqueID();
//		}
//		else
//		{
			Object id = null;
			for (Field field: obj.getClass().getDeclaredFields())
			{
				if (field.getAnnotation(Id.class)!=null)
				{
					field.setAccessible(true);
					id = field.get(obj);
					break;
				}
			}
			return id;
//		}
	}

	@Override
	public void persistChild(Object child, Object parent, Object user)  throws Exception{
		//set parent in child object
		for (Field field: child.getClass().getDeclaredFields())
		{
			if (field.getType().equals(parent.getClass()))
			{
				field.setAccessible(true);
				field.set(child, parent);
				field.setAccessible(false);
			}
		}
		try {
		persist(child, user);
		getEm().getEntityManagerFactory().getCache().evict(parent.getClass(), findID(parent));
		}
		finally {
			closeEm();
		}
	}

	@Override
	public Object merge(Object obj, Object user) throws Exception 
	{	
		try {
		getEm().detach(obj);
//		Object oldObj = getEm().find(obj.getClass(), findID(obj));
//		if (oldObj==null)
//			throw new Exception("Το αντικείμενο δεν υπάρχει στη βάση. Παρακαλώ ξαναφορτώστε τα δεδομένα.");
//		String entityChangesDescription = getEntityChangesDescription(oldObj, obj);
		obj = getEm().merge(obj);
		releaseLock(obj);
		return obj;
//		if (user!=null)
//		{
//			AppUser appuser = (AppUser) user;
//			if(!StringUtils.isEmpty( entityChangesDescription ) ){
//				String userActionDescription = "Edited "+oldObj.getClass().getSimpleName()+": "+entityChangesDescription;
//				logInDB(appuser, UserLogAction.UP, userActionDescription, obj);
//			}
//		}
		}
		finally {
			closeEm();
		}
	}
	
//	public void logInDB(AppUser u, UserLogAction usrlAction, String usrlInfo, Object obj) {
//		LoggingHelper.logInDB(u, usrlAction, usrlInfo, obj, logger, getEm());
//	}

	@Override
	public void delete(Object obj, Object user) throws Exception {
		try {
		obj = getEm().find(obj.getClass(), findID(obj));
		if (obj==null)
			throw new Exception("Το αντικείμενο δεν υπάρχει στη βάση. Παρακαλώ ξαναφορτώστε τα δεδομένα.");
		getEm().remove(obj);
		releaseLock(obj);
		}
		finally {
			closeEm();
		}
//		if (user!=null)
//		{
//			AppUser appuser = (AppUser) user;
//			String userActionDescription = "Deleted "+obj.getClass().getSimpleName()+": "+getEntityFullDescription(obj);
//			logInDB(appuser, UserLogAction.DE, userActionDescription, obj);
//		}
	}

	@Override
	public void deleteChild(Object child, Object parent, Object user) throws Exception {
		try {
		delete(child, user);
		getEm().getEntityManagerFactory().getCache().evict(parent.getClass(), findID(parent));
		}
		finally {
			closeEm();
		}
	}

	@Override
	public void lockForEdit(Object obj) throws Exception {
//		if (obj instanceof DbEntity)
//		{
//			boolean lockSuccess = EntityLocker.lockEntity((DbEntity) obj);
//			if (!lockSuccess)
//				throw new Exception("Entity is locked for edit by another user.");
//		}
//		else
//			throw new Exception("Object "+obj.toString()+" is not an instance of "+DbEntity.class.getName());
	}

	@Override
	public void releaseLock(Object obj) {
//		if (obj instanceof DbEntity)
//			EntityLocker.unlockEntity((DbEntity) obj);
	}

	@Override
	public boolean isLockedForEdit(Object obj) {
//		if (obj instanceof DbEntity)
//			return EntityLocker.isLocked((DbEntity) obj);
//		else 
			return false;
	}

	@Override
	public Object find(Class<?> c, Object id) {
		try {
        return getEm().find(c, id);
		}
		finally {
			closeEm();
		}
	}

	@Override
	public Object findForEdit(Class<?> c, Object id) throws Exception {
		Object obj = find(c, id);
		lockForEdit(obj);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends Object> executeCustomQuerySelect(String query, Object... parameters)
	{
		try {
		Query q = getEm().createQuery(query);
		int i=1;
		for (Object obj: parameters)
			q.setParameter(i++, obj);
        return q.getResultList();
		}
		finally {
			closeEm();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends Object> executeNamedQuerySelect(String query, Object... parameters)
	{
		try {
		Query q = getEm().createNamedQuery(query);
		int i=1;
		for (Object obj: parameters)
			q.setParameter(i++, obj);
//		System.out.println(q.toString());
		return q.getResultList();
		}
		finally {
			closeEm();
		}
	}
	
	public int executeNamedQueryUpdate(String query, Object... parameters)
	{
		try {
		Query q = getEm().createNamedQuery(query);
		int i=1;
		String params = " Query Parameters: {";
		for (Object obj: parameters){
			params += obj.toString() + ", ";
			q.setParameter(i++, obj);
		}
		params += "}";
		int recordsUpdated = q.executeUpdate();

//		logInDB(IdbUi.get().getAppInfo().getLogginUser(), UserLogAction.OT, q.toString() + params, null);
		return recordsUpdated;
		}
		finally {
			closeEm();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> findAll(Class<?> c) {
		try {
		Query q = getEm().createQuery("SELECT i from "+c.getName()+" i");
        return q.getResultList();
		}
		finally {
			closeEm();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> executeNativeQuerySelect(String query){
		try {
		Query q = getEm().createNativeQuery(query);
        return q.getResultList();
		}
		finally {
			closeEm();
		}
	}
	
	public int executeNativeQueryUpdate(String query){
		try {
		Query q = getEm().createNativeQuery(query);
//		logInDB(IdbUi.get().getAppInfo().getLogginUser(), UserLogAction.OT, q.toString(), null);
		return  q.executeUpdate();
		}
		finally {
			closeEm();
		}
	}
	
	public void invalidateCache(){
		getEm().getEntityManagerFactory().getCache().evictAll();
	}
	
	@SuppressWarnings("rawtypes")
	public void invalidateCache(Class classname){
		getEm().getEntityManagerFactory().getCache().evict(classname);
	}
	
//	private String getEntityFullDescription(Object object) throws Exception
//	{
//		String description;
//		StringBuilder sb = new StringBuilder();
//		for (Field field: object.getClass().getDeclaredFields())
//		{
//			if (!(List.class.isAssignableFrom(field.getType())) 
//					&& !(Set.class.isAssignableFrom(field.getType()))
//					&& !(Modifier.isFinal(field.getModifiers()))
//					&& field.getAnnotation(OneToMany.class)==null
//					&& !field.getName().startsWith("_persistence_"))
//			{
//				field.setAccessible(true);
////				sb.append(field.getName()).append(":").append(field.get(object)).append(", ");
//				sb.append(field.getName()).append(" : ").append( (field.get(object) == null) ? " - " : field.get(object) ).append(", "); // hide nulls
//			}
//		}
//		description = sb.toString();
//		if (description.length()>=2)
//			description = "{"+description.substring(0, description.length()-2)+"}";
//		return description;
//	}
//	
//	public String getEntityChangesDescription(Object oldObject, Object newObject) throws Exception
//	{
//		String description;
//		StringBuilder sb = new StringBuilder();
//		for (Field field: oldObject.getClass().getDeclaredFields())
//		{
//			if (!(List.class.isAssignableFrom(field.getType())) 
//					&& !(Set.class.isAssignableFrom(field.getType()))
//					&& !(Modifier.isFinal(field.getModifiers()))
//					&& field.getAnnotation(OneToMany.class)==null
//					&& !field.getName().startsWith("_persistence_"))
//			{
//				field.setAccessible(true);
//				
//				UserLogAppender appender = new UserLogAppender( oldObject, newObject );
//				String message = appender.getAppenderMessage();
//				if( appender.isSubObjectChanged() ){
//					sb.append( message ).append(",");
//					break;
//				}else{
////					if (field.get(oldObject)!=null && !field.get(oldObject).equals(field.get(newObject)))
////						sb.append(message).append(field.getName()).append(":").append(field.get(oldObject)).append("->").append(field.get(newObject)).append(", ");
//					
//					Object fieldToCompare, otherField;
//					if(field.get(oldObject) != null){
//						fieldToCompare = field.get(oldObject);
//						otherField = field.get(newObject);
//						if (!fieldToCompare.equals(otherField))
//							sb.append(message).append(field.getName()).append(" : ").append( (field.get(oldObject) == null) ? " - " : field.get(oldObject) ).append(" -> ").append( (field.get(newObject) == null) ? " - " : field.get(newObject) ).append(", ");// hide nulls					
//					}
//					else if(field.get(newObject) != null){
//						fieldToCompare = field.get(newObject);
//						otherField = field.get(oldObject);
//						if (!fieldToCompare.equals(otherField))
//							sb.append(message).append(field.getName()).append(" : ").append( (field.get(oldObject) == null) ? " - " : field.get(oldObject) ).append(" -> ").append( (field.get(newObject) == null) ? " - " : field.get(newObject) ).append(", ");// hide nulls					
//					}
//					
//				}
//			}	
//		}
//		description = sb.toString();
//		if (description.length()>=2)
//			description = "{ "+description.substring(0, description.length()-2)+" }";
//		return description;
//	}

	public CriteriaBuilder getCriteriaBuilder() {
		return getEm().getCriteriaBuilder();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Query createQuery(CriteriaQuery criteriaQuery) {
		return getEm().createQuery(criteriaQuery);
	}

	public EntityManager getEm() {
		if(em==null||em.isOpen()==false) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
		}
		return em;
	}
	
	public void closeEm() {
		em.getTransaction().commit();
		em.close();
		em=null;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}