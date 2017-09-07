package controllers;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;
import models.*;

public class UsersController extends Controller {
	
	@Inject
	FormFactory formFactory;
	
	/*
	 * Method to implement registering a new user
	 */
	public Result createNewUser() {
		Form<User> userForm = formFactory.form(User.class);
		String message = "Rules for username and password : Make sure the email has a @ tag. The email and password cannot be the same.";
		return ok(signup.render(userForm, message));
	}
	
	/*
	 * Method to implement registering a new user.
	 * Verify if the email and password fields are correct.
	 * If so, take the user directly to see new restaurants.
	 * Else, return to the signup page.
	 */
	public Result authenticate() {
		Form<User> signupForm = formFactory.form(User.class).bindFromRequest();		
		if(signupForm.hasErrors()) {			
			String message = "Invalid values for the fields. Make sure the email has a @ tag. And the email and password cannot be the same.";
			Form<User> newUserForm = formFactory.form(User.class);
	        return badRequest(signup.render(newUserForm, message));
	    }else {
	    	User newUser = signupForm.get();
	    	session(newUser.getEmail(), newUser.getPassword());
	    	User user = signupForm.get();
	    	return ok(redirect.render(user));	    
	    }
	}
	
	/*
	 * Method to implement logging in of an existing user.
	 */
	public Result login() {
		Form<User> newUserForm = formFactory.form(User.class);
		String message = "Existing user login";
		return ok(login.render(newUserForm, message));
	}
	
	/*
	 * Method to implement logging in of an existing user.
	 * If login credentials are correct, take the existing user to see restaurants.
	 * Else, display error and force him to login correctly.
	 */
	public Result validateUser() {
		Form<User> filledForm = formFactory.form(User.class).bindFromRequest();
		User user = filledForm.get();
		
		//Session attributes
		String passwordInSession = session(user.getEmail());
		if(passwordInSession != null && passwordInSession.equals(user.getPassword())) {
			return ok(redirect.render(user));
	    }else {
	    	String message = "Invalid password. Login again";
			Form<User> newUserForm = formFactory.form(User.class);
	        return badRequest(login.render(newUserForm, message));
	    }		    
	}
	
	/*
	 * Method to reset the existing password.
	 * Only an existing user who has logged in successfully can perform this action.
	 */
	public Result resetPassword() {
		Form<User> userForm = formFactory.form(User.class);
		String message = "This form is to reset your password";
		return ok(resetpassword.render(userForm, message));
	}
	
	/*
	 * Method to reset the existing password.
	 * Only an existing user who has logged in successfully can perform this action.
	 * After this method call, the old password is invalid. User can only use the new password.
	 * Also, after reset, the user is redirected to the login page where he must login using the new credentials.
	 */
	public Result processResetPassword() {
		Form<User> resetForm = formFactory.form(User.class).bindFromRequest();	
		if(resetForm.hasErrors()) {			
			String message = "Invalid values for the fields. Make sure the email has a @ tag. And the email and password cannot be the same.";
			Form<User> newUserForm = formFactory.form(User.class);
	        return badRequest(resetpassword.render(newUserForm, message));
	    }else {
	    	User newUser = resetForm.get();
	    	String passwordInSession = session(newUser.getEmail());
	    	if(passwordInSession != null) {
	    		//Remove old password
	    		session().remove(newUser.getEmail());
	    		
	    		//Add new password
	    		session(newUser.getEmail(), newUser.getPassword());
	    	}
	    }
		return ok(resetpasswordcomplete.render());
	}
	
	/*
	 * Method to logout.
	 */
	public Result logout() {
		return redirect("/");
	}
	
}