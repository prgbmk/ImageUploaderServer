package com.mgpark.imageserver.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("TbImageTypeVo")
public class TbImageTypeVo {
    private int SN;
    private String TYPE;
}
