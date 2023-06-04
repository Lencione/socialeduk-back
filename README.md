
 <h2 align="center">Social Eduk - BACK-END</h2>
 <p align="center">Projeto para 3º semestre - Ciência da Computação UNIFAJ</p>


### Created Routes

#### Authentication
- `/api/login` - User login.
- `/api/register` - User register.

- #### User Routes
- `/api/users/getAll` - `GET` - Get All users.
- `/api/users/{id}` - `GET` - Get user by ID.
- `/api/users/sendFriendRequest` - `Post` - Create Friend Request.
- - Params: sender, receiver.
- `/api/users/getSendFriendRequests/{id}` - `GET` - Get all sent Friend Request by User id.
- `/api/users/getReceivedFriendRequests/{id}` - `GET` - Get all received Friend Request by User id.
- `/api/users/refuseFriendRequest` - `DELETE` - Refuse a Friend request.
- - Params: userId, friendRequestId.
- `/api/users/acceptFriendRequest` - `POST` - Accept a Friend Request.
- - Params: userId, friendRequestId.
- `/api/users/getFriends/{id}` - `GET` - Get all Friends by User id.
- `/api/users/blockUser` - `POST` - Block user.
- - Params: sender, receiver.
- `/api/users/unblockUser` - `POST` - Block user.
- - Params: sender, receiver.
- `/api/users/getBlockedUsers/{id}` - `GET` - Get all blocked users from User.
- `/api/users/getNotBlockedUsers/{id}` - `GET` - Get all not blocked users from User.

#### Post Routes
- `/api/posts/{id}` - `GET` - Get post by Post Id.
- `/api/posts/` - `POST` - Create Post by User.
- - Params: userId, content.
- `/api/posts/delete/{id}` - `DELETE` - Delete Post.
- `/api/posts/{userId}/getAll` - `GET` - Get all posts by User id.
- `/api/posts/getAllFriendsPost/{userId}` - `GET` - Get all friends posts by User id.



Feito com :heart: (ou ódio) e Java.