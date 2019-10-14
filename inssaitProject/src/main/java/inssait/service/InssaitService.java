package inssait.service;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import inssait.crawling.Crawling;
import inssait.crawling.Selenium;
import inssait.model.dao.ElasticSearch;
import inssait.model.dao.InfluencersRepository;
import inssait.model.dao.MembersRepository;
import inssait.model.domain.Influencers;

@Service
public class InssaitService {
	@Autowired
	private static InfluencersRepository ifRepo;
	@Autowired
	private MembersRepository mRepo;

	// 싱글톤 코드 없애고 autowired 적용해야할것 같습니다
	private static Selenium browser = Selenium.getInstance();
	private static ElasticSearch es = ElasticSearch.getInstance();
	private static Crawling crawl = Crawling.getInstance();

	public static void getAndSaveData(String id, String pw, int loopNum, int targetDate) {
		// 로그인
		crawl.login(id, pw);

		// 인플루언서 아이디 리스트 추출
		ArrayList<String> influencerNameList = crawl.getInfluencerNameList();
		for (int i = 1; i < influencerNameList.size(); i++) {

			// 인플루언서 계정 페이지로 이동
			crawl.accessToPage(influencerNameList.get(i));
			List<WebElement> numbers = browser.findAll("span.g47SY ");
			ifRepo.save(new Influencers(influencerNameList.get(i),
					Integer.parseInt(numbers.get(0).getText().replace(",", "")),
					Integer.parseInt(numbers.get(1).getAttribute("title").replace(",", "")),
					Integer.parseInt(numbers.get(2).getText().replace(",", ""))));
			// 인플루언서 게시글 url주소들 추출
			ArrayList<String> urlList = crawl.getUrlList(loopNum);
			System.out.println(urlList);

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
						System.out.println(influencerNameList.get(i));
						System.out.println(hashTagToString);
						System.out.println(browser.find("time").getAttribute("datetime").split("T")[0]);
						System.out.println(browser.find("div.JF9hh > a").getText());

						// ElasticSearch에 인플루언서 계정 아이디, 해쉬태그, 날짜, 장소태그 저장
						es.coreInfoSaveToES(influencerNameList.get(i), hashTagToString,
								browser.find("time").getAttribute("datetime").split("T")[0],
								browser.find("div.JF9hh > a").getText(), i);
						browser.sleep(2);
					} else {
						break;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		getAndSaveData("apphj@naver.com", "rktbless", 20, 20190901);
	}

}
