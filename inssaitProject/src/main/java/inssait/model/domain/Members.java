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
	
	public Members(String memberId, String pw, String location, String address, String birthday, String gender) {
		this.memberId = memberId;
		this.pw = pw;
		this.location = location;
		this.address = address;
		this.birthday = birthday;
		this.gender = gender;
	}
	
}
