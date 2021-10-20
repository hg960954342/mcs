package com.intplog.mcs.service.impl;

import com.google.common.base.Preconditions;
import com.intplog.mcs.bean.model.SysAcl;
import com.intplog.mcs.bean.viewmodel.AclParam;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import com.intplog.mcs.bean.viewmodel.PageResult;
import com.intplog.mcs.exception.ParamException;
import com.intplog.mcs.mapper.SysAclMapper;
import com.intplog.mcs.service.SysAclService;
import com.intplog.mcs.utils.BeanValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-08-22
 */
@Service
public class SysAclServiceImpl implements SysAclService {
        @Resource
        private SysAclMapper sysAclMapper;
//    @Resource
//    private SysLogService sysLogService;

        @Override
        public void save(AclParam param) {
                BeanValidator.check(param);
                if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
                        throw new ParamException("当前权限模块下面存在相同名称的权限点");
                }
                SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                        .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
                acl.setCode(generateCode());
                acl.setOperator("system");
                acl.setOperateTime(new Date());
                acl.setOperateIp("127.0.0.1");
                sysAclMapper.insert(acl);
//        sysLogService.saveAclLog(null, acl);
        }

        @Override
        public void update(AclParam param) {
                BeanValidator.check(param);
                if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
                        throw new ParamException("当前权限模块下面存在相同名称的权限点");
                }
                SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
                Preconditions.checkNotNull(before, "待更新的权限点不存在");

                SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                        .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
                after.setOperator("system");
                after.setOperateTime(new Date());
                after.setOperateIp("127.0.0.1");

                sysAclMapper.updateByPrimaryKeySelective(after);
//        sysLogService.saveAclLog(before, after);
        }

        public boolean checkExist(int aclModuleId, String name, Integer id) {
                return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
        }

        public String generateCode() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
        }

        @Override
        public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
                BeanValidator.check(page);
                int count = sysAclMapper.countByAclModuleId(aclModuleId);
                if (count > 0) {
                        List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
                        return PageResult.<SysAcl>builder().data(aclList).total(count).build();
                }
                return PageResult.<SysAcl>builder().build();
        }


}
