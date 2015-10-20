//package com.gnt.worklogger.entities;
//
//import java.io.Serializable;
//import javax.persistence.*;
//
//
///**
// * The persistent class for the ROLES_PRIVIL database table.
// * 
// */
//@Entity
//@Table(name="ROLES_PRIVIL")
//@NamedQuery(name="RolesPrivil.findAll", query="SELECT r FROM RolesPrivil r")
//public class RolesPrivil implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@EmbeddedId
//	private RolesPrivilPK id;
//
//	//bi-directional many-to-one association to Role
//	@ManyToOne
//	@JoinColumn(name="ROLE_CODE")
//	private Role role;
//
//	public RolesPrivil() {
//	}
//
//	public RolesPrivilPK getId() {
//		return this.id;
//	}
//
//	public void setId(RolesPrivilPK id) {
//		this.id = id;
//	}
//
//	public Role getRole() {
//		return this.role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
//
//}