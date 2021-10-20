package com.intplog.mcs.service;


import com.intplog.mcs.bean.model.SysAcl;
import com.intplog.mcs.bean.viewmodel.AclParam;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import com.intplog.mcs.bean.viewmodel.PageResult;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */

public interface SysAclService {

        void save(AclParam param);

        void update(AclParam param);

        PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page);


}
