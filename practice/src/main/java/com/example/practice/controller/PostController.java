package com.example.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.dto.PostDto;
import com.example.practice.entity.Member;
import com.example.practice.entity.Post;
import com.example.practice.response.CommonListResponse;
import com.example.practice.response.CommonResponse;
import com.example.practice.response.ErrorResponse;
import com.example.practice.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/post")
	public ResponseEntity<?> getAllPost() {
		List<Post> postList = postService.getAllPost();
		
		return ResponseEntity.ok().body(new CommonListResponse<List<Post>>(postList.size(), postList));
	}
	
	@GetMapping("/mypost")
	public ResponseEntity<?> getMyPost(@AuthenticationPrincipal Member member) {
		List<Post> postList = postService.getMyPost(member);
		
		return ResponseEntity.ok().body(new CommonListResponse<List<Post>>(postList.size(), postList));
	}
	
	@PostMapping("/post")
	public ResponseEntity<?> createRecipePost(@RequestBody PostDto info, @AuthenticationPrincipal Member member) {
		Long postId;

		try {
			postId = postService.save(info, member);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("게시글 작성 실패", "500"));
		}

		return ResponseEntity.ok().body(new CommonResponse<Long>(postId));
	}
	
}
