package com.heart.heartcloud.shiro;

import com.heart.heartcloud.domain.CloudPermission;
import com.heart.heartcloud.domain.CloudRole;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.service.CloudUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName:ShiroRealm
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 10:09
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private CloudUserService cloudUserService;

    /**
     * 角色、权限鉴定
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("ShiroRealm授权角色&权限");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        String userName = (String) principals.getPrimaryPrincipal();
        CloudUser cloudUserByUserName = cloudUserService.findCloudUserByUserName(userName);
        logger.info("ShiroRealm授权角色&权限 => 用户信息 :{}", cloudUserByUserName);

        if (cloudUserByUserName.getUserRoles() != null) {
            List<String> rolesList = new ArrayList<>();
            for (CloudRole cloudRole : cloudUserByUserName.getUserRoles()) {
                rolesList.add(cloudRole.getRoleName());
            }
            //将用户角色信息告诉shiro
            logger.info("ShiroRealm授权角色&权限 => 角色信息 :{}", rolesList);
            simpleAuthorizationInfo.setRoles(new HashSet<>(rolesList));
        }
        if (cloudUserByUserName.getUserPermissions() != null) {
            List<String> permissionsList = new ArrayList<>();
            for (CloudPermission cloudPermission : cloudUserByUserName.getUserPermissions()) {
                permissionsList.add(cloudPermission.getPermissionName());
            }
            //将用户权限信息告诉shiro
            logger.info("ShiroRealm授权角色&权限 => 权限信息 :{}", permissionsList);
            simpleAuthorizationInfo.setStringPermissions(new HashSet<>(permissionsList));
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户身份验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("ShiroRealm用户身份验证");

        //获取用户输入的账号
        String userName = (String) token.getPrincipal();

        //通过userName查询用户账号
        CloudUser cloudUserByUserName = cloudUserService.findCloudUserByUserName(userName);
        logger.info("ShiroRealm用户身份验证 => 用户信息 :{}", cloudUserByUserName);
        if (cloudUserByUserName == null) {
            return null;
        }
        //构造身份认证信息
        return new SimpleAuthenticationInfo(
                userName,
                cloudUserByUserName.getUserPass(),
                ByteSource.Util.bytes(cloudUserByUserName.getUserSalt()),
                getName());
    }
}
