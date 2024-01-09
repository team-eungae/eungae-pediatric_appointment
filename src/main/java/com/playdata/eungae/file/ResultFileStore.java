package com.playdata.eungae.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultFileStore {
    private String folderPath;
    private String storeFileName;
}
