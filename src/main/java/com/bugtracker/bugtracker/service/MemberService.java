package com.bugtracker.bugtracker.service;

import com.bugtracker.bugtracker.dto.MemberResponse;
import com.bugtracker.bugtracker.dto.MemberDto;
import com.bugtracker.bugtracker.entity.Member;

public interface MemberService {
    void signup(MemberDto memberDto);

    MemberResponse getAllMember(int pageNo, int pageSize, String sortBy, String sortDir);

    Member getMember(int id);

    Member updateMember(int id, MemberDto memberDto);



    //LoginResponse signin(LoginDto loginDto) throws IllegalAccessException;
}
