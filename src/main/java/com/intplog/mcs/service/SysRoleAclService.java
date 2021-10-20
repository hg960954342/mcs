package com.intplog.mcs.service;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
public interface SysRoleAclService {

        void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}
