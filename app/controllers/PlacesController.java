package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.mvc.*;
import views.html.*;
import models.*;

/*
 * Controller to handle user actions
 */
public class PlacesController extends Controller {
	
	@Inject
	WSClient ws;
	@Inject
	HttpExecutionContext executionContext;
	@Inject
	FormFactory formFactory;
	
	/*
	 * Method that generates the url for the request
	 */
	private String composeRequestUrl(String city) {
		String placeHolder = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+";
		String key = "&key=AIzaSyCLi_YGm1Ld3R9pMzQZWN-v4KzdaCGZQCw";
		String finalRequest = placeHolder.concat(city);
		finalRequest = finalRequest.concat(key);
		
		String defaultUrl = "https://maps.googleapis.com/maps/api/place/search/json?location=46.5882,-95.4075&radius=50000&types=lodging&sensor=false&key=AIzaSyCLi_YGm1Ld3R9pMzQZWN-v4KzdaCGZQCw";
		//return defaultUrl;
		return finalRequest;
	}
	
	/*
	 * Method where the call to the external API is made.
	 * This is a way to do a asynchronous computation using Java 8 lambdas
	 * A CompletionStage is a promise. It promises that the computation eventually will be done.
	 */
	public CompletionStage<Result> getPlaces(String city) {
		//Default city setting in case the user does not specify it
		if(city == null) {
			city = "Toronto";
		}		
		final String myCity = city;
		
		//Get the url
		String requestUrl = composeRequestUrl(city);
		return ws.url(requestUrl).get().thenApplyAsync((response) -> {
			JsonNode jsonNode = response.asJson();			
			List<PlaceResult> myPlaces = processJsonNode(jsonNode);			
			return ok(places.render(myCity, myPlaces));
		}, executionContext.current());
	}
	
	/*
	 * Method to process the response.
	 * It uses Gson library to serialize the JSON response
	 */
	private ArrayList<PlaceResult> processJsonNode(JsonNode jsonNode) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		GoogleMapper mapper = gson.fromJson(jsonNode.toString(), GoogleMapper.class);
		ArrayList<PlaceResult> results = mapper.getResults();
		
		if(results != null && !results.isEmpty()){
		    return results;
		  }else {
			  return new ArrayList<PlaceResult>();
		  }		
	}

	
}