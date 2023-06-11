package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Entities.Post;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.FriendRepository;
import br.com.socialeduk.socialeduk.Repositories.PostRepository;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;


    public Post store(Post post){
        return postRepository.save(post);
    }

    public Post get(Long id){
        Post post = postRepository.getPostById(id);

        if(post == null){
            throw new RuntimeException("Post not found");
        }
        return post;
    }


    public void delete (Long id){
        Post post = postRepository.getPostById(id);
        if(post == null){
            throw new RuntimeException("Post not found!");
        }
        postRepository.delete(post);
    }

    public List<Post> getAllByUserId(Long user_id){
      return postRepository.getAllByUserId(user_id);
    }

    public List<Post> getAllByUserFriends(Long userId, Integer page, Integer size){
        User user = userRepository.getUserById(userId);

        if(user == null){
            throw new RuntimeException("Invalid User!");
        }
        List<Post> posts = postRepository.findAll();
        List<User> friends = friendRepository.findFriendsByUserId(userId);
        posts = posts.stream().filter(post -> friends.contains(post.getUser())).collect(Collectors.toList());
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        for (Post post : posts) {
            post.getUser().setPost(null);
        }
        return posts;
    }

}
