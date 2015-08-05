package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class PasswordForm implements Serializable{
    private static final long serialVersionUID = 1L;

    @NotNull
	private String oldPassword;
    
    @NotNull
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	@Override
	public String toString() {
		return "PasswordForm [oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + "]";
	}
	
}
