package inssait.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.SearchHit;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import inssait.crawling.Crawling;
import inssait.crawling.Selenium;
import inssait.model.dao.MapDetailRepository;
import inssait.model.dao.InfluencersRepository;
import inssait.model.dao.MembersRepository;
import inssait.model.domain.Influencers;
import inssait.model.domain.MapDetail;

@Service
public class InssaitService {
	@Autowired
	private InfluencersRepository ifRepo;
	@Autowired
	private MembersRepository mRepo;
	
	private static MapDetailRepository es = MapDetailRepository.getInstance();

	public void getAndSaveData(String id, String pw, Integer loopNum, Integer targetDate) throws Exception {
		Selenium browser = Selenium.getInstance();
		Crawling crawl = Crawling.getInstance();
		int num = 0;
		// 로그인
		crawl.login(id, pw);
		// 인플루언서 아이디 리스트 추출
		ArrayList<String> influencerNameList = crawl.getInfluencerNameList();
		influencerNameList.remove("hypebeast");
		influencerNameList.remove("dispatch_style");
		influencerNameList.remove("moon_choi_studio");
		for (int i = 1; i < influencerNameList.size(); i++) {
			try {

				// 인플루언서 계정 페이지로 이동
				crawl.accessToPage(influencerNameList.get(i));
				List<WebElement> numbers = browser.findAll("span.g47SY ");
				ifRepo.save(new Influencers(influencerNameList.get(i),
						Integer.parseInt(numbers.get(0).getText().replace(",", "")),
						Integer.parseInt(numbers.get(1).getAttribute("title").replace(",", "")),
						Integer.parseInt(numbers.get(2).getText().replace(",", ""))));
				// 인플루언서 게시글 url주소들 추출
				ArrayList<String> urlList = crawl.getUrlList(loopNum);

				for (int j = 0; j < urlList.size(); j++) {
					// 각 게시글로 이동

					browser.get(urlList.get(j));
					try {
						// 게시글이 지정 날짜보다 최신일 경우에만
						if (crawl.getLimit() >= targetDate) {

							// 해쉬태그 추출
							List<WebElement> hashTags = browser.findAll(".C4VMK > span > a");

							// 추출한 해쉬태그 스트링 하나에 붙여넣기
							String hashTagToString = "";
							if (hashTags.size() != 0) {
								for (WebElement hashTag : hashTags) {
									if (!hashTag.getText().contains("@") && !hashTag.getText().equals(null)) {
										hashTagToString += hashTag.getText();
									}
								}
							}
							List<WebElement> places = browser.findAll("div.JF9hh > a");
							String placeToString = "";
							if (places.size() != 0) {
								for (WebElement place : places) {
									if (!place.getText().contains("@") && !place.getText().equals(null)) {
										placeToString += place.getText();
									}
								}
							}
							System.out.println(influencerNameList.get(i));
							System.out.println(hashTagToString);
							System.out.println(browser.find("time").getAttribute("datetime").split("T")[0]);
							System.out.println(placeToString);
							// ElasticSearch에 인플루언서 계정 아이디, 해쉬태그, 날짜, 장소태그 저장
//							influencerNameList.get(i), hashTagToString,
//							browser.find("time").getAttribute("datetime").split("T")[0], placeToString
							es.coreInfoSaveToES(
									new MapDetail(influencerNameList.get(i), hashTagToString,
											browser.find("time").getAttribute("datetime").split("T")[0], placeToString),++num);
							browser.sleep(2);
						} else {
							break;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		browser.close();
	}

	public SearchHit[] getLocationList() throws IOException {
		return es.getLocationList();
	}

	public void saveLocationData(MapDetail mapDetail) throws IOException {
		es.saveLocationData(new MapDetail(mapDetail.getEsId(), mapDetail.getAddressName(), mapDetail.getCategoryGroupCode(), mapDetail.getCategoryGroupName(), mapDetail.getCategoryName(), mapDetail.getDistance(), mapDetail.getId(), mapDetail.getPhone(),
				mapDetail.getPlaceName(), mapDetail.getPlaceUrl(), mapDetail.getRoadAddressName(), mapDetail.getX(), mapDetail.getY()));
	}

	public SearchHit[] getLocationInfo() throws IOException {
		return es.getLocationInfo();
	}

}
