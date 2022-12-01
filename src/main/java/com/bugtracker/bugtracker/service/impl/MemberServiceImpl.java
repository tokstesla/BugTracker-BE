package com.bugtracker.bugtracker.service.impl;

import com.bugtracker.bugtracker.dto.MemberDto;
import com.bugtracker.bugtracker.entity.Member;
import com.bugtracker.bugtracker.entity.enums.Role;
import com.bugtracker.bugtracker.exception.BugTrackerException;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public void signup(MemberDto memberDto) {
        if(memberRepository.existsByEmail(memberDto.getEmail())){
            throw new BugTrackerException(HttpStatus.BAD_REQUEST,"email exist");
        } else{
            Member member = modelMapper.map(memberDto, Member.class);
            member.setPassword(memberDto.getPassword());
            member.setRoles(Role.USER);
            memberRepository.save(member);
        }
    }

    @Override
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(int id) {
        return memberRepository.findMemberById(id);
    }

    @Override
    public Member updateMember(int id, MemberDto memberDto) {
        Member member = memberRepository.findMemberById(id);
            member.setFirstName(memberDto.getFirstName());
            member.setLastName(memberDto.getLastName());
            memberRepository.save(member);
        return null;
    }


    private Integer convertToInteger(String num){
        return Integer.getInteger(num);
    }

}
