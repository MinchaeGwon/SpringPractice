package com.example.practice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.domain.Member;
import com.example.practice.domain.MemberDto;
import com.example.practice.response.Response;
import com.example.practice.security.JwtRequest;
import com.example.practice.security.JwtResponse;
import com.example.practice.security.JwtTokenUtil;
import com.example.practice.security.JwtUserDetailsService;
import com.example.practice.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping(value = "/member", produces = {MediaType.APPLICATION_JSON_VALUE})
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/member/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> findById(@PathVariable("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Optional<Member> oUser = memberService.findById(id);
		if (oUser.isPresent()) {
			response.put("result", "SUCCESS");
			response.put("user", oUser.get());
		} else {
			response.put("result", "FAIL");
			response.put("reason", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		}

		return response;
	}

	@GetMapping(value = "/member/list", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> test() {

		Map<String, Object> members = new HashMap<>();

		members.put("result", memberService.findAll());
		members.put("test", "????");

//		for (int i = 1; i <= 5; i++) {
//			Map<String, Object> member = new HashMap<>();
//			member.put("idx", i);
//			member.put("nickname", i + "민채");
//			members.put(i, member);
//		}

		return members;
	}

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

//    @PostMapping("/signin")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        final Member member = userDetailService.authenticateByEmailAndPassword
//                (authenticationRequest.getEmail(), authenticationRequest.getPassword());
//        final String token = jwtTokenUtil.generateToken(member.getEmail());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

	@PostMapping(value = "/signin")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//		String token;
//		
//		try {
//			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//
//			final Member userDetails = (Member) userDetailService.authenticateByEmailAndPassword(
//					authenticationRequest.getEmail(), authenticationRequest.getPassword());
//
//			token = jwtTokenUtil.generateToken(userDetails);
//
//			if (!authenticationRequest.getRegistrationToken().equals(userDetails.getRegistrationToken())) {
//				userDetailService.modifyRegistrationToken(authenticationRequest.getRegistrationToken(), userDetails);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 유저가 없습니다. 회원가입이 필요합니다.");
//		}
//
//		return ResponseEntity.ok(new JwtResponse(token));
		
		String token;
		
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final Member userDetails = (Member) userDetailService.authenticateByEmailAndPassword(
				authenticationRequest.getEmail(), authenticationRequest.getPassword());

		token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping(value = "/signup")
	public Response signup(@RequestBody MemberDto infoDto) { // 회원 추가
		Response response = new Response();

		try {
			userDetailService.save(infoDto);
			response.setResponse("success");
			response.setMessage("회원가입을 성공적으로 완료했습니다.");
		} catch (Exception e) {
			response.setResponse("failed");
			response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
			response.setData(e.toString());
		}
		return response;
	}

}
