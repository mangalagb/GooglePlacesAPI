package models;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PlaceResult {
	public String getFormattedAddress() {
		return formatted_address;
	}

	public String getIcon() {
		return icon;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getRating() {
		return rating;
	}

	public String getReference() {
		return reference;
	}

	public List<String> getTypes() {
		return types;
	}
	
	public Photo getPhotoDetails(){
		if(!photos.isEmpty()) {
			return photos.get(0);
		}
		return null;
	}

	@SerializedName("formatted_address")
	private String formatted_address;

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("rating")
	private Double rating;

	@SerializedName("reference")
	private String reference;

	@SerializedName("types")
	private List<String> types;
	
	@SerializedName("photos")
	private ArrayList<Photo> photos;
}
