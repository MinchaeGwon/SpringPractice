package com.example.practice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	  Optional<Member> findById(Long id);
	  List<Member> findAll();
	  Optional<Member> findByEmail(String email);
	  Optional<Member> findByEmailAndType(String email, String type);

}