package com.intplog.mcs.service;


import com.intplog.mcs.bean.dto.AclModuleLevelDto;
import com.intplog.mcs.bean.dto.DeptLevelDto;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-08
 */

public interface SysTreeService {

        List<DeptLevelDto> deptTree();

        List<AclModuleLevelDto> aclModuleTree();

        List<AclModuleLevelDto> roleTree(int roleId);

        List<AclModuleLevelDto> userAclTree(int userId);
}
