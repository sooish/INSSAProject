
# About the project: biz-wise

'핫플레이스'로 떠오르기 시작하는 지역을 소개하는 서비스인 '인싸이트'를 개발한다. 이 서비스에서는 핫플레이스를 소개하되, 현재에 국한하지 않고 앞으로 핫해질 것이라 예상되는 지역들을 발굴하여 제안하는 트렌드 예측 서비스를 지향한다.<br>
<br>
핫플레이스를 정의하기 위해 우선 다방면의 트렌드 세팅에 큰 영향을 미치는 인플루언서들(influencers)을 선정한다. 자신이 사고 입는 것을 잇템으로 만들듯, 스쳐 가는 곳을 핫플레이스로 만드는 인플루언서들의 영향력과 시장 트렌드를 반영하기 위함이다. 이 선정을 위해 트렌드에 민감한 사람들이 주이용자로서 트렌드에 커다란 영향을 미치는 매체로 평가받는 SNS인 인스타그램에 주목하여, 여러 계정 중에서도 패션분야의 트렌드 세터라고 불리는 사람들의 패션 정보를 소개하는 '하트잇(heart.it)'의 계정을 활용하기로 한다. 구체적으로는 하트잇이 팔로우하는 180여명의 팔로잉 계정을 이용, 특정기간 그들이 움직인 동선인 장소태그를 체크하여 장소정보를 크롤링하고 이를 지도 위에 시각화해 핫프레이스 지도를 완성한다. 


# About the project: tech-wise

이 서비스를 기술적으로 구현하기 위해 
  
- 우선 인플루언서들의 장소정보를 Selenium을 통해 크롤링하고, 여기에서 얻은 데이터를 목적에 맞게 분류하여 Elastic Search에 넣는다.
- 카카오 맵 API를 활용하여 지도정보를 가져오고 앤드유저에게 제공할수 있도록 기초적인 지도를 완성한다. 
- 서비스에 가입하는 회원들의 정보를 가입하고 이들에게 서비스를 제공하기 위해 Spring Boot를 활용하여 MVC 패턴을 완성한다.
- 관리자 페이지를 별도로 구현해, 데이터 크롤링과 인플루언서 지도정보 관리, 회원관리, 장소정보 관리를 일원화한다.
- 서비스를 제공하기 위해 웹페이지를 구현한다. 특별히 index.html에는 우리가 제공하는 주요한 장소정보를 시각화하기 위해 자바와 자바스크립트를 통해 워드클라우드로 만든다.


# Built with

```
* RDBMS : Oracle
* NoSQL : Elastic Search7.1.1 
* Back End Development Languages : Java / SQL
* Front End Development Language : Java Script / HTML / CSS
* Libraries : Maven(Lombok, Jsoup1.12.1) / Google Chart / Any Chart / Kakao Map api
* Framework : Bootstrap / Spring Boot2.19
* Tools : Eclipse / Visual Studio Code / Kibana / PostMan / GitHub / SourceTree / ER Master / Oven
* Additional technical set : Axios
```

# How to run it
1. Eclipse에서 Spring boot 파일을 가동한다.
2. 오라클 DB, 엘라스틱 서치, 키바나를 켠 상태로, html 파일을 실행하여 인스타그램 데이터 크롤링을 수행한다. 
3. html 파일을 실행하여 지도 정보를 가져온다.
4. 

# Authors
* **강민웅**:  [강민웅의 깃헙](https://github.com/happymwkang)
* **김수경**:  [김수경의 깃헙](https://github.com/sooish)
* **김웅태**:  [김웅태의 깃헙](https://github.com/angle2v)
* **김종성**:  [김종성의 깃헙](https://github.com/SEJSCloud)
* **최성국**:  [최성국의 깃헙](https://github.com/SunggookCHOI)


# Images

<div>
<img src="https://user-images.githubusercontent.com/51253930/67360627-67b58580-f5a1-11e9-9bfe-2705c300a55b.png" width="90%"></img>
<br>
<hr>
<br>
<img src="https://user-images.githubusercontent.com/51253930/67360936-2d001d00-f5a2-11e9-8be2-beb85bdbe5b7.png" width="90%"></img> 
<br>
<hr>
<br>
<img src="https://user-images.githubusercontent.com/51253930/67360632-6a17df80-f5a1-11e9-9b07-c9fb9f426c55.png" width="90%"></img> 
</div>
 
 

