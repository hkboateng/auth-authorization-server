package com.protecksoftware.authentication.business.entity;

import org.springframework.security.core.GrantedAuthority;

public class Permission implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String permission;
	private Long permissionId;
	
	@Override
	public String getAuthority() {
		return permission;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
