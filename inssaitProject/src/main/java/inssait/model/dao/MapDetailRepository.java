package inssait.model.dao;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import inssait.model.domain.MapDetail;

@Configuration
public class MapDetailRepository {
	@Bean
	public RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
		return RestClients.create(clientConfiguration).rest();
	}

	private static MapDetailRepository instance = new MapDetailRepository();

	public static MapDetailRepository getInstance() {
		return instance;
	}

	public void coreInfoSaveToES(MapDetail mapDetail, int loopNum) throws IOException {
		RestHighLevelClient client = client();
		try {
			IndexRequest request = new IndexRequest("core-info", "_doc", Integer.toString(loopNum + 1));
			request.source(XContentFactory.jsonBuilder().startObject().field("insta-id", mapDetail.getInfluencerName())
					.field("hashtag", mapDetail.getHashTagToString()).field("post-date", mapDetail.getPostDate())
					.field("place", mapDetail.getPlace()).endObject());
			client.index(request, RequestOptions.DEFAULT);
		} finally {
			client.close();
		}
	}

	public SearchHit[] getLocationList() throws IOException {
		RestHighLevelClient client = client();
		SearchHit[] searchHit = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			String[] includeFields = new String[] { "place" };
			String[] excludeFields = new String[] { "post-date", "insta-id", "hashtag" };
			searchSourceBuilder.fetchSource(includeFields, excludeFields);
			searchSourceBuilder.from(0);
			searchSourceBuilder.size(10000);
			
			SearchRequest request = new SearchRequest("core-info");
			request.source(searchSourceBuilder);
			searchHit = client.search(request, RequestOptions.DEFAULT).getHits().getHits();
		} finally {
			client.close();
		}
		return searchHit;
	}

	public void saveLocationData(MapDetail mapDetail) throws IOException {
		RestHighLevelClient client = client();
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
					.field("address_name", mapDetail.getAddressName())
					.field("category_group_code", mapDetail.getCategoryGroupCode())
					.field("category_group_name", mapDetail.getCategoryGroupName())
					.field("category_name", mapDetail.getCategoryName()).field("distance", mapDetail.getDistance())
					.field("location_id", mapDetail.getId()).field("phone", mapDetail.getPhone())
					.field("place_name", mapDetail.getPlaceName()).field("place_url", mapDetail.getPlaceUrl())
					.field("road_address_name", mapDetail.getRoadAddressName()).field("x", mapDetail.getX())
					.field("y", mapDetail.getY()).endObject();
			UpdateRequest request = new UpdateRequest("core-info", "_doc", mapDetail.getEsId()).doc(builder);
			client.update(request, RequestOptions.DEFAULT);
		} finally {
			client.close();
		}
	}

	public SearchHit[] getLocationInfo(String indexName) throws IOException {
		RestHighLevelClient client = client();
		SearchHit[] searchHit = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.from(0);
			searchSourceBuilder.size(10000);

			SearchRequest request = new SearchRequest(indexName);
			request.source(searchSourceBuilder);
			searchHit = client.search(request, RequestOptions.DEFAULT).getHits().getHits();
		} finally {
			client.close();
		}
		return searchHit;
	}
	
	public void saveLocationByUser(MapDetail mapDetail, String userId) throws IOException {
		RestHighLevelClient client = client();
		try {
			IndexRequest request = new IndexRequest("location-byuser", "_doc", mapDetail.getEsId());
			request.source(XContentFactory.jsonBuilder().startObject().field("userId", userId).field("insta-id", mapDetail.getInfluencerName())
					.field("hashtag", mapDetail.getHashTagToString()).field("post-date", mapDetail.getPostDate())
					.field("place", mapDetail.getPlace()).field("address_name", mapDetail.getAddressName())
					.field("category_group_code", mapDetail.getCategoryGroupCode())
					.field("category_group_name", mapDetail.getCategoryGroupName())
					.field("category_name", mapDetail.getCategoryName()).field("distance", mapDetail.getDistance())
					.field("location_id", mapDetail.getId()).field("phone", mapDetail.getPhone())
					.field("place_name", mapDetail.getPlaceName()).field("place_url", mapDetail.getPlaceUrl())
					.field("road_address_name", mapDetail.getRoadAddressName()).field("x", mapDetail.getX())
					.field("y", mapDetail.getY()).endObject());
			client.index(request, RequestOptions.DEFAULT);
		} finally {
			client.close();
		}
	}

}
