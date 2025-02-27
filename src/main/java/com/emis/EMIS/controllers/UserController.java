package com.emis.EMIS.controllers;

import com.emis.EMIS.services.OTPService;
import com.emis.EMIS.services.UserService;
import com.emis.EMIS.utils.JwtUtil;
import com.emis.EMIS.wrappers.responseDTOs.ResponseDTO;
import com.emis.EMIS.wrappers.requestDTOs.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private  final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final OTPService otpService;

    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/all/create-profile")
    public ResponseDTO createProfile(@RequestBody @Valid ProfileDto profileDto){
        return userService.createProfile(profileDto);
    }
    @GetMapping("/all/profiles")
    public ResponseDTO fetchProfiles(){
        return userService.fetchAll();
    }
    @GetMapping("/all/profile/{profile}")
    public ResponseDTO fetchOne(String profile){
        return userService.fetchByProfile(profile);
    }

    @PostMapping("/all/register")
    public ResponseDTO register(@Valid @RequestBody UserDTO userDTO){

        log.info("Register request received from the customer::{}",userDTO);
        return userService.register(userDTO);
    }

    @PostMapping("/all/activateAcc")
    public ResponseDTO verifyOtp(@RequestBody @Valid ActivateAccDTO activateAccDTO){
        return userService.activateAccount(activateAccDTO);
    }

    @GetMapping("/all/regenerate-otp/{userId}")
    public ResponseDTO regenerateOTP(@PathVariable int userId){
        return otpService.regenerateOtp(userId);
    }

    @PostMapping("/all/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, String> objectMap = new HashMap<>();
        try {
            log.info("loginDTO:: {}", loginDTO);
            userService.loadUserByUsername(loginDTO.getEmail());
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

            if (authenticate.isAuthenticated()) {
                String jwtToken =  jwtUtil.generateToken(loginDTO.getEmail());
                objectMap.put("jwtToken", jwtToken);
                responseDTO.setStatusMessage("Authenticated successfully");
                responseDTO.setResult(objectMap);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setStatusMessage("Invalid access");
                return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error("Exception occurred ", ex);
            responseDTO.setStatusMessage(ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
        }


    }
    @PostMapping("/all/forgot-password/{email}")
    public ResponseDTO forgotPassword(@PathVariable String email){
        return otpService.forgotPassword(email);
    }
//
//    @PostMapping("/v1/admsdm/add-authority")
//    public ResponseDTO addAuthority(@RequestBody AddAuthDto addAuthDto){
//        return userService.addAuthority(addAuthDto);
//    }

    @PostMapping("/update-profile")
    public ResponseDTO updateProfile(@RequestPart("file") MultipartFile file){
        return userService.updateProfilePic(file);
    }
}

