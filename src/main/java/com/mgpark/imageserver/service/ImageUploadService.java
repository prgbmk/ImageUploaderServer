package com.mgpark.imageserver.service;

import com.mgpark.imageserver.config.Properties;
import com.mgpark.imageserver.mapper.ImageUploadMapper;
import com.mgpark.imageserver.vo.TbImageTableVo;
import com.mgpark.imageserver.vo.TbImageTypeVo;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ImageUploadService {

    private final Properties properties;
    private final ImageUploadMapper imageuploadMapper;

    @Autowired
    public ImageUploadService(Properties properties, ImageUploadMapper imageUploadMapper) {
        this.properties = properties;
        this.imageuploadMapper = imageUploadMapper;
    }

    /**
     * Get File's Directory.
     * @param type type.
     * @param fileName fileName.
     * @return Directory.
     */
    public byte[] getImageStream(String type, String fileName) throws IOException {
        TbImageTableVo dbVo = imageuploadMapper.selectImage(type, fileName);

        if(dbVo != null) {
            File imageFile = new File(dbVo.getDIR());
            if(imageFile.exists()) {
                InputStream in = new FileInputStream(imageFile);
                return IOUtils.toByteArray(in);
            }
        }
        return new byte[0];
    }

    public void uploadImage(String type, String description, MultipartRequest multipartRequest) throws IOException {
        if (multipartRequest.getFileMap().containsKey("file")) {
            MultipartFile multipartFile = multipartRequest.getFile("file");

            String fileName = multipartRequest.getFile("file").getOriginalFilename();
            Path filePath = Paths.get(properties.getImageSaveFolder(), type, fileName);

            if(!filePath.getParent().toFile().exists()) {
                FileUtils.forceMkdir(filePath.getParent().toFile());
            }

            FileOutputStream fileSaveStream = new FileOutputStream(filePath.toFile());

            FileCopyUtils.copy(multipartFile.getInputStream(), fileSaveStream);

            TbImageTableVo dbVo = new TbImageTableVo();
            dbVo.setTYPE(type);
            dbVo.setFILENAME(fileName);
            dbVo.setDIR(filePath.toString());
            dbVo.setDESCRIPTION(description);
            uploadImage(dbVo);
        }
    }

    public void uploadImage(TbImageTableVo data) {
        if(!imageuploadMapper.existImageType(data.getTYPE())) {
            TbImageTypeVo newTypeData = new TbImageTypeVo();
            newTypeData.setTYPE(data.getTYPE());
            imageuploadMapper.insertNewImageType(newTypeData);
        }
        if(!imageuploadMapper.existImage(data.getTYPE(), data.getFILENAME())){
            imageuploadMapper.insertImage(data);
        }
    }

    public Map getImageList(String type) {
        Map<String, Object> results = new LinkedHashMap<>();
        List<TbImageTableVo> searchResults = imageuploadMapper.selectImageList(type);
        if(!searchResults.isEmpty()) {
            List<Map<String, String>> items = new LinkedList<>();
            for (TbImageTableVo searchResult : searchResults) {
                items.add(new HashMap<String, String>(){{
                    put("TYPE", searchResult.getTYPE());
                    put("FILENAME", searchResult.getFILENAME());
                    put("DESCRIPTION", searchResult.getDESCRIPTION());
                }});
            }

            results.put("results", items);
        }

        return results;
    }

    public Map getImageTypeList() {
        Map<String, Object> result = new HashMap<>();

        List<TbImageTypeVo> typeList = imageuploadMapper.getAllTypeList();
        List<String> items = new LinkedList<>();
        typeList.forEach(type -> items.add(type.getTYPE()));
        result.put("types", items);

        return result;
    }

    public void deleteImage(String type, String fileName) {
        imageuploadMapper.deleteImage(type, fileName);
    }
}
