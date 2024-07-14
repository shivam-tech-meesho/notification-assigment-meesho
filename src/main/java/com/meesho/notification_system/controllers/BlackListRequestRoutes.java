package com.meesho.notification_system.controllers;


import com.meesho.notification_system.dto.req.BlackList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blacklist")
public class BlackListRequestRoutes {

    @PostMapping("/")
    public ResponseEntity<?> addBlackList(@RequestBody BlackList blackList) {
        return ResponseEntity.ok(blackList.toString());
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteBlackList(@RequestBody BlackList blackList) {
        return ResponseEntity.ok(blackList.toString());
    }

    @GetMapping("/")
    public ResponseEntity<BlackList> getBlackList() {
        return  ResponseEntity.ok(new BlackList());
    }
}
