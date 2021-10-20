package com.intplog.mcs.service.impl;

import com.google.common.base.Preconditions;
import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import com.intplog.mcs.bean.viewmodel.PageResult;
import com.intplog.mcs.bean.viewmodel.UserParam;
import com.intplog.mcs.common.RequestHolder;
import com.intplog.mcs.exception.ParamException;
import com.intplog.mcs.mapper.SysUserMapper;
import com.intplog.mcs.service.SysUserService;
import com.intplog.mcs.utils.BeanValidator;
import com.intplog.mcs.utils.Md5Utils;
import com.intplog.mcs.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */
@Service
public class SysUserServiceImpl implements SysUserService {

        @Resource
        private SysUserMapper sysUserMapper;


        @Override
        public void save(UserParam param) {
                BeanValidator.check(param);
                if (checkTelephoneExist(param.getTelephone(), param.getId())) {
                        throw new ParamException("电话已被占用");
                }
                if (checkEmailExist(param.getMail(), param.getId())) {
                        throw new ParamException("邮箱已被占用");
                }
                String password = PasswordUtil.randomPassword();
                password = "12345678";
                String encryptedPassword = Md5Utils.MD5Encode(password);
                SysUser user = SysUser.builder().userName(param.getUserName()).telephone(param.getTelephone()).mail(param.getMail())
                        .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
                user.setOperator(RequestHolder.getCurrentUser().getUserName());
                user.setOperateTime(new Date());
                user.setOperateIp("127.0.0.1");

                //TODO:send Emil

                sysUserMapper.insert(user);
        }

        @Override
        public void update(UserParam param) {
                BeanValidator.check(param);
                if (checkTelephoneExist(param.getTelephone(), param.getId())) {
                        throw new ParamException("电话已被占用");
                }
                if (checkEmailExist(param.getMail(), param.getId())) {
                        throw new ParamException("邮箱已被占用");
                }
                SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
                Preconditions.checkNotNull(before, "待更新的用户不存在");
                SysUser after = SysUser.builder().id(param.getId()).userName(param.getUserName()).telephone(param.getTelephone()).
                        mail(param.getMail()).password(before.getPassword()).deptId(param.getDeptId()).
                        status(param.getStatus()).remark(param.getRemark()).build();
                after.setOperator("system");
                after.setOperateIp("127.0.0.1");
                after.setOperateTime(new Date());
                sysUserMapper.updateByPrimaryKey(after);
        }

        public boolean checkEmailExist(String mail, Integer userId) {

                return sysUserMapper.countByMail(mail, userId) > 0;
        }

        public boolean checkTelephoneExist(String telephone, Integer userId) {

                return sysUserMapper.countByTelephone(telephone, userId) > 0;
        }

        @Override
        public SysUser findByKeyword(String keyword) {

                return sysUserMapper.findByKeyword(keyword);
        }

        @Override
        public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
                BeanValidator.check(page);
                int count = sysUserMapper.countByDeptId(deptId);
                if (count > 0) {
                        List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
                        return PageResult.<SysUser>builder().total(count).data(list).build();
                }
                return PageResult.<SysUser>builder().build();
        }

        @Override
        public List<SysUser> getAll() {
                return sysUserMapper.getAll();
        }
}
