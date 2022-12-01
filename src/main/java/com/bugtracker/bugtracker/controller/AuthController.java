package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.LoginDto;
import com.bugtracker.bugtracker.dto.MemberDto;
import com.bugtracker.bugtracker.entity.Member;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.response.LoginResponse;

import com.bugtracker.bugtracker.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public AuthController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }


    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Member member = memberRepository.findUserByEmail(loginDto.getEmail());
        if(member.getPassword().equals(loginDto.getPassword())){
            return ResponseEntity.ok(new LoginResponse(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(),member.getRoles().toString()));
        }
        return new ResponseEntity<>(new LoginResponse("user not allowed"),HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto);
            memberService.signup(memberDto);
        System.out.println("in controller");
        return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
    }
}
