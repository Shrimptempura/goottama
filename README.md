# 인테리어 커머스 통합 플랫폼
> 다형성 기반 공통 테이블(`post`, `review`, `comment`, `file`, `report`)을 중심으로  
> 도메인 간 경계를 재정의하고 구조적 일관성을 확보한 인테리어 커뮤니티 웹 서비스


---

## 프로젝트 개요
- **유형:** 팀 프로젝트
- **기간:** 2025.07.11 ~ 2025.08.14  
- **인원:** 백엔드 5명
- **팀 구성:** 회원 / 커뮤니티 / 인테리어 / 쇼핑 / 관리자
- **역할:** 팀장 / 업체·게시글·리뷰 도메인 개발 / 공통·다형 구조 및 파일 보상 로직 설계


---

## 기술 스택
| 구분 | 기술 |
|------|------|
| **Backend** | Java 17 · Spring Boot 3.5.3 · MyBatis 3.0.3 |
| **Frontend** | JSP · HTML · CSS |
| **DB** | MariaDB 11.8 (Driver 3.3.3) |
| **Build/IDE** | Maven · IntelliJ IDEA 2024.3 · STS 4.25 |
| **Test** | JUnit 5 · Mockito 5.14.2 |
| **Infra/DevOps** | AWS EC2 · Jenkins · GitHub Actions |
| **Security** | Spring Security · AOP Logging |


---

## 패키징 / 배포
- Jenkins + GitHub Actions 기반 CI/CD 실습
- AWS EC2 환경에 자동 배포 파이프라인 구축 및 동작 검증 경험(main 푸시 시 자동 반영)
- WAR 패키징 사용


---

## 프로젝트 요약
인테리어 업체와 사용자가 연결되는 커뮤니티형 웹 서비스입니다.
공통 테이블(`post`, `review`, `comment`, `file`, `report`)에 다형성 구조(`target_type` + `target_id`)를 적용하여
게시글·리뷰·댓글 등 다양한 도메인을 통합 관리하고, 도메인 간 책임 경계를 명확히 함

## 핵심 구현
### 공통 테이블 기반 다형성 구조 설계  
게시글·리뷰·댓글·파일 등 중복 테이블을 **다형성(`target_type`, `target_id`) 기반 공통 구조**로 통합. 초기에 추적성과 관리 효율을 높이려 했지만, 도메인별 필드 의미 차이로 **공통 계층의 책임 경계가 모호**해짐. 공통 Mapper에 유사 쿼리와 네이밍 존재하고 공통 코드 수정 시 도메인 전체에 영향이 발생하는 문제 확인

이에 공통 테이블에는 `id`, `target_id`, `target_type`, `created_at` 등 **식별/메타 역할만 유지**하고, 각 도메인의 속성과 로직은 개별 Mapper·Service 단위로 분리함. 다형성 구조는 유지하되, 공통 계층을 얇게 만들어 **중복 제거와 유지보수성**을 모두 확보. 또한 `target_type/id` 조합으로 도메인 추적으로 **관리자 로그·신고 집계 등 통합 관리**가 가능함
<br>
<br>

### 성능보다 유지보수를 택한 조회 구조 설계
게시글 목록과 대표 썸네일을 함께 조회하는 과정에서, 향후 구조 확장이 예정되어 있어 **변경에 유연한 설계 방향**을 우선함

복합 쿼리를 사용하면 순간 성능은 더 좋지만, 스키마 변경 시 전체 SQL을 수정해야 하는 부담이 발생. 이에 **IN 배치 방식**으로 전환해 게시글 목록을 한 번에 조회한 뒤, ID 리스트 기반으로 썸네일을 일괄 조회하도록 개선.

결과적으로 **DB 왕복 횟수를 N건 -> 2회(목록 1 + 썸네일 1)** 로 줄이며 **성능은 충분히 확보하면서도**, 확정되지 않은 개발 구조 내에서 리스크를 최소화할 수 있는 **유지보수성과 확장성 중심의 구조적 선택**을 택함.

이 방식은 다형성(`target_type`, `target_id`) 구조와도 호환되어, 향후 게시글 외 리뷰·프로젝트 등 다른 도메인으로의 확장성 역시 보장됨
<br>
<br>

