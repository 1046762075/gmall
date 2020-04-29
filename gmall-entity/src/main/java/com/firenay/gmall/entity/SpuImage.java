package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/28 21:40
 */
@Data
public class SpuImage  implements Serializable {

    @Column
    @Id
    private String id;

    @Column
    private String spuId;

    @Column
    private String imgName;

    @Column
    private String imgUrl;
}
