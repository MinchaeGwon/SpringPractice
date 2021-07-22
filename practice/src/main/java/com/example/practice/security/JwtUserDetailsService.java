package com.example.practice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.practice.domain.Member;
import com.example.practice.domain.MemberDto;
import com.example.practice.repository.MemberRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	// 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException((email)));
	}

	public UserDetails authenticateByEmailAndPassword(String email, String password) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new BadCredentialsException("Password not matched");
		}

		return member;
	}

	public UserDetails loadUserByEmailAndType(String email, String type) throws UsernameNotFoundException {
		return memberRepository.findByEmailAndType(email, type).orElseThrow(() -> new UsernameNotFoundException((email)));
	}

	@Transactional
	public Long save(MemberDto infoDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoDto.setPassword(encoder.encode(infoDto.getPassword()));

		return memberRepository.save(Member.builder().email(infoDto.getEmail()).auth(infoDto.getAuth())
				.password(infoDto.getPassword()).build()).getId();
	}

}
