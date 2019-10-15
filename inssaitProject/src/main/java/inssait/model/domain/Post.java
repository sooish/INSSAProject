package inssait.model.domain;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName="core-info", type="_doc")
public class Post {
	@Id
	private Long id;
	@Field(name="insta-id")
	private String instaId;
	private String hashtag;
	@Field(name="post-date")
	private String postDate;
	private String place;
	
	public Post(Long id, String hashtag, String place) {
		this.id=id;
		this.hashtag=hashtag;
		this.place=place;
	}

	public Post(Long id, String instaId, String hashtag, String postDate, String place) {
		System.out.println("Constructor");
		System.out.println(id);
		System.out.println(instaId);
		System.out.println(hashtag);
		System.out.println(postDate);
		System.out.println(place);
		this.id = id;
		this.instaId = instaId;
		this.hashtag = hashtag;
		this.postDate = postDate;
		this.place = place;
	}
}
