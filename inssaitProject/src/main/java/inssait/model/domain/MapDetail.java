package inssait.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapDetail {
	private String esId;
	private String addressName;
	private String categoryGroupCode;
	private String categoryGroupName;
	private String categoryName;
	private String distance;
	private String id;
	private String phone;
	private String placeName;
	private String placeUrl;
	private String roadAddressName;
	private String x;
	private String y;
	private String influencerName;
	private String hashTagToString;
	private String postDate;
	private String place;
	
	public MapDetail(String influencerName, String hashTagToString, String postDate, String place) {
		this.influencerName = influencerName;
		this.hashTagToString = hashTagToString;
		this.postDate = postDate;
		this.place = place;
	}

	public MapDetail(String esId, String addressName, String categoryGroupCode, String categoryGroupName,
			String categoryName, String distance, String id, String phone, String placeName, String placeUrl,
			String roadAddressName, String x, String y) {
		this.esId = esId;
		this.addressName = addressName;
		this.categoryGroupCode = categoryGroupCode;
		this.categoryGroupName = categoryGroupName;
		this.categoryName = categoryName;
		this.distance = distance;
		this.id = id;
		this.phone = phone;
		this.placeName = placeName;
		this.placeUrl = placeUrl;
		this.roadAddressName = roadAddressName;
		this.x = x;
		this.y = y;
	}

}
