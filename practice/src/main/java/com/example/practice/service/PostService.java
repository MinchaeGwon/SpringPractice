package com.example.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.practice.dto.PostDto;
import com.example.practice.entity.Member;
import com.example.practice.entity.Post;
import com.example.practice.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	public List<Post> getAllPost() {
		return postRepo.findAll();
	}
	
	public List<Post> getMyPost(Member member) {
		return postRepo.findByMember(member);
	}
	
	@Transactional
	public Long save(PostDto info, Member member) {
		Long post_id = postRepo.save(Post.builder().title(info.getTitle()).content(info.getContent()).member(member).build()).getId();

		return post_id;
	}
	
}
