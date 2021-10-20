package com.intplog.mcs.service.impl;

import com.google.common.collect.Lists;
import com.intplog.mcs.bean.dto.AclDto;
import com.intplog.mcs.bean.dto.AclModuleLevelDto;
import com.intplog.mcs.bean.viewmodel.Menu;
import com.intplog.mcs.service.SysMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-07-11 14:04
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

        @Override
        public List<Menu> changeTreeToMenu(List<AclModuleLevelDto> aclModuleLevelDtoList) {
                List<Menu> menuList = Lists.newArrayList();
                if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
                        return menuList;
                }

                // 处理菜单
                // 由于样式只适合最多两级，因此这里假设只有两层，尽管实现时层级是不限制多少级的
                for (AclModuleLevelDto firstLevel : aclModuleLevelDtoList) {
                        Menu firstLevelMenu = new Menu();
                        firstLevelMenu.setId(firstLevel.getId());
                        firstLevelMenu.setName(firstLevel.getName());
                        firstLevelMenu.setImg(firstLevel.getImg());

                        boolean needAddFirstMenu = false; // 是否需要增加一级菜单

                        // 首先检查menu下是否已配置了有菜单的权限，如果有，就直接使用对应菜单的权限点的url作为点击后跳转的路径
                        if (CollectionUtils.isNotEmpty(firstLevel.getAclList())) {
                                for (AclDto aclDto : firstLevel.getAclList()) {
                                        if (aclDto.getType() == 1 && StringUtils.isNotBlank(aclDto.getUrl())) { // 类型为菜单, 且配置了url
                                                // 正常每个模块下应该只配置一个菜单的权限点，如果有多个，那是配置的有问题
                                                firstLevelMenu.setUrl(aclDto.getUrl());
                                                needAddFirstMenu = true;
                                                break;
                                        }
                                }
                        }

                        // 目前这个菜单还没有点击跳转的url， 而且下面还有子模块时，继续尝试处理下一层级
                        if (StringUtils.isBlank(firstLevelMenu.getUrl()) && CollectionUtils.isNotEmpty(firstLevel.getAclModuleList())) {
                                for (AclModuleLevelDto sendLevel : firstLevel.getAclModuleList()) {
                                        Menu secondLevelMenu = new Menu();
                                        secondLevelMenu.setId(sendLevel.getId());
                                        secondLevelMenu.setName(sendLevel.getName());
                                        boolean needAddSecondMenu = false; // 是否需要增加二级菜单

                                        if (CollectionUtils.isNotEmpty(sendLevel.getAclList())) {
                                                for (AclDto aclDto : sendLevel.getAclList()) {
                                                        if (aclDto.getType() == 1 && StringUtils.isNotBlank(aclDto.getUrl())) { // 类型为菜单, 且配置了url
                                                                // 正常每个模块下应该只配置一个菜单的权限点，如果有多个，那是配置的有问题
                                                                secondLevelMenu.setUrl(aclDto.getUrl());
                                                                needAddFirstMenu = true;
                                                                needAddSecondMenu = true;
                                                                break;
                                                        }
                                                }
                                        }
                                        if (needAddSecondMenu) { // 需要增加二级菜单
                                                firstLevelMenu.addSubMenu(secondLevelMenu);
                                        }
                                }
                        }

                        if (needAddFirstMenu) { // 需要增加一级菜单
                                menuList.add(firstLevelMenu);
                        }
                }
                return menuList;
        }
}
