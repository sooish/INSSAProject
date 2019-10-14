package inssait.crawling;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import inssait.model.domain.Influencers;

public class Crawling {
	private static Crawling instance = new Crawling();

	public static Crawling getInstance() {
		return instance;
	}

	private static Selenium browser = Selenium.getInstance();
	private static JavascriptExecutor js = ((JavascriptExecutor) Selenium.getDriver());

	// 로그인
	public void login(String id, String pw) {
		browser.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
		browser.sleep(2);
		browser.input("div:nth-child(2) > div > label > input", id);
		browser.input("div:nth-child(3) > div > label > input", pw);
		browser.sleep(2);
		browser.click("div:nth-child(4) > button > div");
		browser.sleep(2);
	}

	// 인플루언서 계정 아이디 추출
	public ArrayList<String> getInfluencerNameList() {
		browser.get("https://www.instagram.com/heartit.kr/");
		browser.sleep(2);
		browser.click("#react-root > section > main > div > header > section > ul > li:nth-child(3) > a");
		browser.sleep(2);
		List<WebElement> list = browser.findAll("div.PZuss > li");
		browser.sleep(3);
		js.executeScript("javascript:window.scrollBy(0,5000)");

		ArrayList<String> influencerNameList = new ArrayList<>();
		int beforeMax = 0;
		while (beforeMax != list.size() - 1) {
			beforeMax = list.size() - 1;
			js.executeScript("arguments[0].scrollIntoView(true);", list.get(list.size() - 1));
			browser.sleep(2);
			list = browser.findAll("div.PZuss > li");
		}
		for (WebElement wel : list) {
			influencerNameList.add(wel.findElement(By.cssSelector("div > div > div:nth-child(2) > div > a")).getText());
		}
		browser.sleep(2);
		return influencerNameList;
	}

	// 인플루언서 계정으로 이동
	public void accessToPage(String id) {
		browser.get("https://www.instagram.com/" + id + "/");
		browser.sleep(2);
	}

	// 날짜 제한에 이용할 값 추출
	public int getLimit() {
		return Integer.parseInt(browser.find("time").getAttribute("datetime").split("T")[0].replaceAll("-", ""));
	}

	// 인플루언서 게시글 url추출
	public ArrayList<String> getUrlList(int loopNum) {
		int count = 0;
		ArrayList<String> urlList = new ArrayList<>();
		for (int i = 1; i <= loopNum; i++) {
			if (i >= 8 && i % 4 == 0) {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				browser.sleep(2);
			}
			for (int j = 1; j <= 3; j++) {
				if (i > 10) {
					if (i % 4 == 3) {
						count = 11;
					} else if (i % 4 == 0) {
						count = 12;
					} else if (i % 4 == 1) {
						count = 13;
					} else if (i % 4 == 2) {
						count = 14;
					}
					urlList.add(browser
							.find("main.SCxLW > div > div._2z6nI > article > div:nth-child(1) > div > div:nth-child("
									+ count + ") > div:nth-child(" + j + ") > a")
							.getAttribute("href"));
				} else {
					urlList.add(browser
							.find("main.SCxLW > div > div._2z6nI > article > div:nth-child(1) > div > div:nth-child("
									+ i + ") > div:nth-child(" + j + ") > a")
							.getAttribute("href"));
				}
			}
		}
		return urlList;
	}
	
}
