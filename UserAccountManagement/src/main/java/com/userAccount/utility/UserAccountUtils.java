package com.userAccount.utility;

import org.springframework.stereotype.Component;

@Component
public class UserAccountUtils {
	
	private String message="";
	
	
	
	private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

    
	public boolean isPasswordValid(String password) {
		if(password.matches(PASSWORD_PATTERN)) {
			return true;
		} else {
			message="Pasword is not Strong.";
			return false;
		}
		
	}
    
    
    public void sendNotification() {
    	
    }
}