### 테스트 기반 품질 확보
Mapper·Service 중심으로 테스트를 작성해 주요 로직의 안정성을 검증함. **총 130개의 테스트 케이스**를 통해 리뷰 평균 계산 시 발생한 0건 처리 오류를 **테스트로 조기 발견·수정**하였고, Mock 기반 검증으로 입력·권한·예외·파일 정책 등 핵심 흐름을 정검함.

**JaCoCo 커버리지:** 라인 56% / 브랜치 52%  
**CI 파이프라인:** Jenkins 기본 설정으로 테스트 통과 시에만 배포가 진행되어, 결과적으로 배포 안정성을 확보함.
<br>
<br>

---

## 담당 기능 요약 
|기능|설명| 
|--|--| 
|**공통 구조 설계**| `post`/`review`/`comment`/`file`/`report` 테이블 기반 다형성 매핑 (`target_type`, `target_id`) 설계 | 
|**업체 관리**| 상세·위치·기본정보 3단계 등록 트랜잭션 / 썸네일 업로드 및 `soft delete` | 
|**게시글**| 상위 post + 하위 company_post 구조 / 썸네일 자동 지정 / 수정·삭제 권한 검증(실삭제) | 
|**리뷰/평점**| 상하위 review 구조 / 평균·합계 자동 갱신 / 삭제 시 점수 재계산 및 평균 보정 | 
|**댓글**| 단일 테이블 계층형 구조 / `soft delete`(작성자만 수정·삭제 가능) |
<br>
<br>

---

## 시스템 구조(클래스 다이어그램)
> **common**
```mermaid
classDiagram
direction LR

class PostService
class PostServiceImpl
class PostDao

class ReviewService
class ReviewServiceImpl
class ReviewDao

class FileService
class FileServiceImpl
class FileDao

%% Impl -> Interface
PostServiceImpl ..|> PostService
ReviewServiceImpl ..|> ReviewService
FileServiceImpl ..|> FileService

%% Impl -> DAO (persistence)
PostServiceImpl ..> PostDao : persistence
ReviewServiceImpl ..> ReviewDao : persistence
FileServiceImpl ..> FileDao : persistence
```

<br>
<br>

> **interior**
```mermaid
classDiagram
direction LR
class CompanyController
class CompanyPostController
class CompanyReviewController
class CompanyService
class CompanyServiceImpl
class CompanyPostService
class CompanyPostServiceImpl
class CompanyReviewService
class CompanyReviewServiceImpl
class CompanyDao
class CompanyPostDao
class CompanyReviewDao

%% Controller -> Service
CompanyController ..> CompanyService : calls
CompanyController ..> CompanyPostService : calls
CompanyController ..> CompanyReviewService : calls
CompanyPostController ..> CompanyPostService : calls
CompanyReviewController ..> CompanyReviewService : calls

%% Impl -> Interface
CompanyServiceImpl ..|> CompanyService
CompanyPostServiceImpl ..|> CompanyPostService
CompanyReviewServiceImpl ..|> CompanyReviewService

%% Impl -> DAO
CompanyServiceImpl ..> CompanyDao : persistence
CompanyPostServiceImpl ..> CompanyPostDao : persistence
CompanyReviewServiceImpl ..> CompanyReviewDao : persistence
```
> 두 다이어그램은 `target_type` / `target_id` 다형성 키로 연결됨

---

## ERD
> 인테리어 도메인 + 공통 테이블만 올린 사진
<img width="1850" height="1322" alt="proj_1_readMe (1)" src="https://github.com/user-attachments/assets/1fe500e1-74ea-4b1a-b48e-b79fc5a153f1" />




---


