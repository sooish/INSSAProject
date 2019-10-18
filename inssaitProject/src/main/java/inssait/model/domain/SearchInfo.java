package inssait.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchInfo {

	@Id
	@GeneratedValue(generator = "SEARCH_INFO_SEQ")
	private Integer SearchInfoId;
	private String memberId;
	private String searchLocation;
	private String category;
	private String dateOfSearch;
	
}
