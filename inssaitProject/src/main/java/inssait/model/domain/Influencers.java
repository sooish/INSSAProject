package inssait.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Influencers {
	@Id
	private String instaId;
	private int followers;
	private int followings;
	private int feeds; 
}
