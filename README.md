# 🛍️ Coreflowshop Web Project

의류 쇼핑몰 사용자 & 관리자 통합 웹 서비스

---

## 📖 프로젝트 소개

Coreflowshop은 효율적인 온라인 쇼핑 경험과 관리자 중심의 운영 편의성을 목표로 설계된 웹 쇼핑몰 프로젝트입니다. 사용자 친화적인 인터페이스와 통합 관리 시스템을 통해 일반 사용자와 관리자가 모두 쉽게 사용할 수 있도록 구현되었습니다.

---

## 🚀 배포 URL

🔗 [사용자페이지 바로가기](https://coreflowpersonal-production.up.railway.app)
🔗 [관리자페이지 바로가기](https://coreflowpersonal-production.up.railway.app/admin/)

---

## 🛠️ 기술 스택

| 구분 | 사용 기술 |
| --- | --- |
| **Frontend** | HTML, CSS, JavaScript, jQuery, Bootstrap, Thymeleaf |
| **Backend** | Spring Boot, MyBatis, Java |
| **Database** | MySQL (AWS RDS) |
| **API / 외부 연동** | Kakao Login API, KakaoPay API, Daum Postcode API, SMTP, Gemini API |
| **Server / 배포** | Railway, GitHub Actions (CI/CD) |
| **Tool** | SpringToolSuite, DBeaver, GitHub |

---

## ✨ 주요 기능

### 👗 사용자 페이지

| 기능 | 설명 |
| --- | --- |
| 🔐 **회원가입 / 로그인** | 이메일 인증, 자동 로그인, 카카오 소셜 로그인 |
| 🛍️ **상품 페이지** | 카테고리별 상품 목록, 장바구니, 구매 |
| 💳 **카카오페이 결제** | 카카오페이 간편 결제 및 결제 내역 이메일 발송 |
| ✍️ **리뷰 작성** | 구매 상품 후기 작성 |
| 🤖 **챗봇** | Gemini API 기반 FAQ 자동 응답 |
| 👤 **마이페이지** | 회원정보 수정, 주문 관리, 회원탈퇴 |

### 🧭 관리자 페이지

| 기능 | 설명 |
| --- | --- |
| 📊 **대시보드** | 회원 수, 주문 수, 매출 통계 요약 |
| 👥 **회원 관리** | 회원 목록 검색, 수정, 삭제 |
| 👗 **상품 관리** | 상품 등록, 수정, 삭제 |
| 📦 **주문 관리** | 주문 상세 내역 및 상태 변경 |
| 💬 **리뷰 관리** | 리뷰 답변 및 삭제 |
| 📈 **통계** | 날짜별 매출 통계 시각화 |
| 🏷️ **카테고리 관리** | 카테고리 순서 변경, 등록, 수정, 삭제 |

---

## 📌 트러블슈팅

**N+1 문제 해결**
- 문제: 상품 목록 조회 시 상품마다 카테고리를 별도로 조회해 쿼리가 과도하게 발생
- 해결: JOIN 쿼리로 한 번에 조회하도록 MyBatis 쿼리 최적화

**카카오페이 결제 URL 변경**
- 문제: 배포 후 카카오페이 콜백 URL이 localhost로 설정되어 결제 실패
- 해결: 배포 URL로 approval, cancel, fail URL 변경

---

## 👨‍💻 개발자

| 이름 | 역할 |
| --- | --- |
| 박용준 | 백엔드 개발 |
| 이세근 | 백엔드 개발 |
| 김카타리나 | 백엔드 개발 |

---

## 🖥 실행 방법

```bash
git clone https://github.com/nuj27301/coreflow_personal.git
cd coreflow_personal
# application-private.properties에 DB 및 API 정보 입력 후
./mvnw spring-boot:run
```