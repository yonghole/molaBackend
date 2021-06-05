package com.mola.mola.service;

import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.DuplicateFileException;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.file_management_util.FileUnzipper;
import com.mola.mola.repository.JpaUserRepository;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.executable.ValidateOnExecution;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ReceiveFileService {

    private final UserRepository userRepository;
    private final OutSourceRepository outSourceRepository;
    private final FileUnzipper fileUnzipper;
    @Value("{zipped-file-location}")
    String directoryPath;
    //String directoryPath = "/Users/gim-yeonghyeon/zipped_file";

    public String saveFile(MultipartFile file, String user_id) throws IOException, IllegalStateException {
        ValidateUserID(Long.parseLong(user_id));
        // User가 존재하는지 확인.
        String returnFilePath = null;

        try {
            Path directory = Paths.get(directoryPath + "/").toAbsolutePath().normalize();
            //Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();

            // directory 해당 경로까지 디렉토리를 모두 만든다.
            Files.createDirectories(directory);

            // 파일명을 바르게 수정한다.
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");

            String fileName = StringUtils.cleanPath(user_id +"_"+ (sdf.format(timestamp))+".zip");

            // 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류 방지)
            Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");
            // 파일을 저장할 경로를 Path 객체로 받는다.
            Path targetPath = directory.resolve(fileName).normalize();
            returnFilePath = targetPath.toString();

            // 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
            Assert.state(!Files.exists(targetPath), fileName + " File already exists.");
            file.transferTo(targetPath);

        } catch (IOException ex) {
            ex.getStackTrace();
        } catch (IllegalStateException ex){
            System.out.println(ex);
            return "Can't write file!";
        }

        return returnFilePath;
    }

    public List<File> unzipFileByFilePath(String filePath) throws IOException{
        File file = fileUnzipper.getZippedFileByFilePath(filePath);
        return fileUnzipper.unzip(file);
    }

    public void ValidateUserID(Long user_id) {
       userRepository.findByUserId(user_id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST_ERROR));
    }

    //외주 아이디가 제대로 등록된 애인지 확인용
    public void ValidateOsID(Long os_id){
        boolean osExists = outSourceRepository.findByID(os_id).isEmpty();
        if(osExists){
            throw new EntityNotFoundException(ErrorCode.OUTSOURCE_ID_INVALID_ERROR);
        }
    }

//    public static class UserIdNullError extends InvalidValueException {
//        public UserIdNullError(ErrorCode errorCode) {
//            super(errorCode);
//        }
//    }
//
//    public static class OSNotValidError extends InvalidValueException {
//        public OSNotValidError(ErrorCode errorCode) {
//            super(errorCode);
//        }
//    }

}
