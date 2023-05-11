package com.yang.realm;

import com.yang.entify.Admin;
import com.yang.entify.Resources;
import com.yang.entify.Role;
import com.yang.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 龙小洋
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = adminService.findOneByUsername(primaryPrincipal);
        ArrayList<String> roles = new ArrayList<>();
        ArrayList<String> resources = new ArrayList<>();
        //获取 角色  遍历
        List<Role> role = admin.getRoles();

        for (Role role1 : role) {
            roles.add(role1.getRole_name());
            for (Resources resource : role1.getResources()) {
                resources.add(resource.getResource_name());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(resources);
        return simpleAuthorizationInfo;

    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        Admin oneByName = adminService.findOneByName(principal);
        if(oneByName==null){
            return null;
        }
        AuthenticationInfo simpleAuthenticationInfo =new SimpleAuthenticationInfo(oneByName.getName(),oneByName.getPassword(),this.getName());
        return simpleAuthenticationInfo;

    }
}
