package com.iuce.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginControl implements ILoginControl {

	// dosyadan kac karakter okumasý gerektigi
	static final int READ_BLOCK_SIZE = 100;

	// sifrelerin saklandýgý dosya
	private String fileName = "user.txt";

	// algoritmasý cozulmus sifre
	private String password;

	// sifrelerin sifrelendigi ve sifrelenmis sifrelerin tekrar cozuldugu sinif
	private PasswordAlgorithm passAlgorithm;

	@Override
	public boolean controlPassword(String password) {
		// TODO Auto-generated method stub
		if (password.equals(this.password)) {
			return true;
		}
		return false;
	}

	public void passwordFromFile(FileInputStream f) {
		String encyPassword = readUserDataFromFile(f);
		passAlgorithm = new PasswordAlgorithm(encyPassword);
		this.password = passAlgorithm.decyription();
	}

	public String readUserDataFromFile(FileInputStream f) {

		InputStreamReader inputRead = new InputStreamReader(f);
		StringBuffer fileContent = new StringBuffer("");
		char[] buffer = new char[READ_BLOCK_SIZE];
		int charRead;
		String s = "";
		try {
			while ((charRead = inputRead.read(buffer)) > 0) {
				String readString = String.copyValueOf(buffer, 0, charRead);
				s += readString;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

}
