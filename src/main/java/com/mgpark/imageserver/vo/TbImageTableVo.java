package com.mgpark.imageserver.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("TbImageTableVo")
public class TbImageTableVo {
    private int SN;
    private String TYPE;
    private String FILENAME;
    private String DIR;
    private String DESCRIPTION;
}
