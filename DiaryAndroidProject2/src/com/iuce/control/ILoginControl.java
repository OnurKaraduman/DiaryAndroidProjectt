package com.iuce.control;

public interface ILoginControl {

	public boolean controlPassword(String password);
	public boolean resetPassword(String password);
	public boolean savePassword(String password);
	public boolean controlFirstOpen();
	public boolean changePassword(String currentPassword, String newPassword);
}
