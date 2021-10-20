package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysAcl;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-08
 */
public interface SysCoreService {

        List<SysAcl> getCurrentUserAclList();

        List<SysAcl> getRoleAclList(int roleId);

        List<SysAcl> getUserAclList(int userId);

        boolean hasUrlAcl(String url);
}
