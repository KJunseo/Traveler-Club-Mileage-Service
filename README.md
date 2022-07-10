# 트리플여행자 클럽 마일리지 서비스

## 🛠 기술 스택
- 언어: Java 11
- 프레임워크: Spring Boot 2.7.1, Spring MVC, Spring rest docs
- Database: MySQL 8, Spring Data JPA, flyway
- infra: docker
- 테스트: Junit5, REST Assured

## 💻 개발 환경
MacBook M1 Air

## ▶️ 실행 방법

**docker & docker-compose 가 설치되어 있어야합니다!**

1) `git clone https://github.com/KJunseo/Traveler-Club-Mileage-Service.git`
2) `cd Traveler-Club-Mileage-Service/`
3) `./gradlew clean build`
4) `docker-compose build --pull`
5) `docker-compose up -d` (MySQL 띄우는데 시간이 좀 걸립니다!)

**M1 환경인 경우 docker-compose.yml 파일에서 주석을 풀어줘야합니다!**

4) `vi docker-compose.yml`
5) 4번째 라인 `platform: linux/x86_64` 주석을 풀어주고 저장
6) `docker-compose build --pull`
7) `docker-compose up -d`

### 📚 설명
#### 포인트 적립 API 
리뷰 생성 API(실제로 구현 x) 호출 후 이어서 포인트 적립 API가 호출된다고 생각하고 구현했습니다.(리뷰 생성 API -> 포인트 적립 API)

#### 포인트 수정 API
생성되었던 리뷰 수정 API 이후 포인트 수정 API가 호출된다고 생각하고 구현했습니다. 실제 내용 변경이 필요하여 리뷰 수정 API도 추가로 구현했습니다.(리뷰 수정 API -> 포인트 수정 API)
   
#### 포인트 삭제 API 
포인트를 계산하기 위해서 리뷰 정보가 필요하기 때문에 포인트 삭제 API 호출 이후 리뷰 삭제 API(실제 구현 x)가 호출된다고 생각하고 구현했습니다.(포인트 삭제 API -> 리뷰 삭제 API)

#### 유저 별 포인트 조회
유저의 uuid를 통해 현재 누적 포인트값을 조회할 수 있는 API를 구현했습니다.

#### 포인트 부여 히스토리 관리
포인트 증감시 `PointHistory`라는 테이블에 내역을 저장했습니다. 전체 내역 조회와 유저별 내역 조회 API를 구현했습니다.

#### 기타 내용
- 포인트 적립 기능을 수행하기 위해 기존 데이터가 필요하다고 생각하여 애플리케이션 실행시 `유저`, `장소`, `리뷰` 를 저장했습니다.(실행해보기 위해 해당 더미 데이터를 조회할 수 있는 임시 API를 만들었습니다. `GET /dummy`)

- intellij 사용시 쉽게 실행시켜볼 수 있도록 [http 파일](./http/scenario.http)을 추가했습니다.


### 📝 API 문서
실행 후 http://localhost:8080/docs/api.html

### 🗄 DDL
flyway를 사용하여 db를 관리하였습니다. 버전 변경이 있었어서 [최종 ddl과 index 쿼리문](./src/main/resources/ddl.sql) 링크를 첨부합니다.

### 🔌 종료
1) `docker-compose down`
