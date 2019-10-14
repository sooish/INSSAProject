package inssait.model.dao;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticSearch {
	@Bean
	public static RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo("localhost:9200").build();
		return RestClients.create(clientConfiguration).rest();
	}
	private static RestHighLevelClient client = client();
	private static ElasticSearch instance = new ElasticSearch();
	public static ElasticSearch getInstance() {
		return instance;
	}
	
	public void coreInfoSaveToES(String influencerName, String hashTagToString, String postDate, String place, int loopNum) throws IOException {
		IndexRequest request = new IndexRequest("core-info", "_doc", Integer.toString(loopNum + 1));
		request.source(XContentFactory.jsonBuilder().startObject().field("insta-id", influencerName)
				.field("hashtag", hashTagToString)
				.field("post-date", postDate)
				.field("place", place).endObject());
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
	}

}
