package com.intplog.mcs.service;

import com.intplog.mcs.bean.dto.AclModuleLevelDto;
import com.intplog.mcs.bean.viewmodel.Menu;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-09
 */
public interface SysMenuService {

        public List<Menu> changeTreeToMenu(List<AclModuleLevelDto> aclModuleLevelDtoList);
}
