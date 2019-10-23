package inssait.model.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticSearch {
	@Bean
	public static RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
		return RestClients.create(clientConfiguration).rest();
	}

	private static RestHighLevelClient client = client();
	private static ElasticSearch instance = new ElasticSearch();

	public static ElasticSearch getInstance() {
		return instance;
	}

	public void coreInfoSaveToES(String influencerName, String hashTagToString, String postDate, String place,
			int loopNum) throws IOException {
		IndexRequest request = new IndexRequest("core-info", "_doc", Integer.toString(loopNum + 1));
		request.source(XContentFactory.jsonBuilder().startObject().field("insta-id", influencerName)
				.field("hashtag", hashTagToString).field("post-date", postDate).field("place", place).endObject());
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
	}

	public ArrayList<SearchHit[]> getLocationList() {
		ArrayList<SearchHit[]> locationList = new ArrayList<>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		String[] includeFields = new String[] { "place" };
		String[] excludeFields = new String[] { "post-date", "insta-id", "hashtag" };
		searchSourceBuilder.fetchSource(includeFields, excludeFields);
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(10000);

		SearchRequest request = new SearchRequest("core-info");
		request.source(searchSourceBuilder);

		SearchResponse response = null;
		SearchHits searchHits = null;

		try {
			response = client.search(request, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			locationList.add(searchHits.getHits());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return locationList;
	}

	public void saveLocationData(String esId, String addressName, String categoryGroupCode, String categoryGroupName,
			String categoryName, String distance, String id, String phone, String placeName, String placeUrl,
			String roadAddressName, String x, String y) throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("address_name", addressName)
				.field("category_group_code", categoryGroupCode).field("category_group_name", categoryGroupName)
				.field("category_name", categoryName).field("distance", distance).field("location_id", id)
				.field("phone", phone).field("place_name", placeName).field("place_url", placeUrl)
				.field("road_address_name", roadAddressName).field("x", x).field("y", y).endObject();
		try {
			UpdateRequest request = new UpdateRequest("core-info", "_doc", esId).doc(builder);
			UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchHit[] getLocationInfo() {
		SearchHit[] locationInfoList = null;
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(10000);

		SearchRequest request = new SearchRequest("core-info");
		request.source(searchSourceBuilder);

		SearchResponse response = null;
		SearchHits searchHits = null;
//		searchHits = response.getHits();
//		for (int i = 0; i < searchHits.getHits()[0].getSourceAsString().split(",").length; i++) {
//			mass.add(searchHits.getHits()[0].getSourceAsString().split(",")[i].split(":")[0].replace("{", "")
//					.replace("\"", ""));
//		} "place_url"

		try {
			response = client.search(request, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			locationInfoList = searchHits.getHits();
//			for(int i = 0; i < searchHits.getHits().length; i++) {
//				if(searchHits.getHits()[i].getSourceAsString().contains("\"place_url\"")) {
//					locationInfoList.add(searchHits.getHits());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationInfoList;
	}

}
