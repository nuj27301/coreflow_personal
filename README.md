# 🛍️ Coreflowshop Web Project

의류 쇼핑몰 사용자 & 관리자 통합 웹 서비스

---

## 📖 프로젝트 소개

Coreflowshop은 효율적인 온라인 쇼핑 경험과 관리자 중심의 운영 편의성을 목표로 설계된 웹 쇼핑몰 프로젝트입니다. 사용자 친화적인 인터페이스와 통합 관리 시스템을 통해 일반 사용자와 관리자가 모두 쉽게 사용할 수 있도록 구현되었습니다.

이 프로젝트는 의류 쇼핑몰의 **사용자 페이지**와 **관리자 페이지**를 구현한 웹 애플리케이션입니다.\
일반 사용자는 회원가입부터 상품 주문, 결제, 리뷰 작성 등 쇼핑 기능을 이용할 수 있으며,\
관리자는 상품과 회원, 주문, 리뷰, 통계 등을 관리할 수 있습니다.

---

## 🚀 주요 기능

### 👗 사용자 페이지 (User)

| 기능 구분                  | 설명                                                 |
| ---------------------- | -------------------------------------------------- |
| 🏠 **메인 페이지**          | 최신 상품 및 인기 상품 표시                                   |
| 📝 **회원가입 기능**         | 신규 사용자 계정 생성, 다음 우편번호 API를 이용한 주소 검색, 이메일 인증 기능 포함 |
| 🔐 **로그인 기능**          | 일반 로그인 및 자동 로그인 지원                                 |
| 💬 **카카오 로그인**         | 카카오 계정으로 간편 로그인                                    |
| 🛍️ **상품 페이지**         | 카테고리별 상품 목록 및 장바구니, 구매버튼 표시                        |
| 👕 **상품 상세 페이지**       | 상품 상세 정보 및 리뷰 확인                                   |
| ✍️ **리뷰 작성 기능**        | 구매 상품에 대한 후기 작성                                    |
| 🛒 **장바구니 페이지**        | 선택 상품 담기 / 수량 변경 / 삭제                              |
| 💳 **주문 페이지**          | 주문서 작성 및 결제 전 확인                                   |
| 💰 **카카오페이 결제 기능**     | 카카오페이를 통한 간편 결제                                    |
| 📧 **결제 내역 메일 발송**     | 주문 완료 시 이메일 알림 발송                                  |
| 📦 **주문 접수 페이지**       | 주문 완료 후 접수 상태 표시                                   |
| 🤖 **챗봇 기능**           | FAQ 및 간단한 문의 AI자동응답                                |
| 👤 **마이페이지 - 비밀번호 변경** | 비밀번호 수정 기능                                         |
| 👤 **마이페이지 - 회원정보 수정** | 이름, 이메일 등 개인정보 수정                                  |
| 📦 **마이페이지 - 주문관리**    | 주문 내역 및 배송 상태 조회                                   |
| ❌ **마이페이지 - 회원탈퇴**     | 회원 탈퇴 기능                                           |

---

### 🧭 관리자 페이지 (Admin)

| 기능 구분               | 설명                          |
| ------------------- | --------------------------- |
| 🔐 **관리자 로그인 페이지**  | 관리자 전용 로그인                  |
| 📊 **메인 대시보드**      | 회원 수, 주문 수, 매출 등 통계 요약      |
| 👥 **회원정보 관리 페이지**  | 회원 목록 검색, 수정, 삭제            |
| 👗 **상품 목록 페이지**    | 등록된 상품 검색, 수정, 삭제           |
| ➕ **상품 등록 기능**      | 신규 상품 추가 등록                 |
| 📦 **주문 목록 페이지**    | 주문 상세내역 및 상태 변경 관리          |
| 💬 **리뷰 목록 페이지**    | 리뷰 답변기능 및 삭제                |
| 📈 **통계 페이지**       | 날짜별 매출 통계 시각화               |
| 🏷️ **카테고리 관리 페이지** | 상품 카테고리 순서변경, 등록, 수정, 삭제 기능 |

---

## 🗄️ 데이터베이스 구조

### 🧑‍💼 admin
- ad_userid
- ad_passwd
- login_date

### 🧍 members
- mbsp_id
- mbsp_name
- mbsp_email
- mbsp_password
- mbsp_zipcode
- mbsp_address
- mbsp_addressdetail
- mbsp_phone
- mbsp_gender
- mbsp_receive
- mbsp_birthdate
- mbsp_point
- mbsp_lastlogin
- mbsp_datesub
- mbsp_updatedate

### 🛒 cart
- pro_num
- mbsp_id
- cart_amount
- cart_date

### 🏷️ categories
- cate_code
- cate_prtcode
- cate_name
- cate_order

