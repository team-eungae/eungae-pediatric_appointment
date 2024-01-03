package com.playdata.eungae.member.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.dto.ChildrenDto;
import com.playdata.eungae.member.repository.ChildrenRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ChildrenService {
	private final ChildrenRepository childrenRepository;

	@Autowired
	public ChildrenService(ChildrenRepository childrenRepository) {

		this.childrenRepository = childrenRepository;
	}
	// 자녀 추가 (사진 파일 처리 로직 추가할 것)
	@Transactional
	public ChildrenDto createChild(ChildrenDto childrenDto, MultipartFile photoFile) {
		if (!photoFile.isEmpty()) {
			try {
				byte[] photoContent = photoFile.getBytes();
				childrenDto.setPhotoContent(photoContent); // DTO에 사진 데이터 설정
			} catch (IOException e) {
				throw new RuntimeException("정상 업로드가 안되었습니다", e);
			}
		}
		Children children = Children.from(childrenDto);
		children = childrenRepository.save(children);
		return ChildrenDto.from(children);
	}

	/*	private String savePhotoFile(MultipartFile photoFile) {
		return "";
	}
*/
	//자녀 정보 조회
	@Transactional(readOnly = true)
	public ChildrenDto getChildById(Long id) {
		Children children = childrenRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 자녀 정보를 찾을 수 없습니다. " + id));
		return ChildrenDto.from(children);
	}

	//자녀 정보 수정
	@Transactional
	public ChildrenDto updateChild(Long id, ChildrenDto childrenDto) {
		Children children = childrenRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 자녀의 정보를 찾을 수 없습니다.:" + id));

		children.updateFromDto(childrenDto);

		children = childrenRepository.save(children);
		return ChildrenDto.from(children);
	}

	//자녀 삭제
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
			// 날짜 파싱에 실패한 경우, 원본 문자열 반환 혹은 적절한 예외 처리
			return dateStr;
		}
	}

	@Transactional(readOnly = true)
	public List<ChildrenDto> getAllChildren() {
		return childrenRepository.findAll().stream()
			.map(children -> {
				ChildrenDto dto = ChildrenDto.from(children);
				dto.setBirthDate(formatDate(dto.getBirthDate())); // 날짜 형식 변환
				return dto;
			})
			.collect(Collectors.toList());
	}


	// 기타 메소드...
}

