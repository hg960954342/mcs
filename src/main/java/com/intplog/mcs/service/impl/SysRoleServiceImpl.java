package com.intplog.mcs.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.intplog.mcs.bean.model.SysRole;
import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.RoleParam;
import com.intplog.mcs.exception.ParamException;
import com.intplog.mcs.mapper.SysRoleAclMapper;
import com.intplog.mcs.mapper.SysRoleMapper;
import com.intplog.mcs.mapper.SysRoleUserMapper;
import com.intplog.mcs.mapper.SysUserMapper;
import com.intplog.mcs.service.SysRoleService;
import com.intplog.mcs.utils.BeanValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liaoliming
 * @Date 2019-09-09
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

        @Resource
        private SysRoleMapper sysRoleMapper;
        @Resource
        private SysRoleUserMapper sysRoleUserMapper;
        @Resource
        private SysRoleAclMapper sysRoleAclMapper;
        @Resource
        private SysUserMapper sysUserMapper;
//    @Resource
//    private SysLogService sysLogService;

        @Override
        public void save(RoleParam param) {
                BeanValidator.check(param);
                if (checkExist(param.getName(), param.getId())) {
                        throw new ParamException("角色名称已经存在");
                }
                SysRole role = SysRole.builder().name(param.getName()).status(param.getStatus()).type(param.getType())
                        .remark(param.getRemark()).build();
                role.setOperator("system");
                role.setOperateIp("127.0.0.1");
                role.setOperateTime(new Date());
                sysRoleMapper.insert(role);
//        sysLogService.saveRoleLog(null, role);
        }

        @Override
        public void update(RoleParam param) {
                BeanValidator.check(param);
                if (checkExist(param.getName(), param.getId())) {
                        throw new ParamException("角色名称已经存在");
                }
                SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
                Preconditions.checkNotNull(before, "待更新的角色不存在");

                SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus()).type(param.getType())
                        .remark(param.getRemark()).build();
                after.setOperator("system");
                after.setOperateIp("127.0.0.1");
                after.setOperateTime(new Date());
                sysRoleMapper.updateByPrimaryKey(after);
//        sysLogService.saveRoleLog(before, after);
        }

        @Override
        public List<SysRole> getAll() {
                return sysRoleMapper.getAll();
        }

        @Override
        public List<SysRole> getRoleListByUserId(int userId) {
                List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
                if (CollectionUtils.isEmpty(roleIdList)) {
                        return Lists.newArrayList();
                }
                return sysRoleMapper.getByIdList(roleIdList);
        }

        private boolean checkExist(String name, Integer id) {
                return sysRoleMapper.countByName(name, id) > 0;
        }


        @Override
        public List<SysRole> getRoleListByAclId(int aclId) {
                List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
                if (CollectionUtils.isEmpty(roleIdList)) {
                        return Lists.newArrayList();
                }
                return sysRoleMapper.getByIdList(roleIdList);
        }

        @Override
        public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
                if (CollectionUtils.isEmpty(roleList)) {
                        return Lists.newArrayList();
                }
                List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
                List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
                if (CollectionUtils.isEmpty(userIdList)) {
                        return Lists.newArrayList();
                }
                return sysUserMapper.getByIdList(userIdList);
        }

}
