package models;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Photo {
	
	@SerializedName("html_attributions")
	private List<String> html_attributions;
	
	@SerializedName("height")
	private String height;
	
	@SerializedName("width")
	private String width;
	
	@SerializedName("photo_reference")
	private String photo_reference;
	
	public List<String> getHtmlAttributions() {
		return html_attributions;
	}
	
	public String getPhotoReference() {
		return photo_reference;
	}
	
	public String getHeight() {
		return height;
	}
	
	public String getWidth() {
		return width;
	}
}
