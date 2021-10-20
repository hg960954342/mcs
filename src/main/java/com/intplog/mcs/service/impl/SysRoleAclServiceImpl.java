package com.intplog.mcs.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intplog.mcs.bean.model.SysRoleAcl;
import com.intplog.mcs.common.RequestHolder;
import com.intplog.mcs.mapper.SysRoleAclMapper;
import com.intplog.mcs.service.SysRoleAclService;
import com.intplog.mcs.utils.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

        @Resource
        private SysRoleAclMapper sysRoleAclMapper;

        //    @Resource
//    private SysLogMapper sysLogMapper;
        @Override
        public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
                List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
                if (originAclIdList.size() == aclIdList.size()) {
                        Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
                        Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
                        originAclIdSet.removeAll(aclIdSet);
                        if (CollectionUtils.isEmpty(originAclIdSet)) {
                                return;
                        }
                }
                updateRoleAcls(roleId, aclIdList);
//        saveRoleAclLog(roleId, originAclIdList, aclIdList);
        }

        @Transactional
        public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
                sysRoleAclMapper.deleteByRoleId(roleId);

                if (CollectionUtils.isEmpty(aclIdList)) {
                        return;
                }
                List<SysRoleAcl> roleAclList = Lists.newArrayList();
                for (Integer aclId : aclIdList) {
                        SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getUserName())
                                .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
                        roleAclList.add(roleAcl);
                }
                sysRoleAclMapper.batchInsert(roleAclList);
        }

//    private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
//        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
//        sysLog.setType(LogType.TYPE_ROLE_ACL);
//        sysLog.setTargetId(roleId);
//        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
//        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        sysLog.setOperateTime(new Date());
//        sysLog.setStatus(1);
//        sysLogMapper.insertSelective(sysLog);
//    }

}
