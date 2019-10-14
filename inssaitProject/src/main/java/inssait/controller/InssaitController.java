package inssait.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import inssait.service.InssaitService;

@RestController
public class InssaitController {
	
	@Autowired
	private InssaitService service;
	
	public InssaitController(){
		System.out.println("--- InssaitController ---");
	}
	
	@GetMapping("/getAndSave")
	public void getAndSaveData(String id, String pw, Integer loopNum, Integer targetDate) {
		service.getAndSaveData(id, pw, loopNum, targetDate);
	}
	
}
