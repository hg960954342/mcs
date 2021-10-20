package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysUser;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
public interface SysRoleUserService {

        void changeRoleUsers(int roleId, List<Integer> userIdList);

        List<SysUser> getListByRoleId(int roleId);

}
