package com.intplog.mcs.service;

import com.intplog.mcs.bean.viewmodel.AclModuleParam;

public interface SysAclModuleService {

        void save(AclModuleParam param);

        void update(AclModuleParam param);

        void delete(int aclModuleId);
}
