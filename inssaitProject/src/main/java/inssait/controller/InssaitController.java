package inssait.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
		service.getAndSaveData(id, pw, loopNum, targetDate);
	}

	@GetMapping("/load")
	public ArrayList<SearchHit[]> loadLocationKeyword() {
		return service.getLocationList();
	}

	@GetMapping("/saveLocation")
	public void saveLocationData(String esId, String addressName, String categoryGroupCode, String categoryGroupName,
			String categoryName, String distance, String id, String phone, String placeName, String placeUrl,
			String roadAddressName, String x, String y) {
		try {
			service.saveLocationData(esId, addressName, categoryGroupCode, categoryGroupName, categoryName, distance,
					id, phone, placeName, placeUrl, roadAddressName, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getLocationInfo")
	public SearchHit[] getLocationInfo() {
		return service.getLocationInfo();
	}

}
