package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GooglePlace {
	
	private ArrayList<PlaceResult> results;
	
	private ArrayList<File> photos;
	
	public List<PlaceResult> getResults() {
		return results;
	}
	
	public ArrayList<File> getGooglePhotos() {
		return photos;
	}

	public void setPlaceResult(ArrayList<PlaceResult> results) {
		this.results = results;
	}
	
	public void setPlacePhotos(ArrayList<File> photos) {
		this.photos = photos;

		
	}
}
