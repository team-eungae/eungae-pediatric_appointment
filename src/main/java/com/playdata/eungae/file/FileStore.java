package com.playdata.eungae.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileStore {

	@Value("${file.upload.path}")
	String uploadPath;

	public String getFullPath(String storeFilename) {
		return uploadPath + File.separator + storeFilename;
	}

	public List<ResultFileStore> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
		List<ResultFileStore> storeFileResult = new ArrayList<>();

		if (!multipartFiles.isEmpty()) {
			for (MultipartFile multipartFile : multipartFiles) {
				storeFileResult.add(storeFile(multipartFile));
			}

		}
		return storeFileResult;
	}

	public ResultFileStore storeFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return new ResultFileStore();
		}

		// 파일 이름
		String originalFilename = multipartFile.getOriginalFilename();
		log.info("originalFilename :" + originalFilename);

		// 파일 저장 이름
		String storeFileName = createStoreFileName(originalFilename);
		log.info("storeFileName:" + storeFileName);

		// 폴더 생성
		String folderPath = makeFolder();

		//이미지 저장
		multipartFile.transferTo(new File(getFullPath(storeFileName)));

		return new ResultFileStore(folderPath, storeFileName, originalFilename);
	}

	private String createStoreFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "_" + originalFileName;
	}

	private String makeFolder() {
		String folderPath = uploadPath;
		log.info("folderPath:" + folderPath);
		File uploadPathFolder = new File(folderPath);

		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}

	public ResultFileStore storeProfileFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return new ResultFileStore();
		}

		String originalFilename = multipartFile.getOriginalFilename();
		log.info("originalFilename : " + originalFilename);

		String storeFileName = createStoreFileName(originalFilename);
		log.info("storeFileName " + storeFileName);

		String folderPath = makeProfileFolder();

		multipartFile.transferTo(new File(getFullPath(storeFileName)));

		return new ResultFileStore(folderPath, storeFileName, originalFilename);
	}

	private String makeProfileFolder() {
		String folderPath = uploadPath;
		log.info("folderPath : " + folderPath);
		File uploadPathFolder = new File(folderPath);

		if (!uploadPathFolder.exists()) {
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}

}
