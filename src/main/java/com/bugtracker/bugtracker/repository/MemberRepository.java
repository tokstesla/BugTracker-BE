package com.bugtracker.bugtracker.repository;

import com.bugtracker.bugtracker.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findMemberById(int memberId);

    Boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Member findUserByEmail(String email);
}
