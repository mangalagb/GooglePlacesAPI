package models;

import java.util.ArrayList;
import java.util.List;
//import play.data.validation.ValidationError;


import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class User {
	@Required
    protected String email;
	
	@Required
    protected String password;
	
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
        if (authenticate(email, password) == null) {
        	errors.add(new ValidationError("email", "invalid"));
        }
        return errors.isEmpty() ? null : errors;
	}
	
	private String authenticate(String email, String password) {
		if(email.length()==0 || password.length() == 0 || !email.contains("@") || password.equals(email)) {
			return null;
		}
		return "ok";
	}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
