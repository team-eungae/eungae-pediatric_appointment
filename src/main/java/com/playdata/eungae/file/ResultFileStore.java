package com.playdata.eungae.file;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultFileStore {
    private String folderPath;
    private String storeFileName;
    private String originalFileName;

    public String getFullPath() {
        return folderPath + File.separator + storeFileName;
    }
}
