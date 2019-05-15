package com.jisu.WeChatApp.service;

import java.util.List;

import com.jisu.WeChatApp.pojo.Permission;
import com.jisu.WeChatApp.pojo.Role;

public interface RoleService {

	int addPermission(Permission permission);

	List<Permission> permList();

	int updatePerm(Permission permission);

	Permission getPermission(String id);

	String delPermission(String id);

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	List<Role> roleList();

	/**
	 * 关联查询权限树列表
	 * 
	 * @return
	 */
	List<Permission> findPerms();

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @param permIds
	 * @return
	 */
	String addRole(Role role, String permIds);

	Role findRoleAndPerms(String id);

	/**
	 * 更新角色并授权
	 * 
	 * @param role
	 * @param permIds
	 * @return
	 */
	String updateRole(Role role, String permIds);

	/**
	 * 删除角色以及它对应的权限
	 * 
	 * @param id
	 * @return
	 */
	String delRole(String id);

	/**
	 * 查找所有角色
	 * 
	 * @return
	 */
	List<Role> getRoles();

	/**
	 * 根据用户获取角色列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> getRoleByUser(String userId);

	/**
	 * 根据角色id获取权限数据
	 * 
	 * @param id
	 * @return
	 */
	List<Permission> findPermsByRoleId(String id);

	/**
	 * 根据用户id获取权限数据
	 * 
	 * @param id
	 * @return
	 */
	List<Permission> getUserPerms(String id);

}
