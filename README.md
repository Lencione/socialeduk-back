
<center>
    <img src="https://github.com/Lencione/socialeduk-back/assets/44005188/77cc8aa8-4ee1-4137-bb44-f8dd58b4591b" width="300" height="300" title="SocialEduk" alt="SocialEduk"/>
</center>

 <h2 align="center">Social Eduk - BACK-END</h2>
 <p align="center">Projeto para 3º semestre - Ciência da Computação UNIFAJ</p>

- #### Rotas de autenticação
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/login` - User login.
  ```JSON
      {
          "email": "admin@admin.com",
          "password": "password"
      }
    ```
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/register` - User register.
  ```JSON
      {
          "name": "admin",
          "username": "admin",
          "email": "admin@admin.com",
          "password": "password"
      }
  ```

- #### Rotas do Usuário
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getAll` - Get All users.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/{id}` - Get user by ID.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getSendFriendRequests/{id}` -  - Get all sent Friend Request by User id.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getReceivedFriendRequests/{id}` -  - Get all received Friend Request by User id.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getFriends/{id}` -  - Get all Friends by User id.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getBlockedUsers/{id}` -  - Get all blocked users from User.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/users/getNotBlockedUsers/{id}` -  - Get all not blocked users from User.
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/users/acceptFriendRequest` -  - Accept a Friend Request.
    ```JSON
      {
          "userId": 1,
          "friendRequestId": 2
      }
    ```
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/users/unblockUser` -  - Block user.
    ```JSON
      {
          "sender": 1,
          "receiver": 2
      }
    ```
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/users/blockUser` -  - Block user.
    ```JSON
      {
          "sender": 1,
          "receiver": 2
      }
    ```
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/users/sendFriendRequest` - Create Friend Request.
      ```JSON
      {
          "sender": 1,
          "receiver": 2
      }
      ```
  - ![DELETE](https://img.shields.io/badge/-DELETE-red) - `/api/users/refuseFriendRequest` - Refuse a Friend request.
      ```JSON
        {
            "userId": 1,
            "friendRequestId": 2
        }
      ```

- #### Rotas de Posts
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/posts/{id}` -  - Get post by Post Id.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/posts/{userId}/getAll` -  - Get all posts by User id.
  - ![GET](https://img.shields.io/badge/-GET-green) - `/api/posts/getAllFriendsPost/{userId}` -  - Get all friends posts by User id.
  - ![POST](https://img.shields.io/badge/-POST-blue) - `/api/posts/` -  - Create Post by User.
    ```JSON
          {
              "userId": 1,
              "content": "Conteudo do post"
          }
    ```
  - ![DELETE](https://img.shields.io/badge/-DELETE-red) - `/api/posts/delete/{id}` - Delete Post.


### Criação do Banco de dados

```SQL
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
```


Feito com :heart: (ou ódio) e Java.