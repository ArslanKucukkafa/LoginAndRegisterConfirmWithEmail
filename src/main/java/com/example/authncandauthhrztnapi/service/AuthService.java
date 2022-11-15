package com.example.authncandauthhrztnapi.service;

import com.example.authncandauthhrztnapi.appuser.AppUser;
import com.example.authncandauthhrztnapi.appuser.LoginRequest;
import com.example.authncandauthhrztnapi.repository.AppUserRepository;
import com.example.authncandauthhrztnapi.token.ConfirmationToken;
import com.example.authncandauthhrztnapi.token.ConfirmationTokenSerivce;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private  final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenSerivce tokenSerivce;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }


    public ResponseEntity SignIn(@RequestBody LoginRequest loginRequest){
        Optional<AppUser> isconfirmed = appUserRepository.findByEmail(loginRequest.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());
        if(isconfirmed.isPresent()){
            return ResponseEntity.ok("validate password an email  "+isconfirmed);
        }else {
            boolean x= false;
            return ResponseEntity.ok("Yanlış "+isconfirmed);}
    }


    public String signUpUser(AppUser appUser){
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        boolean userExist = appUserRepository.findByEmailAndPassword(appUser.getEmail(),encodedPassword).isPresent();
      if(userExist){
          throw new IllegalStateException("email already taken");
      }

      appUser.setPassword(encodedPassword);

      appUserRepository.save(appUser);

      String token = UUID.randomUUID().toString();
      // TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser);

        tokenSerivce.saveConfirmationToken(confirmationToken);

        // TODO:  Send Email
        return token ;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }


}
