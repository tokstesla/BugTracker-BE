package com.bugtracker.bugtracker.service;

import com.bugtracker.bugtracker.dto.MemberResponse;
import com.bugtracker.bugtracker.dto.MemberDto;
import com.bugtracker.bugtracker.entity.Member;

import java.util.List;

public interface MemberService {
    void signup(MemberDto memberDto);

    List<Member> getAllMember();

    Member getMember(int id);

    Member updateMember(int id, MemberDto memberDto);



    //LoginResponse signin(LoginDto loginDto) throws IllegalAccessException;
}
