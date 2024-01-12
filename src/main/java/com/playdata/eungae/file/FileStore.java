package com.playdata.eungae.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
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

	public ResultFileStore storeFile(MultipartFile multipartFile){
		if (multipartFile.isEmpty()) {
			return new ResultFileStore();
		}

		// 파일 이름
		String originalFilename = multipartFile.getOriginalFilename();
		// 파일 저장 이름
		String storeFileName = createStoreFileName(originalFilename);
		// 폴더 생성
		String folderPath = makeFolder();

		//이미지 저장
		try {
			multipartFile.transferTo(new File(getFullPath(storeFileName)));
		} catch (IOException e) {
			throw new RuntimeException("File {%s} path is null".formatted(multipartFile.getOriginalFilename()),e);
		}

		return new ResultFileStore(folderPath, storeFileName, originalFilename);
	}

	private String createStoreFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "_" + originalFileName;
	}

	private String makeFolder() {
		String folderPath = uploadPath;
		File uploadPathFolder = new File(folderPath);

		if (!uploadPathFolder.exists()) {
			uploadPathFolder.mkdirs();
		}

		return folderPath;
	}

	public ResultFileStore storeProfileFile(MultipartFile multipartFile){
		if (multipartFile.isEmpty()) {
			return new ResultFileStore();
		}

		String originalFilename = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);
		String folderPath = makeProfileFolder();

		try {
			multipartFile.transferTo(new File(getFullPath(storeFileName)));
		} catch (IOException e) {
			throw new RuntimeException("File {%s} path is null".formatted(multipartFile.getOriginalFilename()),e);
		}

		return new ResultFileStore(folderPath, storeFileName, originalFilename);
	}

	private String makeProfileFolder() {
		String folderPath = uploadPath;
		File uploadPathFolder = new File(folderPath);

		if (!uploadPathFolder.exists()) {
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}

}
