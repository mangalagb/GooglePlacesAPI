package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.concurrent.HttpExecutionContext;
import play.api.libs.concurrent.ExecutionContextProvider;
import play.api.libs.concurrent.Promise;
import play.api.libs.json.Json;
import play.core.j.JavaResultExtractor;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.*;
import views.html.*;
import models.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class Application extends Controller {
	
	@Inject FormFactory formFactory;
	@Inject WSClient ws;
	@Inject HttpExecutionContext executionContext;
	
	@Inject
    public Application(HttpExecutionContext ec, WSClient ws) {
        this.executionContext = ec;
        this.ws = ws;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     */
    public Result signup() {
    	Form<User> userForm = formFactory.form(User.class);
    	return ok(signup.render(userForm));
    }
    
	public Result validateUser() {
    	Form<User> filledForm = formFactory.form(User.class).bindFromRequest();
    	String content = "empty initially";
    	
    	if(filledForm.hasErrors()) {
    		return ok(index.render("has errors"));
	}else {
		User user = filledForm.get();		
	    return ok(places.render(user.getEmail()));
	}
   }
	
	public CompletionStage<Result> index2() {
		String requestUrl = composeRequestUrl();
        return ws.url(requestUrl).get().thenApplyAsync((response) -> {
        	JsonNode jsonNode = response.asJson();
        	String body = processJsonNode(jsonNode);
            return ok(body);
        }, executionContext.current());
    } 
	
	private String composeRequestUrl() {
		String requestUrl = "https://maps.googleapis.com/maps/api/place/search/json?location=46.5882,-95.4075&radius=50000&types=lodging&sensor=false&key=AIzaSyCLi_YGm1Ld3R9pMzQZWN-v4KzdaCGZQCw";
		return requestUrl;
	}
	
	private String processJsonNode(JsonNode jsonNode) {
		String body = jsonNode.toString();
		return body;
	}

}
