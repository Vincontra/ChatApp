package com.sub.chat.chat_app_backend.controllers;


import com.sub.chat.chat_app_backend.entities.Room;
import com.sub.chat.chat_app_backend.repositories.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
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
        } else{
            // nhi hai matlab create krna padega room
             Room room=new Room();
             room.setRoomID(roomId);
             roomRepository.save(room);
             Room savedRooms=roomRepository.save(room);
             return ResponseEntity.status(HttpStatus.CREATED).body(room);
        }
    }



    // get room


    //get messages of room




}
