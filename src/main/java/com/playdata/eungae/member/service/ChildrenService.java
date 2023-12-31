package com.playdata.eungae.member.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.ChildrenDto;
import com.playdata.eungae.member.repository.ChildrenRepository;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChildrenService {

	private final ChildrenRepository childrenRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void createChildren(ChildrenDto childrenDto, MultipartFile photoFile, String email) {

		if (photoFile != null && !photoFile.isEmpty()) {
			String photoPath = savePhotoAndGetPath(photoFile);
			childrenDto.setPhotoPath(photoPath);
		}

		Member member = memberRepository.findByEmail(email).get();
		Children children = Children.from(childrenDto);
		children.setMember(member);

		childrenRepository.save(children);
	}

	private String savePhotoAndGetPath(MultipartFile photoFile) {
		return " ";
	}

	@Transactional
	public void deleteChild(Long id) {
		childrenRepository.deleteById(id);
	}

	private String formatDate(String dateStr) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

		try {
			return outputFormat.format(inputFormat.parse(dateStr));
		} catch (ParseException e) {
			return dateStr;
		}
	}

	@Transactional(readOnly = true)
	public List<ChildrenDto> getAllChildren() {
		return childrenRepository.findAll().stream()
			.map(children -> {
				ChildrenDto dto = ChildrenDto.from(children);
				dto.setBirthDate(formatDate(dto.getBirthDate()));
				return dto;
			})
			.collect(Collectors.toList());
	}
}
