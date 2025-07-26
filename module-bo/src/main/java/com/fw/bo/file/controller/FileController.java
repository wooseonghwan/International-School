package com.fw.bo.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    @Value("${file.upload.editor_path}")
    private String UPLOAD_EDITOR_PATH;

    @PostMapping( "/bo/system/file/fileUpload")
    public String fileUpload(HttpServletRequest req, HttpServletResponse res, MultipartHttpServletRequest multiFile, @RequestParam MultipartFile upload) throws Exception {

        // 랜덤 문자 생성
        UUID uid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");

        try {
            // 파일 이름 가져오기
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String today = dateFormat.format(new Date());

            String folderPath = UPLOAD_EDITOR_PATH + "/" + today;

            String ckUploadPath = folderPath + "/" + uid + "_" + fileName;
            File folder = new File(folderPath);

            log.info("uploadPath :: {}", folderPath); // 이미지 저장 경로 log 확인

            // 해당 디렉토리 확인
            if (!folder.exists()) {
                try {
                    folder.mkdirs(); // 폴더 생성
                } catch (Exception e) {
                    log.error("Error", e);
                }
            }

            out = new FileOutputStream(ckUploadPath);
            out.write(bytes);

            String callback = req.getParameter("CKEditorFuncNum");
            printWriter = res.getWriter();

            String callBackHtml = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <script type='text/javascript'>";
            callBackHtml += "window.parent.CKEDITOR.tools.callFunction(" + callback + ",'/upload/editor/" + today + "/" + uid + "_" + fileName + "','확인을 눌러주세요.');";
            callBackHtml += "</script>";

            res.getWriter().write(callBackHtml);

        } catch (IOException e) {
            log.error("Error", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                log.error("Error", e);
            }
        }
        return null;
    }
    // TinyMCE 전용 업로드 메서드 추가
    @PostMapping("/bo/system/file/tinymceUpload")
    public ResponseEntity<?> fileUploadForTinyMCE(@RequestParam("file") MultipartFile file) {
        UUID uid = UUID.randomUUID();
        OutputStream out = null;

        try {
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String today = dateFormat.format(new Date());

            String folderPath = UPLOAD_EDITOR_PATH + "/" + today;
            String filePath = folderPath + "/" + uid + "_" + fileName;

            // 폴더 생성
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 파일 저장
            out = new FileOutputStream(filePath);
            out.write(bytes);

            // 업로드된 이미지의 URL 생성
            String fileUrl = "/upload/editor/" + today + "/" + uid + "_" + fileName;

            // JSON 형태로 URL 반환
            return ResponseEntity.ok().body("{\"url\": \"" + fileUrl + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
