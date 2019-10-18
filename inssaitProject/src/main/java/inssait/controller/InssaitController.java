package inssait.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.elasticsearch.search.SearchHit;
import org.omg.PortableServer.ForwardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import inssait.model.domain.MapDetail;
import inssait.model.domain.Members;
import inssait.model.domain.SearchInfo;
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
	
	@PostMapping("/saveSearchInfo")
	public void saveSearchInfo (SearchInfo sInfo) {
		try {
			service.saveSearchInfo(sInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getSearchInfo")
	public Iterable<SearchInfo> getAllSearchInfo() {
		return service.getAllSearchInfo();
	}
	
	// ======================================================
	
	@PostMapping("/signUp")
	public ResponseEntity<Object> signUp(Members member, Model model) {
		ResponseEntity<Object> response = null;
		try {
			if(service.signUp(member)) {
				response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
			               .body("<script>sessionStorage.setItem('" + member.getMemberId() +"', '" + member.getMemberId()+ "');"
			               		+ "location.href='showMarker.html';</script>");
			}else {
			     response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
			           .body("<script>alert('정보가 유효하지 않습니다.'); history.go(-1);</script>");
			}
		} catch (Exception e) {
			System.out.println(e);
			response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
			           .body("<script>alert('정보가 유효하지 않습니다.'); history.go(-1);</script>");
		}
		return response;
	}
	@PostMapping("/login")
	public ResponseEntity<Object> login(Members member) {
		ResponseEntity<Object> response = null;
		try {
			if(service.login(member) && member.getMemberId().equals("salad")) {
				response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
			               .body("<script>sessionStorage.setItem('" + member.getMemberId() +"', '" + member.getMemberId()+ "');"
			               		+ "location.href='manager.html';</script>");
			}else if(service.login(member)) {
				response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
			               .body("<script>sessionStorage.setItem('" + member.getMemberId() +"', '" + member.getMemberId()+ "');"
			               		+ "location.href='showMarker.html';</script>");
			}else {
		         response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
		               .body("<script>alert('로그인 정보를 확인해주세요.'); history.go(-1);</script>");
			}
		} catch (Exception e) {
			System.out.println(e);
	         response = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
	               .body("<script>alert('로그인 정보를 확인해주세요.'); history.go(-1);</script>");
		}
		return response;
	}
	
	// 로그아웃 로직
	@GetMapping("/logout2")
	public ResponseEntity<Object> logout() {
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Content-Type", "text/html; charset=UTF-8")
	               .body("<script>sessionStorage.clear();"
		               		+ "alert('로그아웃되었습니다.'); location.href='login.html';</script>");
	}
}
