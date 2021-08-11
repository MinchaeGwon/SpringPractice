package com.example.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.Member;
import com.example.practice.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public Optional<Member> findById(Long id) {
		return memberRepository.findById(id);
	}
	
	public List<Member> findAll() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findByEmailAndType(String email, String type) {
		return memberRepository.findByEmailAndType(email, type);
	}

}