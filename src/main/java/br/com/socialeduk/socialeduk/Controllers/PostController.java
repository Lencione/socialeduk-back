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

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable Long id){
        try{
            Post post = postService.get(id);
            post.getUser();
            return ResponseEntity.ok().body(new Response("success", "Post retrieved successfull!", post ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("error", e.getMessage(), null));
        }
    }

    @PostMapping("/store")
    public ResponseEntity<Response> store(@RequestBody PostRequestDto request){
        try{
            Post post = new Post();
            post.setContent(request.getContent());
            post.setUser(userService.getUserById(request.getUser_id()));
            post = postService.store(post);
            return ResponseEntity.ok().body(new Response("success", "Post created successfull", post));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        try{
            postService.delete(id);
            return ResponseEntity.ok().body(new Response("success", "Post deleted successfull", true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("error", e.getMessage(), null));
        }
    }

    @GetMapping("{user_id}/getAll")
    public ResponseEntity<Response> getAll(@PathVariable Long user_id){
        try{
            User user = userService.getUserById(user_id);
            List<?> posts = postService.getAllByUserId(user.getId());
            return ResponseEntity.ok().body(new Response("success","Posts retrieved with success!", posts));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response("error",e.getMessage(), null));
        }
    }




}
