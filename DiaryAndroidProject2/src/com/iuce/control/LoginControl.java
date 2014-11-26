package com.iuce.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginControl implements ILoginControl {

	static final int READ_BLOCK_SIZE = 100;
	private String fileName = "user.txt";

	@Override
	public boolean controlPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public void readUserDataFromFile(FileInputStream f) {

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
	}

}
