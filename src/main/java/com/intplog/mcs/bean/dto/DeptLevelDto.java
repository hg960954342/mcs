package com.intplog.mcs.bean.dto;

import com.google.common.collect.Lists;
import com.intplog.mcs.bean.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */

@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept){

        DeptLevelDto dto = new DeptLevelDto();
        //将dept的值赋值给dto
        BeanUtils.copyProperties(dept,dto);
        return dto;
    }
}
