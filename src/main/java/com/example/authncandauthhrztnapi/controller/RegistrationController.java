package com.example.authncandauthhrztnapi.controller;

import com.example.authncandauthhrztnapi.appuser.RegistrationRequest;
import com.example.authncandauthhrztnapi.service.AuthService;
import com.example.authncandauthhrztnapi.service.RegistrationService;
import com.example.authncandauthhrztnapi.appuser.LoginRequest;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@Api(value="Controller")
public class RegistrationController {

    private AuthService authService;
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token)
    {
try{
    return registrationService.confirmToken(token);

}
catch (Exception ex){return ex.toString();}
    }

    @PostMapping(path = "login")
    public ResponseEntity login(@RequestBody LoginRequest request){
        return authService.SignIn(request);
    }
}
