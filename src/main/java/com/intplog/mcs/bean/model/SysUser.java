package com.intplog.mcs.bean.model;

import lombok.*;

import java.util.Date;

/**
 * @author liaoliming
 * @create 2019-08-22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SysUser {
    private Integer id;

    private String userName;

    private String telephone;

    private String mail;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    private String role;

    private String sex;

}