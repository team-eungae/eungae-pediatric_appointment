package com.playdata.eungae.hospital.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalImage;
import com.playdata.eungae.hospital.dto.HospitalImageResponseDto;
import com.playdata.eungae.hospital.repository.HospitalImageRepository;
import com.playdata.eungae.hospital.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalImageService {
	private final HospitalImageRepository hospitalImageRepository;
	private final HospitalRepository hospitalRepository;
	private final FileStore fileStore;

	@Transactional
	public void saveHospitalImage(MultipartFile file, Long hospitalSeq) {
		HospitalImage hospitalImage;
		Hospital hospital = hospitalRepository.findById(hospitalSeq)
			.orElseThrow(() -> new NoSuchElementException("Hospital not found"));
		try {
			ResultFileStore resultFileStore = fileStore.storeFile(file);
			hospitalImage = HospitalImage.builder()
				.storeFileName(resultFileStore.getStoreFileName())
				.originFileName(file.getOriginalFilename())
				.build();
			hospitalImage.setHospital(hospital);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		hospitalImageRepository.save(hospitalImage);
	}

	@Transactional(readOnly = true)
	public List<HospitalImageResponseDto> findhospitalImages(Long hospitalSeq) {
		List<HospitalImage> hospitalImageList = hospitalImageRepository.findAllByHospitalHospitalSeq(hospitalSeq);

		return hospitalImageList.stream()
			.map(HospitalImageResponseDto::toDto)
			.collect(Collectors.toList());
	}
}
