package pl.azebrow.harvest.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity test1(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/test")
    public ResponseEntity test2(){
        return ResponseEntity.ok("not ok");
    }
}
