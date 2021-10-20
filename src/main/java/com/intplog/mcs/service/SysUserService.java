package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import com.intplog.mcs.bean.viewmodel.PageResult;
import com.intplog.mcs.bean.viewmodel.UserParam;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */
public interface SysUserService {

        void save(UserParam param);

        void update(UserParam param);

        SysUser findByKeyword(String keyword);

        PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page);

        List<SysUser> getAll();

}
