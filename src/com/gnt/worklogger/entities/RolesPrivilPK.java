//package com.gnt.worklogger.entities;
//
//import java.io.Serializable;
//import javax.persistence.*;
//
///**
// * The primary key class for the ROLES_PRIVIL database table.
// * 
// */
//@Embeddable
//public class RolesPrivilPK implements Serializable {
//	//default serial version id, required for serializable classes.
//	private static final long serialVersionUID = 1L;
//
//	@Column(name="ROLE_CODE", insertable=false, updatable=false)
//	private long roleCode;
//
//	@Column(name="PRIV_ID", insertable=false, updatable=false)
//	private long privId;
//
//	public RolesPrivilPK() {
//	}
//	public long getRoleCode() {
//		return this.roleCode;
//	}
//	public void setRoleCode(long roleCode) {
//		this.roleCode = roleCode;
//	}
//	public long getPrivId() {
//		return this.privId;
//	}
//	public void setPrivId(long privId) {
//		this.privId = privId;
//	}
//
//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof RolesPrivilPK)) {
//			return false;
//		}
//		RolesPrivilPK castOther = (RolesPrivilPK)other;
//		return 
//			(this.roleCode == castOther.roleCode)
//			&& (this.privId == castOther.privId);
//	}
//
//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + ((int) (this.roleCode ^ (this.roleCode >>> 32)));
//		hash = hash * prime + ((int) (this.privId ^ (this.privId >>> 32)));
//		
//		return hash;
//	}
//}