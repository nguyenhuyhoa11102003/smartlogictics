package com.tdtu.logistics_files_storage_service.controller;

import com.tdtu.logistics_files_storage_service.dto.response.ApiResponse;
import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.exception.AppException;
import com.tdtu.logistics_files_storage_service.exception.ErrorCode;
import com.tdtu.logistics_files_storage_service.service.S3ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3ClientController {

    private final S3ClientService s3ClientService;

    @PostMapping("/upload")
    public ApiResponse<FileRecordResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filePath") String filePath,
            @RequestParam(value = "acl", required = false, defaultValue = "private") String acl) {

        ObjectCannedACL cannedACL = ObjectCannedACL.valueOf(acl.toUpperCase());
        try {
            log.info("Uploading file {} to bucket {}", file.getOriginalFilename(), filePath);

            Path tmpPath = Paths.get(filePath, file.getOriginalFilename());
            File tempFile = File.createTempFile(tmpPath.toString(), null);
            file.transferTo(tempFile);

            log.info("Temp file created: {}, file name: {}", tempFile.getAbsolutePath(), tempFile.getName());
            String s3FilePath = Paths.get(filePath, file.getOriginalFilename()).toString()
                    .replace("\\", "/");

            FileRecordResponse response = s3ClientService.uploadFile(s3FilePath, tempFile, cannedACL);

            tempFile.deleteOnExit();

            return ApiResponse.<FileRecordResponse>builder()
                    .code(HttpStatus.OK.value())
                    .result(response)
                    .isSuccess(true)
                    .message("File uploaded successfully.")
                    .build();
        } catch (IOException e) {
            throw new AppException(ErrorCode.EMAIL_INVALID);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam("filePath") String filePath) {
        try {
            // Lấy tệp từ S3 và chuyển nó sang ByteArrayResource
            ResponseInputStream<GetObjectResponse> responseInputStream = s3ClientService.getFile(filePath);
            byte[] fileContent = responseInputStream.readAllBytes();
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            String fileName = Paths.get(filePath).getFileName().toString();
            return customAttachmentResponse(resource, fileName);
        } catch (Exception e) {
            log.error("Download Error for file {}: {}", filePath, e.getMessage());
            throw new AppException(ErrorCode.S3_DOWNLOAD_FAILED);
        }
    }

    @GetMapping("/display")
    public ResponseEntity<?> viewImage(@RequestParam("filePath") String filePath) {
        try {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3ClientService.getFile(filePath);
            byte[] fileContent = responseInputStream.readAllBytes();
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            String fileName = Paths.get(filePath).getFileName().toString();
            return customDisplayImageResponse(resource, fileName);
        } catch (Exception e) {
            log.error("Error viewing image {}: {}", filePath, e.getMessage());
            throw new AppException(ErrorCode.RESOURCES_NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{fileId}")
    public ApiResponse<?> deleteFile(@PathVariable String fileId) {
        try {
            s3ClientService.deleteFile(fileId);

            log.info("File deleted successfully: {}", fileId);

            return ApiResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .isSuccess(true)
                    .message("File deleted successfully.")
                    .build();
        } catch (Exception e) {
            throw new AppException(ErrorCode.S3_DELETE_FAILED);
        }
    }

    private ResponseEntity<?> customAttachmentResponse(ByteArrayResource resource, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        String mediaType = new Tika().detect(resource.getInputStream());
        log.info("MediaType detected: {}", mediaType);
        headers.setContentType(MediaType.parseMediaType(mediaType));
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    private ResponseEntity<?> customDisplayImageResponse(ByteArrayResource resource, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());

        String mediaType = new Tika().detect(resource.getInputStream());

        log.debug("Display images....");

        headers.setContentType(MediaType.parseMediaType(mediaType));
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
