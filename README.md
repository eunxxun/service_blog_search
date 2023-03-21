# 블로그 검색 서비스

## 실행 방법
- git clone https://github.com/eunxxun/service_blog_search.git
- ./gradlew clean build
- java -jar build/libs/service_blog_search-0.0.1-SNAPSHOT.jar

### 프로젝트 환경
- Java17
- Spring Boot
- Gradle
- h2, JPA
- Redis(인기검색어 목록 조회시 사용)

### 빌드 결과물 링크(service_blog_search-0.0.1-SNAPSHOT.jar)
https://drive.google.com/file/d/1rG2TUzvNCoAyTtke8rLI_6cHL0kiA2kj/view?usp=sharing

## 문제 해결 전략

### 키워드로 블로그 검색
- 키워드를 통해 블로그 검색, Sorting기능 지원, Pagination 형태로 제공
  - 검색 소스는 카카오 블로그 검색 API를 사용했습니다.
  - Spring에서 제공하는 @Validated를 이용하여 입력 파라미터 유효성 검증을 했습니다.
  - 로그 데이터로 사용자의 ip와 키워드, 검색일을 JPA를 활용하여 h2 디비에 저장하였습니다.
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려하여 확장성 있는 API 설계
  - 인터페이스 기반 설계
    - BlogSearchApi 인터페이스를 정의하고 각각의 출처로부터 데이터를 가져올 수 있도록 했습니다.
    - 새로운 검색 API가 추가되더라도 해당 인터페이스를 구현하여 새로운 구현체를 만들어 사용할 수 있습니다. (KakaoSearchApi, NaverSearchApi)
    - 추상화 계층을 사용하여 코드 내 변경사항 및 유연성, 확장 가능성을 높였습니다.

### 인기검색어 목록 조회
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드와 검색된 횟수 제공
  - size를 parameter로 받아 1~10개의 검색 키워드가 제공될 수 있게 했습니다.
- 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현
  - 인기검색어 목록을 조회하기 위한 데이터는 Redis 해시에 저장하였습니다.
  - Redis를 이용하여 대용량 데이터에서 많이 저장된 단어 10개, 검색횟수를 뽑아내기 위해 Redis의 Sorted Set 자료구조를 이용했습니다.
  - Sorted Set은 각 원소마다 score를 가지고 있으므로 검색횟수로 활용했습니다.

### Back-end 추가 요건
- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현(예시. 키워드 별로 검색된 횟수의 정확도)
  - 검색횟수 증가시 동시성 이슈가 발생할 수 있는데, Redis의 increment()는 내부적으로 동기처리를 하기 때문에 사용했습니다.
  - redisTemplate.setEnableTransactionSupport(true) 로 Redis의 트랜잭션을 사용하여 횟수의 정확도를 높였습니다.
- 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공

## API 명세
### 1. 키워드로 블로그 검색 API
#### Request
- 사용자가 지정한 키워드로 블로그 게시물을 검색 할 수 있습니다.
  ```
  GET http://localhost:8080/api/blog/search?keyword=아이폰&sort=REC&page=4&size=5
  ```
  |파라미터|타입|필수여부|설명|
    |------|:---:|:---:|---|
  |keyword|String|Y|검색할 키워드|
  |sort|String|N|정렬 방식, ACC(정확도순) 또는 REC(최신순), 기본 값 ACC|
  |page|Integer|N|결과 페이지 번호, 1~50 사이의 값, 기본 값 1|
  |size|Integer|N|한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10|