## 시퀀스 다이어그램
### 업체 등록(미들)
```mermaid
sequenceDiagram
autonumber
actor User
participant Ctrl as CompanyController
participant S as CompanyServiceImpl
participant Auth as CompanyAuthService
participant DAO as CompanyDao
participant FS as FileService

User->>Ctrl: POST /companies(createDto, locationDto, file)
Ctrl->>S: createCompany(createDto, locationDto, file)

S->>Auth: getLoginUserId()
Auth-->>S: userId

S->>S: validateCompanyNameDuplication(name)
S->>DAO: isDuplicateCompanyName(name)
DAO-->>S: exists?
alt 이름 중복
  S-->>Ctrl: IllegalArgumentException("이미 등록된 이름")
else 계속 진행
  Note over S: 중복 아님 → 계속
end

S->>DAO: insertCompanyDetail(createDto)
DAO-->>S: companyDetailId
S->>DAO: insertLocation(locationDto)
DAO-->>S: locationId
S->>DAO: insertCompany({userId, companyDetailId, locationId})
DAO-->>S: companyId

note over S,FS: 이미지 1장 필수(썸네일)
S->>FS: saveFile(INTERIOR, companyId, file, true)

S-->>Ctrl: success

opt any error
  note right of S: best-effort 정리
  S->>FS: deleteThumbnail(INTERIOR, companyId)
  S-->>Ctrl: IllegalStateException("업체 등록 실패")
end
```

### 리뷰 생성(미들)
```mermaid
sequenceDiagram
autonumber
actor User
participant C as CompanyReviewController
participant S as CompanyReviewServiceImpl
participant A as CompanyAuthService
participant RS as ReviewService
participant D as CompanyReviewDao
participant F as FileService

User->>C: POST /reviews(createDto, files)
C->>S: createReview(createDto, files)

S->>A: getLoginUserId()
A-->>S: userId

S->>S: validate(companyId)
alt companyId == null
  S-->>C: IllegalArgumentException("companyId 없음")
else 계속 진행
  Note over S: companyId OK
end

S->>S: makePolyReview(createDto)
S->>RS: insertPolyReview(ReviewDto[target=INTERIOR_REVIEW, targetId=companyId])
RS-->>S: reviewId
S->>S: createDto.setReviewId(reviewId)

S->>D: insertCompanyReview(createDto)
D-->>S: inserted(1)

note over S,F: 이미지 저장(최소 1장, 첫 장 썸네일)
S->>F: saveFile(INTERIOR_REVIEW, reviewId, files[0], true)
opt files[1..n-1]
  loop 나머지 이미지
    S->>F: saveFile(INTERIOR_REVIEW, reviewId, file[i], false)
  end
end

S->>D: isExistScoreTable(companyId)
alt 첫 리뷰 없음
  S->>D: createScoreTable(createDto)
else 기존 리뷰 있음
  S->>D: addScoreOnCreate(createDto)
  S->>D: averageOnCreate(createDto)
end

S-->>C: reviewId

opt any error
  note right of S: best-effort 파일 정리
  S->>F: deleteAllByTargetId(INTERIOR_REVIEW, reviewId)
  S-->>C: IllegalStateException("리뷰 생성 실패")
end

```

---

## 주요 화면
[업체페이지]  
<img width="1868" height="907" alt="업체상세_게시글" src="https://github.com/user-attachments/assets/9e60fb1d-61ac-42d1-a5f9-a677853b9762" />  
[업체게시글]  
<img width="1899" height="936" alt="업체게시글" src="https://github.com/user-attachments/assets/201181bb-e33c-4063-9516-af6a937417d3" />  
[리뷰작성중]  
<img width="1874" height="941" alt="리뷰작성중" src="https://github.com/user-attachments/assets/8842c714-93dc-4b6e-8121-8fa19f6c3488" />  
[리뷰페이지]  
<img width="1875" height="935" alt="업체페이지_리뷰" src="https://github.com/user-attachments/assets/3c8292b7-f4c7-4562-a767-67ab26e8e8ec" />  

<br><br>

---

## 개선 제안 및 향후 계획
- **파일 업로드 3단계화:** `temp -> linked -> final` 단계로 분리하여 트랜잭션 병목/롤백 리스크 완화 
- **rootId 기반 단일 트랜잭션:** `rootId`(UUID/상위 PK)로, 상·하위을 한 트랜잭션에서 일괄 처리, 삭제는 `ON DELETE CASCADE` 또는 일괄 플래그로 단순화
- **리뷰 남용 방지:** 동일 사용자·업체 기준 24시간 작성 제한(최대 24시간 3회)
- **에러 응답 표준화:** `@ControllerAdvice`로 예외·HTTP 상태/에러코드 매핑, `code/message/traceId`를 포함한 공통 에러 스키마 적용

---

## Links
**팀 ERD:** https://www.erdcloud.com/d/Rnc9wsvt2D2jJnKGg




