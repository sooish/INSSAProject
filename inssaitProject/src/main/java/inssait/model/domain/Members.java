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
public class Members {
	@Id
	private String memberId;
	private String pw;
	private String location;
	private String address;
	private String birthday;
	private String gender;
	private String instaId;
	
}
