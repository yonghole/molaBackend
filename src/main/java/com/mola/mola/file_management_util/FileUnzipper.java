package com.mola.mola.file_management_util;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnzipper {

    private final String unzippedFilePath = "/Users/gim-yeonghyeon/unzipped_files";

    private final String zipFilePath = "/Users/gim-yeonghyeon/zipped_files/";

    //해당 파일이 존재하는지 확인
    public boolean isExist(String fileName){
        String filePath = zipFilePath + fileName;
        File zipped_file = new File(filePath);
        return zipped_file.exists();
    }

    //해당 파일 객체를 반환한다.
    public File getZippedFileByFilePath(String filePath) throws IOException{
        return new File(filePath);
    }

    public List<File> unzip(File zipped_file) throws IOException {

        List<File> fileList = new ArrayList<>();

        File destDir = new File(unzippedFilePath + "/" + zipped_file.getName());

        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipped_file.getPath()));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        byte[] buffer = new byte[1024];

        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);

            if(newFile == null) {
                zipEntry = zipInputStream.getNextEntry();
                continue;
            }

            fileList.add(newFile);

            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {

                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

            }
            zipEntry = zipInputStream.getNextEntry();
        }

        zipInputStream.closeEntry();
        zipInputStream.close();

        if(zipped_file.delete()){
            System.out.println("파일 삭제 성공");
        }else {
            System.out.println("파일 삭제 실패");
       };

        return fileList;
    }

    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException{

        if(zipEntry.getName().startsWith("_")) return null;

        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
