-- Tabela "users"
CREATE TABLE users (
    id NUMBER(10) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR2(50) NOT NULL,
    password VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela "posts"
CREATE TABLE posts (
    id NUMBER(10) PRIMARY KEY AUTO_INCREMENT,
    user_id NUMBER(10) NOT NULL,
    content VARCHAR2(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Tabela "friend_requests"
CREATE TABLE friend_requests (
    id NUMBER(10) PRIMARY KEY AUTO_INCREMENT,
    sender_id NUMBER(10) NOT NULL,
    receiver_id NUMBER(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (receiver_id) REFERENCES users (id)
);

-- Tabela "friends"
CREATE TABLE friends (
    id NUMBER(10) PRIMARY KEY AUTO_INCREMENT,
    user_id_1 NUMBER(10) NOT NULL,
    user_id_2 NUMBER(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id_1) REFERENCES users (id),
    FOREIGN KEY (user_id_2) REFERENCES users (id)
);