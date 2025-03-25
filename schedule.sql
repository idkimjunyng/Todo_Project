CREATE SCHEMA todo;

USE todo;

CREATE TABLE todo (
      todo_id BIGINT AUTO_INCREMENT NOT NULL COMMENT "일정 아이디",
      member_name TEXT COMMENT "일정 멤버 이름",
      todo_content TEXT COMMENT "일정 내용",
      todo_password TEXT COMMENT "일정 비밀번호",
      create_at DATETIME DEFAULT NOW() COMMENT "일정 생성 날짜",
      update_at DATETIME DEFAULT NOW() COMMENT "일정 수정 날짜",
      PRIMARY KEY (todo_id)
) ENGINE=InnoDB CHARACTER SET utf8;