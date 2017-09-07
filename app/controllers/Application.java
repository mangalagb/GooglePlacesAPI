package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.activation.MimetypesFileTypeMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 */
public class Application extends Controller {

	@Inject
	FormFactory formFactory;
	@Inject
	WSClient ws;
	@Inject
	HttpExecutionContext executionContext;

	@Inject
	public Application(HttpExecutionContext ec, WSClient ws) {
		this.executionContext = ec;
		this.ws = ws;
	}

	/**
	 * An action that renders an HTML page with a welcome message.
	 */
	public Result index() {
		String message = "Hello. Welcome to the Google Places API. If you are a new user, signup. Else, login.";
		return ok(firstpage.render(message));
	}

}
