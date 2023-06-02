package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Dto.FriendRequestDto;
import br.com.socialeduk.socialeduk.Dto.Response;
import br.com.socialeduk.socialeduk.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok().body(new Response("success", "Users retrieved successfull!", userService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(new Response("success", "User retrieved successfull!", userService.getUserById(id)));
        }catch(Exception e) {
            return ResponseEntity.ok().body(new Response("error", e.getMessage(), null));
        }
    }

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<Response> sendFriendRequest(@RequestBody FriendRequestDto request){
        try{
            return ResponseEntity.ok().body(new Response("success", "Friend request sent successfull!", userService.sendFriendRequest(request.getSender(),request.getReceiver())));
        }catch(Exception e) {
            return ResponseEntity.ok().body(new Response("error", e.getMessage(), null));
        }
    }

    @GetMapping("/getSendFriendRequests/{id}")
    public ResponseEntity<Response> getFriendRequests(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(new Response("success", "Teste", userService.getSendFriendsRequest(id)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response("error", e.getMessage(), null));
        }
    }

    @GetMapping("/getReceivedFriendRequests/{id}")
    public ResponseEntity<Response> getReceivedFriendRequests(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(new Response("success", "Teste", userService.getReceivedFriendsRequest(id)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response("error", e.getMessage(), null));
        }
    }

//    @PostMapping("/refuseFriendRequest")
//    public ResponseEntity<Response> refuseFriendRequest(@RequestBody FriendRequestDto request){
//        try{
//            return ResponseEntity.ok().body(new Response("success", "Friend request recusada!", userService.refuseFriendRequest(request.getSender(),request.getReceiver())));
//        }catch (Exception e){
//            return ResponseEntity.ok().body(new Response("error", e.getMessage(), null));
//        }
//    }
}
