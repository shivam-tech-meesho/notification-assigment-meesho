package com.meesho.notification_system.controllers;


import com.meesho.notification_system.dto.req.BlackList;
import com.meesho.notification_system.services.BlackListService;
import com.meesho.notification_system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blacklist")
public class BlackListRequestRoutes {

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> addBlackList( @Valid @RequestBody BlackList blackList) {
        blackListService.addToBlockList(blackList.getPhoneNumberList());
        userService.changeStatusToBlockList(blackList.getPhoneNumberList());
        return ResponseEntity.ok("Success Added to Block List");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> removeFromBlockList(@Valid @RequestBody BlackList blackList) {
        blackListService.removeFromBlockList(blackList.getPhoneNumberList());
        userService.changeStatusToWhiteList(blackList.getPhoneNumberList());
        return ResponseEntity.ok("Success removed from Black List");
    }

    @GetMapping("/")
    public ResponseEntity<?> getBlackList() {
          return ResponseEntity.
                  status(HttpStatus.OK)
                  .body(userService.getAllBlackListNumbers());

    }
}
