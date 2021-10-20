package com.intplog.mcs.bean.viewmodel;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-07-11 14:01
 */
@Getter
@Setter
@ToString
public class Menu {

    private Integer id;

    // 菜单展示的名称
    private String name;

    // 菜单点击跳转的链接
    private String url;

    // 菜单前的小图标
    private String img;

    // 下级菜单， 如果存在url里，就不存在下级菜单了
    private List<Menu> subList = Lists.newArrayList();

    public void addSubMenu(Menu menu) {
        if (menu != null) {
            subList.add(menu);
        }
    }
}