### 📜 loginlog
- log_idx
- log_userid
- log_accesstime

### 🧾 orders
- ord_code
- mbsp_id
- ord_name
- ord_addr_zipcode
- ord_addr_basic
- ord_addr_detail
- ord_tel
- ord_mail
- ord_price
- ord_status
- ord_regdate
- ord_message

### 📦 order_items
- ord_code
- pro_num
- oi_quantity
- oi_price

### 🚚 order_shipping
- delivery_code
- ord_code
- shipping_recipient
- shipping_phone
- shipping_zipcode
- shipping_address
- shipping_address2
- delivery_date
- delivery_status

### 💳 payment
- payment_id
- ord_code
- mbsp_id
- payment_method
- payment_price
- payment_status
- payment_date

### 👕 products
- pro_num
- cate_code
- pro_name
- pro_price
- pro_discount
- pro_publisher
- pro_summary
- pro_up_folder
- pro_img
- pro_amount
- pro_buy
- pro_review
- pro_date
- pro_updatedate

### 💬 review
- rev_code
- mbsp_id
- pro_num
- rev_content
- rev_rate
- rev_date

### 💭 review_replies
- reply_id
- rev_code
- manager_id
- reply_text
- reply_date

---

## 🛠️ 기술 스택

| 구분              | 사용 기술                                                          |
| --------------- | -------------------------------------------------------------- |
| **Frontend**    | HTML, CSS, JavaScript, jQuery, Bootstrap, Thymeleaf            |
| **Backend**     | Spring Boot / MyBatis / JAVA                                   |
| **Database**    | MySQL                                                          |
| **API / 외부 연동** | Kakao Login API, KakaoPay API, Daum Postcode API, SMTP (메일 전송), Gemini Open API |
| **Server / 배포** | AWS EC2                                                        |
| **Tool**        | SpringToolSuite, DBeaver, VS Code, GitHub                      |

---

## 👨‍💻 개발자
- 박용준
- 이세근
- 김카타리나

---

## 📂 프로젝트 구조

```
coreflowshop/  # 패키지별 기능 분류 중심으로 요약
├── src/
│   ├── main/
│   │   ├── java/com/coreflow/shop/
│   │   │   ├── CoreFlowShopApplication.java
│   │   │   ├── HomeController.java
│   │   │   ├── admin/
│   │   │   ├── cart/
│   │   │   ├── category/
│   │   │   ├── chatbot/
│   │   │   ├── common/
│   │   │   │   ├── config/
│   │   │   │   ├── constants/
│   │   │   │   ├── dto/
│   │   │   │   ├── utils/
│   │   │   ├── kakaologin/
│   │   │   ├── kakaopay/
│   │   │   ├── mail/
│   │   │   ├── member/
│   │   │   ├── order/
│   │   │   ├── product/
│   │   │   ├── review/
│   │   ├── resources/
│   │   │   ├── kakaopay/
│   │   │   ├── mail/
│   │   │   ├── mapper/
│   │   │   ├── static/
│   │   │   │   ├── chart/
│   │   │   │   ├── ckeditor/
│   │   │   │   ├── css/
│   │   │   │   ├── dist/
│   │   │   │   ├── image/
│   │   │   │   ├── plugins/
│   │   │   │   ├── index.html
│   │   │   │   ├── test.html
│   │   │   ├── templates/
│   │   │   │   ├── admin/
│   │   │   │   │   ├── category/
│   │   │   │   │   ├── fragments/
│   │   │   │   │   ├── layouts/
│   │   │   │   │   ├── member/
│   │   │   │   │   ├── order/
│   │   │   │   │   ├── products/
│   │   │   │   │   ├── review/
│   │   │   │   │   ├── statist/
│   │   │   │   │   ├── ad_login.html
│   │   │   │   │   ├── ad_menu.html
│   │   │   │   ├── cart/
│   │   │   │   ├── chatapi/
│   │   │   │   ├── fragments/
│   │   │   │   ├── layouts/
│   │   │   │   ├── mail/
│   │   │   │   ├── member/
│   │   │   │   ├── order/
│   │   │   │   ├── product/
│   │   │   │   ├── index.html
│   │   │   ├── application.properties
│   │   │   ├── application-private.properties
│   │   │   └── application-real.properties
│   └── test/
├── .mvn/
├── .settings/
├── 2025/
├── target/
├── .classpath
├── .factorypath
├── .gitattributes
├── .gitignore
├── .project
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
```

---

## 포트폴리오

제 개인 웹 포트폴리오를 아래 링크에서 확인하실 수 있습니다.

🔗 [사용자페이지 바로가기가(http://13.209.216.208)
🔗 [관리자페이지 바로가기가(http://13.209.216.208/admin/)

---

---

## 📜 라이선스
MIT License

---
