package com.mgpark.imageserver.mapper;

import com.mgpark.imageserver.vo.TbImageTableVo;
import com.mgpark.imageserver.vo.TbImageTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ImageUploadMapper {

    List<TbImageTypeVo> getAllTypeList();

    TbImageTableVo selectImage(
            @Param("TYPE") String type,
            @Param("FILENAME") String fileName
    );

    List<TbImageTableVo> selectImageList(@Param("TYPE") String type);
    boolean existImageType(@Param("TYPE") String type);
    boolean existImage(@Param("TYPE") String type, @Param("FILENAME") String fileName);

    void insertNewImageType(TbImageTypeVo data);
    void insertImage(TbImageTableVo data);
    void deleteImage(
            @Param("TYPE") String type,
            @Param("FILENAME") String fileName
    );
}
