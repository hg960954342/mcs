package com.intplog.mcs.bean.model.WmsModel;

import lombok.*;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/11 14:37
 */
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class BucketDetectionDataDto {
    private String target;
    private Integer full;
}
