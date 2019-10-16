package inssait.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import inssait.model.domain.MapDetail;
import inssait.service.InssaitService;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class InssaitController {

	@Autowired
	private InssaitService service;

	public InssaitController() {
		System.out.println("--- InssaitController ---");
	}

	@GetMapping("/getAndSave")
	public void getAndSaveData(String id, String pw, Integer loopNum, Integer targetDate) {
		try {
			service.getAndSaveData(id, pw, loopNum, targetDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

}
