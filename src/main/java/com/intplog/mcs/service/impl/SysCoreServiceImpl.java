package com.intplog.mcs.service.impl;

import com.google.common.collect.Lists;
import com.intplog.mcs.bean.model.SysAcl;
import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.common.RequestHolder;
import com.intplog.mcs.mapper.SysAclMapper;
import com.intplog.mcs.mapper.SysRoleAclMapper;
import com.intplog.mcs.mapper.SysRoleUserMapper;
import com.intplog.mcs.mapper.SysUserMapper;
import com.intplog.mcs.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liaoliming
 * @Date 2019-09-09
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

        @Resource
        private SysAclMapper sysAclMapper;
        @Resource
        private SysRoleUserMapper sysRoleUserMapper;
        @Resource
        private SysRoleAclMapper sysRoleAclMapper;
        @Resource
        private SysUserMapper sysUserMapper;

//    @Resource
//    private SysCacheService sysCacheService;

        @Override
        public List<SysAcl> getCurrentUserAclList() {
                int userId = RequestHolder.getCurrentUser().getId();
                return getUserAclList(userId);
        }

        @Override
        public List<SysAcl> getRoleAclList(int roleId) {
                List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
                if (CollectionUtils.isEmpty(aclIdList)) {
                        return Lists.newArrayList();
                }
                return sysAclMapper.getByIdList(aclIdList);
        }

        @Override
        public List<SysAcl> getUserAclList(int userId) {
                if (isSuperAdmin()) {
                        return sysAclMapper.getAll();
                }
                List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
                if (CollectionUtils.isEmpty(userRoleIdList)) {
                        return Lists.newArrayList();
                }
                List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
                if (CollectionUtils.isEmpty(userAclIdList)) {
                        return Lists.newArrayList();
                }
                return sysAclMapper.getByIdList(userAclIdList);
        }

        public boolean isSuperAdmin() {
                // ???????????????????????????????????????????????????????????????????????????????????????????????????
                // ????????????????????????????????????????????????????????????????????????????????????
                SysUser sysUser = RequestHolder.getCurrentUser();
                if (sysUser.getMail().contains("admin")) {
                        return true;
                }
                return false;
        }

        @Override
        public boolean hasUrlAcl(String url) {
                if (isSuperAdmin()) {
                        return true;
                }
                List<SysAcl> aclList = sysAclMapper.getByUrl(url);
                if (CollectionUtils.isEmpty(aclList)) {
                        return true;
                }

                List<SysAcl> userAclList = getCurrentUserAclList();
                Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

                boolean hasValidAcl = false;
                // ?????????????????????????????????????????????????????????????????????????????????
                for (SysAcl acl : aclList) {
                        // ????????????????????????????????????????????????????????????
                        if (acl == null || acl.getStatus() != 1) { // ???????????????
                                continue;
                        }
                        hasValidAcl = true;
                        if (userAclIdSet.contains(acl.getId())) {
                                return true;
                        }
                }
                if (!hasValidAcl) {
                        return true;
                }
                return false;
        }

//    public List<SysAcl> getCurrentUserAclListFromCache() {
//        int userId = RequestHolder.getCurrentUser().getId();
//        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
//        if (StringUtils.isBlank(cacheValue)) {
//            List<SysAcl> aclList = getCurrentUserAclList();
//            if (CollectionUtils.isNotEmpty(aclList)) {
//                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
//            }
//            return aclList;
//        }
//        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
//        });
//    }
}
