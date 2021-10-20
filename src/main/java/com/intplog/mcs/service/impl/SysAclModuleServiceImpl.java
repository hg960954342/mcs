package com.intplog.mcs.service.impl;

import com.google.common.base.Preconditions;
import com.intplog.mcs.bean.model.SysAclModule;
import com.intplog.mcs.bean.viewmodel.AclModuleParam;
import com.intplog.mcs.exception.ParamException;
import com.intplog.mcs.mapper.SysAclMapper;
import com.intplog.mcs.mapper.SysAclModuleMapper;
import com.intplog.mcs.service.SysAclModuleService;
import com.intplog.mcs.utils.BeanValidator;
import com.intplog.mcs.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

        @Resource
        private SysAclModuleMapper sysAclModuleMapper;

        @Resource
        private SysAclMapper sysAclMapper;

        @Override
        public void save(AclModuleParam param) {

                BeanValidator.check(param);
                if (checkExist(param.getParentId(), param.getName(), param.getId())) {
                        throw new ParamException("同一层级下存在相同名称的权限模块");
                }
                SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                        .status(param.getStatus()).remark(param.getRemark()).build();
                aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
                aclModule.setOperator("system");
                aclModule.setOperateIp("127.0.0.1");
                aclModule.setOperateTime(new Date());
                sysAclModuleMapper.insert(aclModule);

        }

        @Override
        public void update(AclModuleParam param) {

                BeanValidator.check(param);
                if (checkExist(param.getParentId(), param.getName(), param.getId())) {
                        throw new ParamException("同一层级下存在相同名称的权限模块");
                }
                SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
                Preconditions.checkNotNull(before, "待更新的权限模块不存在");

                SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                        .status(param.getStatus()).remark(param.getRemark()).build();
                after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
                after.setOperator("system");
                after.setOperateIp("127.0.0.1");
                after.setOperateTime(new Date());

                updateWithChild(before, after);

        }

        @Transactional
        public void updateWithChild(SysAclModule before, SysAclModule after) {
                String newLevelPrefix = after.getLevel();
                String oldLevelPrefix = before.getLevel();
                if (!after.getLevel().equals(before.getLevel())) {
                        String curLevel = before.getLevel() + "." + before.getId();
                        List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(curLevel + "%");
                        if (CollectionUtils.isNotEmpty(aclModuleList)) {
                                for (SysAclModule aclModule : aclModuleList) {
                                        String level = aclModule.getLevel();
                                        if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                                                // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                                                // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                                                level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                                                aclModule.setLevel(level);
                                        }
                                }
                                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
                        }
                }
                sysAclModuleMapper.updateByPrimaryKey(after);
        }

        @Override
        public void delete(int aclModuleId) {
                SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
                Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
                if (sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
                        throw new ParamException("当前模块下面有子模块，无法删除");
                }
                if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
                        throw new ParamException("当前模块下面有用户，无法删除");
                }
                sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);

        }


        private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
                return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
        }


        private String getLevel(Integer aclModuleId) {
                SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
                if (aclModule == null) {
                        return null;
                }
                return aclModule.getLevel();
        }
}
