package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Entities.Post;
import br.com.socialeduk.socialeduk.Repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;


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
        List<Post> posts = postRepository.getAllByUserId(user_id);
        return posts;
    }
}
