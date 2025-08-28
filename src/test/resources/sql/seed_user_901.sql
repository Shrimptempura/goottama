INSERT INTO roles VALUES(100, '일반', '게시글/댓글 작성, 수정, 삭제, 상품 조회 및 구매, 본인 정보 조회/수정, 본인 주문 내역 조회, 신고 접수');
INSERT INTO roles VALUES(200, '판매자', '일반회원 권한, 상품 등록/수정/삭제, 배송 상태 처리, 판매 통계 조회, 문의/리뷰 확인 및 응답');
INSERT INTO roles VALUES(300, '관리자', '판매자 권한, 게시글/댓글/신고 관리, 회원 목록 조회 / 정지, 전체 상품 목록 관리, 주문/배송 상태 강제 변경, 통계 조회, 이벤트/배너/공지 등록 및 수정 가능');
INSERT INTO roles VALUES(400, '운영자', '모든 데이터 및 권한 접근 가능, 권한 수정, 부여 및 회수 가능, DB 접근 가능, 설정값 관리, 모든 로그 열람 가능, 모든 삭제/복원 작업 가능');

INSERT INTO user_detail(user_id, user_name,user_nickname,user_gender,user_birth,user_tel,user_zipcode,user_addr,user_email)
VALUES (901, '업체통합테스트2','업체닉네임5','F','2003-01-01',
        '01012343333','13111','서울시 구로구 구트아카데미3','wweee@naver.com');

INSERT INTO user_login
VALUES('companyIT002',901,100,'$2a$10$bY8fsZp6eKJrRSvaNHIINOPlQ6S3Ed4ON1Hy8PA7DEjgifETN915W');

