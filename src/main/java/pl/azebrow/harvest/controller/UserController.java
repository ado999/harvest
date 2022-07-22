package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.azebrow.harvest.constants.RoleEnum;

@RestController
@RequestMapping("/api/v1/user")
@Secured(RoleEnum.Constants.USER)
@RequiredArgsConstructor
public class UserController {



}
