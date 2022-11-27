package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.JWTAuthResponse;
import com.bugtracker.bugtracker.dto.LoginDto;
import com.bugtracker.bugtracker.dto.MemberDto;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.security.JwtTokenProvider;
import com.bugtracker.bugtracker.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, MemberService memberService, MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager ;
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto);
            memberService.signup(memberDto);
        System.out.println("in controller");
        return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
    }
}
