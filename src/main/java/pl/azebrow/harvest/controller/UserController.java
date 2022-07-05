package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.UserRequest;
import pl.azebrow.harvest.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Secured(RoleEnum.Constants.ADMIN)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createStaffAccount(
            @RequestBody UserRequest dto) {
        userService.createStaffAccount(dto);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @PathVariable Long id,
            @RequestBody UserRequest dto
    ){
        userService.updateAccount(id, dto);
        return ResponseEntity.ok(null);
    }

}
