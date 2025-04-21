# 📅 Calendar API 프로젝트

## 📝 프로젝트 개요
본 프로젝트는 일정 관리 기능을 제공하는 Calendar API를 Java 기반 Spring Boot 3 환경에서 구현한 백엔드 과제입니다.
사용자는 일정을 등록하고, 수정 및 삭제하며, 다른 사용자와 일정을 공유할 수 있습니다.

## ⚙️ 사용 기술 스택
|구분|기술|
|:---:|:---:|
|Language|Java 17|
|Framework|Spring Boot 3.x|
|ORM|MyBatis|
|DB|MySQL|
|API Docs|Swagger (springdoc-openapi)|
|Build Tool|Gradle|

## 📦 기능 요약
### ✅ 기본 기능 (CRUD)
* 일정 등록
* 일정 조회
    * 일 단위 조회
    * 월 단위 조회
    * 상세 조회
* 일정 수정
* 일정 삭제

### ➕ 추가 기능
* 일정 공유 기능 (개별 사용자 대상)
* 일정 색상/범주 지정
* 일정 공개/비공개 설정

### ➕ Todo
* 국가 공휴일, 기념일
* 일정 반복 설정 (Daily, Weekly, Monthly, Yearly)
* 일정 알림 설정 (팝업/메일, 알림 시간 선택)


## 🚀 소스 빌드 및 실행 방법
### 소스 빌드
```bash
# 프로젝트 디렉토리 진입 후
./gradlew clean build
```

### 실행 방법
```bash
# 실행
java -jar -DDB_USER=username -DDB_PASS=password build/libs/calendar-0.0.1-SNAPSHOT.jar
```

## 📦 주요 라이브러리 및 사용 이유
|     라이브러리     |                              설명                               |                     사용이유                      |
|:---:|:---:|:---:|
|MyBatis|   SQL을 XML 또는 Annotation으로 관리하며 동적 SQL 작성이 가능한 퍼시스턴스 프레임워크    | 복잡한 조건 및 JOIN 쿼리 작성에 유리하며, SQL 제어가 필요한 부분에 사용 |
|  JSON.simple  |           JSON 데이터를 파싱하고 Java 객체로 변환할 수 있는 경량 라이브러리           |        공공데이터 API에서 제공하는 JSON 응답 파싱에 사용        |
|Spring Context Scheduling (@Scheduled)|정기적인 작업을 스케줄링하여 자동 실행할 수 있게 해주는 기능|매월 1일 공휴일 정보 자동 저장을 위한 배치 처리|

📄 [📥 Calendar_Dump.sql 다운로드 (Google Drive)](https://drive.google.com/file/d/18gzKOyLzJYT5COhniRMg9JWgooctNddE/view?usp=drive_link)