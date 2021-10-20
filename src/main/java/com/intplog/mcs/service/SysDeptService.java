package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysDept;
import com.intplog.mcs.bean.viewmodel.DeptParam;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
public interface SysDeptService {

        void save(DeptParam param);

        void update(DeptParam param);

        int insert(SysDept record);

        SysDept selectByPrimaryKey(Integer id);

        int updateByPrimaryKey(SysDept record);

        void delete(int deptId);

}
