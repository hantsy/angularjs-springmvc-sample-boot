package com.hantsylabs.restexample.springmvc.api;

public class PasswordForm {

	private String oldPassword;
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
