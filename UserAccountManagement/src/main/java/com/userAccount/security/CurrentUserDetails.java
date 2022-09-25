package com.userAccount.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.userAccount.model.User;

public class CurrentUserDetails implements UserDetails {
    
	private  User user;
	
	
	public CurrentUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String getFullName() {
		
		return user.getFirstName() +" "+ user.getLastName();
	}
	
	public String getGender() {
		return user.getGender();
	}
	
	public String getMaritalStatus() {
		return user.getMaritalStatus();
	}
	public String getNationality() {
		return user.getNationality();
	}
	
	public String getDob() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return  sdf.format(user.getDob()); 
	}
	
	public int getAge() {
		Calendar cal = Calendar.getInstance();
		int year;
		Long currentTime=System.currentTimeMillis();
		cal.setTime(user.getDob());
		
		Calendar now = Calendar.getInstance();
		year=now.get(Calendar.YEAR)-cal.get(Calendar.YEAR);
		return year;
	}
	
	

}
