# 🔥Afterburner - Backend

> 🔥Afterburner - Backend 프로젝트는 국비 수료생을 위한 커뮤니티인 Afterburner의 백엔드 서버를 구성하기 위하여 설계되었습니다.
> 
<br/>

## **목차**
+ [기술 스택](#기술-스택)
+ [설치 방법](#설치-방법)
+ [기여 방법](#기여-방법)
+ [프로젝트 패키지 구조](#프로젝트-패키지-구조)
+ [코드 컨벤션](#코드-컨벤션)
+ [문의 사항](#문의-사항)

<br/>

## 기술 스택
+ 언어: Java - TEMURINJDK 21
+ 프레임워크: Spring Boot - v3.5.3, Spring Security
+ 데이터베이스: PostgreSQL, JPA (Hibernate)
+ 인증: JSON Web Token (JWT)
+ 빌드 도구: Gradle
+ API 문서화: Springdoc OpenAPI (Swagger UI)
+ 컨테이너: Docker Compose

<br/>

## ✨ 주요 기능
+ **사용자 관리**: JWT 기반의 안전한 회원가입 및 로그인/로그아웃 기능을 제공합니다.
+ **커뮤니티**: 사용자들이 자유롭게 소통할 수 있는 커뮤니티, 공지사항, Q&A 게시판 기능을
      제공합니다.
+ **프로젝트/스터디**: 사용자들이 프로젝트 및 스터디 그룹을 생성하고, 팀원을 모집하고, 관리할 수
      있습니다.
<br/>

## 설치 방법

### **리포지토리 클론**
```bash

$ git clone https://github.com/Afterburner2024/Afterburner-Backend.git

```

<br/>

### **디렉토리 이동**

```bash

cd Afterburner-Backend

```

<br/>

### **의존성 설치**

```bash

# 의존성만 다운로드
./gradlew dependencies

# 또는
gradle dependencies

```

<br/>

### **환경 설정**

`src/main/resources/application.yml` (또는 `.properties`) 파일에 데이터베이스 연결 정보 등 환경에
맞는 설정을 입력합니다.
```spring:
datasource:
    url: jdbc:mysql://localhost:3306/afterburner_db
    username: your_db_username
    password: your_db_password
driver-class-name: com.mysql.cj.jdbc.Driver
jpa:
    hibernate:
    ddl-auto: create
    show-sql: true
```

<br/>
### **애플리케이션 실행**

```bash

# 애플리케이션 실행 (Spring Boot)
./gradlew bootRun

# 애플리케이션 실행 (일반 Java)
./gradlew run

```

<br/>

#### Docker Compose로 실행

`docker-compose.yml` 파일이 프로젝트 루트에 포함되어 있어, Docker만 설치되어 있다면 더 쉽게
실행할 수 있습니다.

```bash
docker-compose up --build
```

## 📖 API 문서
 서버 실행 후, 아래 주소에서 API 문서를 확인하고 테스트할 수 있습니다.
- **Swagger UI**
 ```
http://localhost:8080/swagger-ui/index.html
```

## 프로젝트 패키지 구조
- **패키지 구조**
    - `com.afterbunner.도메인`(기본 패키지)
        - `controller` (API 컨트롤러)
        - `service` (비즈니스 로직)
        - `repository` (데이터 접근)
        - `model` (도메인 모델)
        - `config` (설정 클래스)
        - `exception` (예외 처리)
<br/>

## 기여 방법
### **버전 관리 규칙 (Version Control Guidelines)**

- **브랜치 전략 (Branching Strategy)**:
    
    모든 브랜치 이름은 `afterburner/`로 시작합니다.
    병합이 완료된 브랜치는 삭제하지 않습니다.

    - `main` : 안정적인 릴리스 브랜치
    - `develop` : 기능들이 병합되는 개발 브랜치
    - `feature/{feature-name}` : 새로운 기능을 위한 브랜치
    - `fix/{issue-name}` : 버그 수정을 위한 브랜치


- **커밋 메시지 규칙 (Commit Message Conventions)**: 커밋 메세지의 형식은 [**https://jane-aeiou.tistory.com/93**](https://jane-aeiou.tistory.com/93) 에 의하여 작성한다.
    
    
    | Type 키워드 | 사용 시점 |
    | --- | --- |
    | feat | 새로운 기능 추가 |
    | fix | 버그 수정 |
    | docs | 문서 수정 |
    | style | 코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등)기능 수정이 없는 경우 |
    | design | 사용자 UI 디자인 변경 (CSS 등) |
    | test | 테스트 코드, 리팩토링 테스트 코드 추가 |
    | refactor | 코드 리팩토링 |
    | build | 빌드 파일 수정 |
    | ci | CI 설정 파일 수정 |
    | perf | 성능 개선 |
    | chore | 빌드 업무 수정, 패키지 매니저 수정 (gitignore 수정 등) |
    | rename | 파일 혹은 폴더명을 수정만 한 경우 |
    | remove | 파일을 삭제만 한 경우 |
    
    ### Commit Message 구조
    
    > type(타입) : title(제목)
    > 
    
    ex) feat : 
    

- **병합 및 충돌 해결 (Merging and Conflict Resolution)**:
    - 미정
<br/>

## 코드 컨벤션

### **코딩 스타일 가이드 (Coding Style Guide)**

- 코드스타일 참고 1(https://juneyr.dev/checkstyle)
- 코드스타일 참고 2(https://jojaeng2.tistory.com/39)
- 코드스타일 참고 3(https://yeongchan1228.tistory.com/129)
- 코드스타일 참고 4(https://yeongchan1228.tistory.com/129)
- 코드스타일 참고 5(https://github.com/Programmers-Coach-Coach/Coach-Coach-server/pull/4)

<br/>

## 문의 사항

> 프로젝트와 관련된 문의는 다음을 통해 연락주시기 바랍니다.

토론 등록: [GitHub Discussions](https://github.com/orgs/Afterburner2024/discussions)

<br/>
