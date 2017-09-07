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

public class PlacesController extends Controller {
	
	@Inject
	WSClient ws;
	@Inject
	HttpExecutionContext executionContext;
	@Inject
	FormFactory formFactory;
	
	private String composeRequestUrl(String city) {
		String placeHolder = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+";
		String key = "&key=AIzaSyCLi_YGm1Ld3R9pMzQZWN-v4KzdaCGZQCw";
		String finalRequest = placeHolder.concat(city);
		finalRequest = finalRequest.concat(key);
		
		String defaultUrl = "https://maps.googleapis.com/maps/api/place/search/json?location=46.5882,-95.4075&radius=50000&types=lodging&sensor=false&key=AIzaSyCLi_YGm1Ld3R9pMzQZWN-v4KzdaCGZQCw";
		return defaultUrl;
		//return finalRequest;
	}
	
	public CompletionStage<Result> getPlaces(String city) {	
		if(city == null) {
			city = "Toronto";
		}		
		final String myCity = city;
				
		String requestUrl = composeRequestUrl(city);
		GooglePlace placesResult = new GooglePlace();
		return ws.url(requestUrl).get().thenApplyAsync((response) -> {
			JsonNode jsonNode = response.asJson();			
			List<PlaceResult> myPlaces = processJsonNode(jsonNode, placesResult);			
			return ok(places.render(myCity, myPlaces));
		}, executionContext.current());
	}
	
	private ArrayList<PlaceResult> processJsonNode(JsonNode jsonNode, GooglePlace placesResult) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		GoogleMapper mapper = gson.fromJson(jsonNode.toString(), GoogleMapper.class);
		ArrayList<PlaceResult> results = mapper.getResults();

		if(results != null && !results.isEmpty()) {
			placesResult.setPlaceResult(results);
		}
		
		if(results != null && !results.isEmpty()){
		    return results;
		  }else {
			  return new ArrayList<PlaceResult>();
		  }		
	}

	
}