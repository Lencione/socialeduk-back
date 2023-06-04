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
    user_id NUMBER(10) NOT NULL,
    friend_id NUMBER(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
);

-- Tabela "blocks"
CREATE TABLE blocked_users(
    id NUMBER(10) PRIMARY KEY AUTO_INCREMENT,
    user_id NUMBER(10) NOT NULL,
    blocked_id NUMBER(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (blocked_id) REFERENCES users (id)
);

INSERT INTO users (username, password, name, email) VALUES ('USER 1', 'admin', 'USER 1', 'admin@admin.com');
INSERT INTO users (username, password, name, email) VALUES ('USER 2', 'password', 'USER 2', 'user1@dba.com');
INSERT INTO users (username, password, name, email) VALUES ('USER 3', 'password', 'USER 3', 'user2@dba.com');

INSERT INTO posts (user_id, content) VALUES (1, 'Primeiro post do USER 1');
INSERT INTO posts (user_id, content) VALUES (2, 'Primeiro post do USER 2');
INSERT INTO posts (user_id, content) VALUES (3, 'Primeiro post do USER 3');

-- Admin enviou solicitação para o User1
INSERT INTO  friend_requests (sender_id, receiver_id) VALUES (1, 2);

-- User1 enviou solicitação para o User2
INSERT INTO  friend_requests (sender_id, receiver_id) VALUES (2, 3);

-- User1 acetou solicitação do Admin
INSERT INTO friends (user_id, friend_id) VALUES (1, 2);
INSERT INTO friends (user_id, friend_id) VALUES (2, 1);

-- User2 aceitou solicitação do User1
INSERT INTO friends (user_id, friend_id) VALUES (2, 3);
INSERT INTO friends (user_id, friend_id) VALUES (3, 2);

-- User2 bloqueou o Admin
INSERT INTO blocked_users (user_id, blocked_id) VALUES (2, 1);

-- Visualizar todos os usuários
SELECT * FROM users;

-- Visualizar todos os posts do Admin
SELECT CONTENT,created_at FROM posts WHERE user_id = 1;

--  Visualizar todos os amigos do Admin fazendo inner join com users
SELECT users.name, users.email FROM users INNER JOIN friends ON users.id = friends.friend_id WHERE friends.user_id = 1;

--  Visualizar todos os amigos do User1 fazendo inner join com users
SELECT users.name, users.email FROM users INNER JOIN friends ON users.id = friends.friend_id WHERE friends.user_id = 2;

--  Visualizar todos os amigos do User2 fazendo inner join com users
SELECT users.name, users.email FROM users INNER JOIN friends ON users.id = friends.friend_id WHERE friends.user_id = 3;

--  Visualizar todos os usuários bloqueados do User2 fazendo inner join com users
SELECT u.name, u.email FROM users u INNER JOIN blocked_users b ON u.id = b.blocked_id WHERE b.user_id = 2;




