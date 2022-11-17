# Tester Matching Platform - Backend

<br>
<br>

## 소개

### BM?
![소개](https://user-images.githubusercontent.com/63409722/202464271-51c09c5a-8e60-4f3b-9d4a-a7952679a3c8.png)

<br>

### 동작 과정
![기대효과](https://user-images.githubusercontent.com/63409722/202464522-3d56e194-eb4c-44a4-86b5-0a42c6004015.png)

---

<br>
<br>

### BM 이란,

테스터를 필요로 하는 여러 프로그램 제작자, 제작 회사와 테스터간 서로의 니즈를 충족시키기 위해서 개발한 플랫폼입니다.

테스터는 현금화할 수 있는 일관된 보상, Tester를 모집하는 공고와 신청 경쟁률 확인을 지원

테스터를 구하는 사람은 테스터의 이전 테스트 기록, 포인트를 통한 보상 지급, 선정 시 Tester 정보 조희를 제공하고, 보상도 플랫폼을 통해서 제공해줄 수 있습니다.

<br>
<br>

개요는 문서화해서 작성하고 있습니다.

[Notion으로 정리한 개요 문서](https://www.notion.so/kukjun/6378aad79254427b9b95f8a842eb17bc)

---

## 개발 현황

### 요구사항 문서 작성

[요구사항 문서](https://www.notion.so/kukjun/d696548da742483a9dec0fed9951900f)

<br>

### 

### Database 개발

[Database 설계](https://www.notion.so/kukjun/Database-22c5c8876f5b41148bdb63f3493d7dcd)

<br>

### API 문서

[API 문서](https://www.notion.so/kukjun/API-ac017f3405604a1ca63614e471f9db14)

<br>

### Backend 구조 설계 및 개발 진행 방향

Backend 구조는 역할을 각각 맡아서 처리하도록 함.

* NoLogin, Tester API는 `임희영` 작성 진행.
* Maker API는 `이국준` 작성 진행.

API 문서를 분리해서 개발하고, 코드 리뷰 과정을 통해서 서로의 코드의 문제점을 파악하고 개선하도록 진행기로 함.

<br>

### API 문서 내용 개발

1. 1차적으로 API 구현을 완료
* Frontend 연동을 위해, API 동작을 테스트 해 본결과 정상적으로 동작함을 확인함.
2. 해당 API에서 너무 비효율적인 로직 수정 및 프론트 엔드 연동 <- 구현중
* Frontend 연동 진행중
* 너무 많은 DB 접근 발견, 이를 수정

<br>

### 보안 / 인가 절차 적용 - 구현

1. 보안을 위한 JWT 적용
* Spring Security없이, Filter 사용을 통해서 JWT를 적용
2. JWT CORS Filter 오류 해결

<br>

### 추가 구현 사항 - 구현

1. 추가 API 구현
2. 오류 발생한 부분 코드 수정