#### Response
- 요청에 대한 응답으로 검색된 블로그 게시물 목록과 페이징 정보를 포함한 JSON 객체를 반환합니다.
  ```
    {
      "meta": {
        "is_end": false,
        "pageable_count": 799,
        "total_count": 5169802
      },
      "blogResultList": 
      [
        {
          "title": "현대카드 애플페이 추가방법 (<b>아이폰</b>, 애플워치) 및 사용후기",
          "contents": "출장관계로 아침에 일찍 이동하는 길에 애플페이에 추가를 해보았습니다. 추가하는 방법은 생각보다 쉬웠습니다. 실물카드 없이 현대카드 앱만 있으면 <b>아이폰</b>에 애플페이 추가를 할 수 있습니다. 저는 현대카드 앱에서 사용하고 있는 현대카드를 애플페이에 추가하지 않고, 애플 지갑 앱에서 추가를 했습니다. 일본에서...",
          "url": "http://justin486.tistory.com/262",
          "blogname": "저스틴 놀이터",
          "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/4hv1IuswPqg",
          "datetime": "2023-03-21T20:22:13.000+09:00"
        },
        {
          "title": "<b>아이폰</b> 애플페이 현대가드 쉽고 간단하게 등록했다! (사용처가 많아지길)",
          "contents": "안녕하세요~ 딩딩입니다. 오늘은 애플페이를 처음으로 등록했어요. 사실 애플페이 사용처가 많지는 않죠;; 시리에게 물어봤죠; 애플 공식 홈페이지에서 시키는데요... 시리야 애플페이 사용할 수 있는 카페 알려죠~~ 그 결과는?? 네;; 저는 제주도 살고 있는데;;;; 엄청 가까운 ㅋㅋㅋㅋ 카페를 알려주네요;; 시리야 너...",
          "url": "http://1dingding.tistory.com/643",
          "blogname": "제주도여행과 카페투어",
          "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/FK9ZsjmLDRL",
          "datetime": "2023-03-21T20:19:41.000+09:00"
        },
        {
          "title": "실패없이 노트북 사는 합리적인 방법. 가성비좋은 대학생 노트북 추천",
          "contents": "삼성 갤럭시북 노트북이 아닌 애플사의 맥북이나 아이맥으로 갈아탈 경우, 가장 큰 변화는 바로 운영체제(OS)이다. 쉽게말해 삼성 갤럭시 핸드폰에서 애플의 <b>아이폰</b>으로 갈아탈 경우 느끼는 변화와 비슷하다. WINDOW OS -&gt; MAC OS 로 바뀔 경우 적응하지 못하면 어쩌지 이전에 쓰던 윈도우 운영체제가 더 편해서 노트북...",
          "url": "http://crushonming2.tistory.com/18",
          "blogname": "쑥쑥 크는 지식성장 블로그",
          "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/ADx3FDv2Lwc",
          "datetime": "2023-03-21T20:15:25.000+09:00"
        },
        {
          "title": "애플페이 카드 등록 방법 안내",
          "contents": "나오기도 했지만 결국에는 도입되지 못했던 애플페이가 이제 정말로 한국에 왔습니다. 참고로 애플페이는 2014년부터 미국에서 시작된 간편 결제 서비스로 <b>아이폰</b>6, 애플워치 1세대와 함께 발표된 서비스입니다. 한국 도입까지 무려 9년 가까이 걸렸네요. 2023년 3월 현재 아직까지는 현대카드만 사용 가능합니다. 차후...",
          "url": "http://sdjoon.tistory.com/319",
          "blogname": "리뷰 농장",
          "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/DFFtRUyYcg",
          "datetime": "2023-03-21T20:15:00.000+09:00"
        },
        {
          "title": "애플페이 등록부터 사용법까지, 설치 오류 문제해결, 사용 가능한 곳",
          "contents": "오늘부터 애플페이 이용이 가능해졌습니다. 일단 <b>아이폰</b> 카드케이스부터 그냥 케이스로 다시 바꿔야겠습니다. 그동안 카드 넣어 다니느라 뚱뚱한 <b>아이폰</b>에 무선충전도 불편했었는데 정말 희소식이네요. 그럼 설치 방법부터 사용방법, 사용가능 한 곳까지 모두 알려드려 볼게요. 애플페이 등록 애플페이를 사용하기...",
          "url": "http://rich.sonenm.com/14",
          "blogname": "부자되는시크릿",
          "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/LdO6bkRnzyF",
          "datetime": "2023-03-21T20:12:34.000+09:00"
        }
      ]
    }
  ```
  meta

  |JSON 속성|타입|설명|
    |------|:---:|---|
  |total_count|Integer|검색된 문서 수|
  |pageable_count|Integer|total_count 중 노출 가능 문서 수|
  |is_end|Boolean|현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음|
  blogResultList

  |JSON 속성|타입|설명|
    |------|:---:|---|
  |title|String|블로그 글 제목|
  |contents|String|블로그 글 요약|
  |url|String|블로그 글 URL|
  |blogname|String|블로그 이름|
  |thumbnail|String|검색 시스템에서 추출한 대표 미리보기 이미지 URL|
  |datetime|Datetime|블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]|

### 2. 인기검색어 목록 조회 API
#### Request
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드와 검색 횟수를 조회합니다.
  ```
  GET http://localhost:8080/api/blog/popular-keyword?size=8
  ```

  |파라미터|타입|필수여부|설명|
    |------|:---:|:---:|---|
  |size|Integer|N|인기검색어 노출할 갯수 1~10 사이의 값, 기본 값 10|
#### Response
- 요청에 대한 응답으로 상위 10개의 인기 검색어와 검색 횟수 목록을 반환합니다.
  ```
  [
    {
      "keyword": "애플페이 사용처",
      "count": 13
    },
    {
      "keyword": "애플워치 사용법",
      "count": 7
    },
    {
      "keyword": "아이폰",
      "count": 6
    },
    {
      "keyword": "텀블러 추천",
      "count": 4
    },
    {
      "keyword": "맥북 M1",
      "count": 4
    },
    {
      "keyword": "test",
      "count": 3
    },
    {
      "keyword": "스탠드조명추천",
      "count": 2
    },
    {
      "keyword": "Nike",
      "count": 2
    }
  ]
  ```

  |JSON 속성|타입|설명|
    |------|:---:|---|
  |keyword|String|인기 검색어|
  |count|Integer|검색 횟수|

## 개선할 점
- 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공하는 부분
  - Circuit breaker 패턴을 이용하면 안정성이 더 높아졌을 것이나 구현하지 못했습니다.
- Redis 데이터는 휘발성이므로 일배치로 RDBMS에 저장한다면 더 안정성있는 서비스를 제공할 수 있을 것 같습니다.