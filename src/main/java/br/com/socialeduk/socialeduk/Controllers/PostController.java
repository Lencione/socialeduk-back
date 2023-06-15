package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Dto.PostRequestDto;
import br.com.socialeduk.socialeduk.Dto.Response;
import br.com.socialeduk.socialeduk.Entities.Post;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Services.PostService;
import br.com.socialeduk.socialeduk.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(new Response("success", "Post retrieved successfull!", postService.get(id) ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("error", e.getMessage(), null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<Response> store(@RequestBody PostRequestDto request){
        try{
            Post post = new Post();
            post.setContent(request.getContent());
            post.setUser(userService.getUserById(request.getUserId()));
            post = postService.store(post);
            return ResponseEntity.ok().body(new Response("success", "Post created successfull", post));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        try{
            postService.delete(id);
            return ResponseEntity.ok().body(new Response("success", "Post deleted successfull", true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("error", e.getMessage(), null));
        }
    }

    @GetMapping("{userId}/getAll")
    public ResponseEntity<Response> getAll(@PathVariable Long userId){
        try{
            User user = userService.getUserById(userId);
            List<?> posts = postService.getAllByUserId(user.getId());
            return ResponseEntity.ok().body(new Response("success","Posts retrieved with success!", posts));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response("error",e.getMessage(), null));
        }
    }

    // get all friends posts paginated
    @GetMapping("/getAllFriendsPost/{userId}")
    public ResponseEntity<Response> getAllFriendsPost(@PathVariable Long userId,  @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        try{
            User user = userService.getUserById(userId);
            List<Post> posts = postService.getAllByUserFriends(user.getId(), page, size);
            return ResponseEntity.ok().body(new Response("success","Posts retrieved with success!", posts));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response("error",e.getMessage(), null));
        }
    }




}
