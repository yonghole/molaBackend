package com.mola.mola.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//
//        return upload(uploadFile, dirName);
//    }

    public String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
            if(targetFile.getParentFile().exists()&&
            targetFile.getParentFile().isDirectory()&&
            targetFile.getParentFile().listFiles().length == 0){
                targetFile.getParentFile().delete();
            }
            System.out.println("파일이 삭제 되었습니다.");
        } else {
//            log.info("파일이 삭제되지 못했습니다.");
            System.out.println("파일이 삭제 되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException, IllegalStateException {
        File convertFile = new File(file.getOriginalFilename());
        file.transferTo(convertFile);
        return Optional.ofNullable(convertFile);
    }

}
