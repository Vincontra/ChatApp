package com.sub.chat.chat_app_backend.controllers;
import com.sub.chat.chat_app_backend.entities.Message;
import com.sub.chat.chat_app_backend.entities.Room;
import com.sub.chat.chat_app_backend.repositories.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:3000")
public class RoomController {

    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository){
        this.roomRepository=roomRepository;
    }
    // create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){
        // pehle we need to check ki wo room pehle se na ho
        // that we will check through roomRepo
        if (roomRepository.findByRoomId(roomId)!=null){
            // room is already there
            return ResponseEntity.badRequest().body("Room already exist");
        }
        else{
            // nhi hai matlab create krna padega room
             Room room=new Room();
             room.setRoomId(roomId);
             roomRepository.save(room);
             Room savedRooms=roomRepository.save(room);
             return ResponseEntity.status(HttpStatus.CREATED).body(room);
        }
    }

    // get room: i mean which room do you want to join
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        // agar room hai to we can join
        // otherwise we cant
        Room room=roomRepository.findByRoomId(roomId);
        if (room==null){
            return ResponseEntity.badRequest().body("Room does not exist");
        }
        return ResponseEntity.ok(room);
    }



    //get messages of room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>>getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size",defaultValue = "20",required = false) int size
    ){
        Room room=roomRepository.findByRoomId(roomId);
        if (room==null){
            return ResponseEntity.badRequest().build();
        }

        List<Message>messages=room.getMessages();
        //pagination
        int start=Math.max(0,messages.size()-(page+1)*size);
        int end=Math.min(messages.size(),start+size);
        List<Message>paginatedMessages=messages.subList(start,end);
        return ResponseEntity.ok(paginatedMessages);

    }
}
