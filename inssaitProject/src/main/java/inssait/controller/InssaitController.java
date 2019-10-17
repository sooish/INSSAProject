package inssait.controller;

import java.io.IOException;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.apache.xml.internal.utils.URI;

import inssait.model.domain.MapDetail;
import inssait.model.domain.Members;
import inssait.service.InssaitService;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class InssaitController {

	@Autowired
	private InssaitService service;

	public InssaitController() {
		System.out.println("--- InssaitController ---");
	}

	// 크롤링 해서 오라클, 엘라스틱서치에 정보 저장
	@GetMapping("/getAndSave")
	public void getAndSaveData(String id, String pw, Integer loopNum, Integer targetDate) {
		try {
			service.getAndSaveData(id, pw, loopNum, targetDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 엘라스틱서치와 연동, 정보 가져오고 저장
	@GetMapping("/load")
	public SearchHit[] loadLocationKeyword() {
		SearchHit[] searchHit = null;
		try {
			searchHit = service.getLocationList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchHit;
	}

	@GetMapping("/saveLocation")
	public void saveLocationData(MapDetail mapDetail) {
		try {
			service.saveLocationData(new MapDetail(mapDetail.getEsId(), mapDetail.getAddressName(), mapDetail.getCategoryGroupCode(), mapDetail.getCategoryGroupName(), mapDetail.getCategoryName(), mapDetail.getDistance(), mapDetail.getId(), mapDetail.getPhone(),
					mapDetail.getPlaceName(), mapDetail.getPlaceUrl(), mapDetail.getRoadAddressName(), mapDetail.getX(), mapDetail.getY()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getLocationInfo")
	public SearchHit[] getLocationInfo() {
		SearchHit[] searchHit = null;
		try {
			searchHit = service.getLocationInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchHit;
	}
	
	// ======================================================
	
	@PostMapping("/signUp")
	public String signUp(Members member, Model model) {
		String msg = "회원가입실패";
		model.addAttribute("msg", "회원가입성공");
		if(service.signUp(member)) {
			msg = "회원가입성공";
			model.addAttribute("msg", "회원가입성공");
		}
		return msg;
	}
//	return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, newUrl).build();
	@PostMapping("/login")
	public ResponseEntity<Object> login(Members member) {
		ResponseEntity<Object> response = null;
		if(service.login(member)) {
			response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, "index.html").build();
		}
		return response;
	}
}
