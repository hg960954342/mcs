package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysRole;
import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.RoleParam;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-08
 */
public interface SysRoleService {

        void save(RoleParam param);

        void update(RoleParam param);

        List<SysRole> getAll();

        List<SysRole> getRoleListByUserId(int userId);

        List<SysRole> getRoleListByAclId(int aclId);

        List<SysUser> getUserListByRoleList(List<SysRole> roleList);

}
