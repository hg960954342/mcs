package com.intplog.mcs.bean.model.WmsModel;

import lombok.*;

@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class WmsTaskList {

    private String taskId;
    private String lineId;
    private String target;
    private Integer type;
    private String dateTime;

}
