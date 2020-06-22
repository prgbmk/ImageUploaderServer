package com.mgpark.imageserver.controller;

import com.mgpark.imageserver.service.ImageUploadService;
import com.mgpark.imageserver.util.LogExecuteTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class ImageUploadController {

    private final ImageUploadService service;

    /**
     * Parameter will be injected by Spring Framework DI.
     * @param service
     */
    public ImageUploadController(ImageUploadService service) {
        this.service = service;
    }


    @ResponseBody
    @GetMapping("/getlist")
    @LogExecuteTime
    public Map getImageTypeList() {
        return this.service.getImageTypeList();
    }

    /**
     * 이미지 리스트 가져오기.
     * @param type 이미지 타입(분류)
     * @return 이미지 리스트.
     */
    @ResponseBody
    @GetMapping("/getimagelist")
    @LogExecuteTime
    public Map getImageList(
            @RequestParam("type") String type
    ) {
        return this.service.getImageList(type);
    }

    /**
     * 이미지 스트림 가져오기.
     * @param type Type.
     * @param fileName FileName.
     * @return 이미지 스트림.
     */
    @ResponseBody
    @GetMapping("/getimage")
    @LogExecuteTime
    public byte[] getImageStream(
            @RequestParam("type") String type,
            @RequestParam("fileName") String fileName
    ) throws IOException {
        return this.service.getImageStream(type, fileName);
    }

    /**
     * 이미지 업로드.
     * @param type Type.
     * @param multipartRequest Multipart Request.
     * @throws IOException IOException.
     */
    @ResponseBody
    @PostMapping("/uploadimage")
    @LogExecuteTime
    public void uploadImage(
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            MultipartRequest multipartRequest
    ) throws IOException {
        service.uploadImage(type, description, multipartRequest);
    }

    /**
     * 이미지 삭제.
     * @param type type.
     * @param fileName fileName.
     * @param request request.
     */
    @ResponseBody
    @DeleteMapping("/deleteimage")
    @LogExecuteTime
    public void deleteImage(
            @RequestParam("type") String type,
            @RequestParam("fileName") String fileName,
            HttpServletRequest request
    ) {
        service.deleteImage(type, fileName);
    }
}
