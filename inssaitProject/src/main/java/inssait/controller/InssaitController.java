package inssait.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import inssait.model.dao.InfluencersRepository;
import inssait.model.dao.MembersRepository;

@RestController
public class InssaitController {
	@Autowired
	private InfluencersRepository ifRepo;
	@Autowired
	private MembersRepository mRepo;
	public InssaitController(){
		System.out.println("--- InssaitController ---");
	}
}
