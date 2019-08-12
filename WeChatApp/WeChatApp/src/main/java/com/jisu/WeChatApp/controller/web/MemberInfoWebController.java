package com.jisu.WeChatApp.controller.web;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.OperatorMemberMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.OperatorMember;
import com.jisu.WeChatApp.pojo.OperatorMemberExample;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.pojo.Permission;
import com.jisu.WeChatApp.pojo.Role;
import com.jisu.WeChatApp.pojo.UserRole;
import com.jisu.WeChatApp.pojo.UserSearchDTO;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.RoleServiceImpl;
import com.jisu.WeChatApp.tool.util.DateUtil;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.ValidateUtil;

import net.sf.json.JSONArray;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

@Controller
@RequestMapping("/web/member")
public class MemberInfoWebController {
	private static final Logger logger = LoggerFactory.getLogger(MemberInfoWebController.class);
	@Autowired
	private RoleServiceImpl roleServiceImpl;
	@Autowired
	private MemberInfoServiceImpl memberInfoServiceImpl;

	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private OperatorMemberMapper peratorMemberMapper;

	@RequestMapping("/userList")
	public String toUserList() {
		return "auth/userList";
	}

	/**
	 * 分页查询用户列表
	 * 
	 * @return ok/fail
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = "usermanage")
	public PageDataResult getUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, UserSearchDTO userSearch) {
		logger.debug("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",page:" + page + ",每页记录数量limit:" + limit);
//		ErrorController
		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			// 获取用户和角色列表
			pdr = memberInfoServiceImpl.getUsers(userSearch, page, limit);
			logger.debug("用户列表查询=pdr:" + pdr);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户列表查询异常！", e);
		}
		return pdr;
	}

	/**
	 * 设置用户是否离职
	 * 
	 * @return ok/fail
	 */
	@RequestMapping(value = "/setJobUser", method = RequestMethod.POST)
	@ResponseBody
	public String setJobUser(@RequestParam("id") String member_no, @RequestParam("job") Integer isJob, @RequestParam("version") Integer version) {
		logger.debug("设置用户是否离职！id:" + member_no + ",isJob:" + isJob + ",version:" + version);
		String msg = "";
		try {
			if (null == member_no || null == isJob || null == version) {
				logger.debug("设置用户是否离职，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				logger.debug("设置用户是否离职，结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}
			// 设置用户是否离职
			msg = memberInfoServiceImpl.setJobUser(member_no, isJob, existUser.getMemberNo(), version);
			logger.info("设置用户是否离职成功！userID=" + member_no + ",isJob:" + isJob + "，操作的用户ID=" + existUser.getMemberNo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置用户是否离职异常！", e);
			msg = "操作异常，请您稍后再试！";
		}
		return msg;
	}

	/**
	 * 设置用户[新增或更新]
	 * 
	 * @return ok/fail
	 */
	@RequestMapping(value = "/setUser", method = RequestMethod.POST)
	@ResponseBody
	public String setUser(@RequestParam("roleIds") String roleIds, MemberInfo user) {
		logger.debug("设置用户[新增或更新]！user:" + user + ",roleIds:" + roleIds);
		try {
			if (null == user) {
				logger.debug("置用户[新增或更新]，结果=请您填写用户信息");
				return "请您填写用户信息";
			}
			if (StringUtils.isEmpty(roleIds)) {
				logger.debug("置用户[新增或更新]，结果=请您给用户设置角色");
				return "请您给用户设置角色";
			}
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				logger.debug("置用户[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}
			// 设置用户[新增或更新]
			logger.info("设置用户[新增或更新]成功！user=" + user + ",roleIds=" + roleIds + "，操作的用户ID=" + existUser.getMemberNo());
			return memberInfoServiceImpl.setUser(user, roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置用户[新增或更新]异常！", e);
			return "操作异常，请您稍后再试";
		}
	}

	/**
	 * 删除用户
	 * 
	 * @return ok/fail
	 */
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	public String delUser(@RequestParam("id") Integer id, @RequestParam("version") Integer version) {
		logger.debug("删除用户！id:" + id);
		String msg = "";
		try {
			if (null == id || null == version) {
				logger.debug("删除用户，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				logger.debug("删除用户，结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}
			// 删除用户
			msg = memberInfoServiceImpl.setDelUser(id, 1, existUser.getMemberNo(), version);
			logger.info("删除用户:" + msg + "。userId=" + id + "，操作用户id:" + existUser.getMemberNo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户异常！", e);
			msg = "操作异常，请您稍后再试";
		}
		return msg;
	}

	/**
	 *
	 * @描述：恢复用户
	 * @创建人：wyait
	 * @创建时间：2018年4月27日 上午9:49:14
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/recoverUser", method = RequestMethod.POST)
	@ResponseBody
	public String recoverUser(@RequestParam("id") Integer id, @RequestParam("version") Integer version) {
		logger.debug("恢复用户！id:" + id);
		String msg = "";
		try {
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				return "您未登录或登录超时，请您登录后再试";
			}
			if (null == id || null == version) {
				return "请求参数有误，请您稍后再试";
			}
			// 删除用户
			msg = memberInfoServiceImpl.setDelUser(id, 0, existUser.getMemberNo(), version);
			logger.info("恢复用户【" + this.getClass().getName() + ".recoverUser】" + msg + "。用户userId=" + id + "，操作的用户ID=" + existUser.getMemberNo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("恢复用户【" + this.getClass().getName() + ".recoverUser】用户异常！", e);
			msg = "操作异常，请您稍后再试";
		}
		return msg;
	}

	/**
	 * 查询用户数据
	 * 
	 * @return map
	 */
	@RequestMapping(value = "/getUserAndRoles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserAndRoles(@RequestParam("id") Integer id) {
		logger.debug("查询用户数据！id:" + id);
		Map<String, Object> map = new HashMap<>();
		try {
			if (null == id) {
				logger.debug("查询用户数据==请求参数有误，请您稍后再试");
				map.put("msg", "请求参数有误，请您稍后再试");
				return map;
			}
			// 查询用户
			UserRole urvo = memberInfoServiceImpl.getUserAndRoles(id);
			logger.debug("查询用户数据！urvo=" + urvo);
			if (null != urvo) {
				map.put("user", urvo);
				// 获取全部角色数据
				List<Role> roles = roleServiceImpl.getRoles();
				logger.debug("查询角色数据！roles=" + roles);
				if (null != roles && roles.size() > 0) {
					map.put("roles", roles);
				}
				map.put("msg", "ok");
			} else {
				map.put("msg", "查询用户信息有误，请您稍后再试");
			}
			logger.debug("查询用户数据成功！map=" + map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "查询用户错误，请您稍后再试");
			logger.error("查询用户数据异常！", e);
		}
		return map;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel sendMsg(MemberInfo user) {
		logger.debug("发送短信验证码！user:" + user);
		MsgModel msg = new MsgModel();
		try {
			if (null == user) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("请求参数有误，请您稍后再试");
				logger.debug("发送短信验证码，结果=responseResult:" + msg);
				return msg;
			}
			if (!validatorRequestParam(user, msg)) {
				logger.debug("发送短信验证码，结果=responseResult:" + msg);
				return msg;
			}
			// 送短信验证码
			// String msg=userService.sendMsg(user);
			String res_str = "ok";
			if (res_str != "ok") {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage(res_str == "no" ? "发送验证码失败，请您稍后再试" : res_str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("发送短信验证码失败，请您稍后再试");
			logger.error("发送短信验证码异常！", e);
		}
		logger.debug("发送短信验证码，结果=responseResult:" + msg);
		return msg;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @param picCode
	 * @return
	 */
	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel sendMessage(@RequestParam("mobile") String mobile, @RequestParam("picCode") String picCode) {
		logger.debug("发送短信验证码！mobile:" + mobile + ",picCode=" + picCode);
		MsgModel msg = new MsgModel();
		try {
			if (!ValidateUtil.isMobilephone(mobile)) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("手机号格式有误，请您重新填写");
				logger.debug("发送短信验证码，结果=responseResult:" + msg);
				return msg;
			}
			if (!ValidateUtil.isPicCode(picCode)) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("图片验证码有误，请您重新填写");
				logger.debug("发送短信验证码，结果=responseResult:" + msg);
				return msg;
			}
			// 判断用户是否登录
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("您未登录或登录超时，请您重新登录后再试");
				logger.debug("发送短信验证码，结果=responseResult:" + msg);
				return msg;
			}
			// 送短信验证码
			// String msg=userService.sendMessage(existUser.getId(),mobile);
			String res_str = "ok";
			if (res_str != "ok") {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage(res_str == "no" ? "发送验证码失败，请您稍后再试" : res_str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("发送短信验证码失败，请您稍后再试");
			logger.error("发送短信验证码异常！", e);
		}
		logger.debug("发送短信验证码，结果=responseResult:" + msg);
		return msg;
	}

	/**
	 * 修改密码之确认手机号
	 * 
	 * @param mobile
	 * @param picCode
	 * @return
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel updatePwd(@RequestParam("mobile") String mobile, @RequestParam("picCode") String picCode, @RequestParam("mobileCode") String mobileCode) {
		logger.debug("修改密码之确认手机号！mobile:" + mobile + ",picCode=" + picCode + ",mobileCode=" + mobileCode);
		MsgModel responseResult = new MsgModel();
		responseResult.setStatus(MsgModel.ERROR);
		try {
			if (!ValidateUtil.isMobilephone(mobile)) {
				responseResult.setMessage("手机号格式有误，请您重新填写");
				logger.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
				return responseResult;
			}
			if (!ValidateUtil.isPicCode(picCode)) {
				responseResult.setMessage("图片验证码有误，请您重新填写");
				logger.debug("发修改密码之确认手机号，结果=responseResult:" + responseResult);
				return responseResult;
			}
			if (!ValidateUtil.isCode(mobileCode)) {
				responseResult.setMessage("短信验证码有误，请您重新填写");
				logger.debug("发修改密码之确认手机号，结果=responseResult:" + responseResult);
				return responseResult;
			}
			// 判断用户是否登录
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
				logger.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
				return responseResult;
			} else {
				// 校验验证码
				/*
				 * if(!existUser.getMcode().equals(user.getSmsCode())){ //不等
				 * responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				 * responseResult.setMessage("短信验证码输入有误");
				 * logger.debug("用户登录，结果=responseResult:"+responseResult); return
				 * responseResult; } //1分钟 long beginTime =existUser.getSendTime().getTime();
				 * long endTime = new Date().getTime(); if(((endTime-beginTime)-60000>0)){
				 * responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				 * responseResult.setMessage("短信验证码超时");
				 * logger.debug("用户登录，结果=responseResult:"+responseResult); return
				 * responseResult; }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setStatus(MsgModel.ERROR);
			responseResult.setMessage("操作失败，请您稍后再试");
			logger.error("修改密码之确认手机号异常！", e);
			return responseResult;
		}
		responseResult.setStatus(MsgModel.SUCCESS);
		responseResult.setMessage("SUCCESS");
		logger.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
		return responseResult;
	}

	/**
	 * 修改密码
	 * 
	 * @param pwd
	 * @param isPwd
	 * @return
	 */
	@RequestMapping(value = "setPwd", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel setPwd(@RequestParam("pwd") String pwd, @RequestParam("isPwd") String isPwd) {
		logger.debug("修改密码！pwd:" + pwd + ",isPwd=" + isPwd);
		MsgModel responseResult = new MsgModel();
		try {
			if (!ValidateUtil.isSimplePassword(pwd) || !ValidateUtil.isSimplePassword(isPwd)) {
				responseResult.setStatus(MsgModel.ERROR);
				responseResult.setMessage("密码格式有误，请您重新填写");
				logger.debug("修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}
			if (!pwd.equals(isPwd)) {
				responseResult.setStatus(MsgModel.ERROR);
				responseResult.setMessage("两次密码输入不一致，请您重新填写");
				logger.debug("发修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}
			// 判断用户是否登录
			MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				responseResult.setStatus(MsgModel.ERROR);
				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
				logger.debug("修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}
			// 修改密码
			int num = memberInfoServiceImpl.updatePwd(existUser.getMemberNo(), DigestUtils.md5Hex(pwd));
			if (num != 1) {
				responseResult.setStatus(MsgModel.ERROR);
				responseResult.setMessage("操作失败，请您稍后再试");
				logger.debug("修改密码失败，已经离职或该用户被删除！结果=responseResult:" + responseResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setStatus(MsgModel.ERROR);
			responseResult.setMessage("操作失败，请您稍后再试");
			logger.error("修改密码异常！", e);
		}
		logger.debug("修改密码，结果=responseResult:" + responseResult);
		return responseResult;
	}

	public static void main(String[] args) throws ParseException {
		Date date = DateUtil.parse("2018-01-04 20:15:21");
		Date date1 = DateUtil.parse("2018-01-04 20:11:21");
		Date date2 = DateUtil.parse("2018-01-04 20:12:21");
		long beginTime = date.getTime();
		long beginTime1 = date1.getTime();
		long end1 = date2.getTime();
		long endTime = System.currentTimeMillis();
		// main
		long betweenDays = endTime - beginTime;
		long betweenDays1 = endTime - beginTime1;
		long eq = end1 - beginTime1;
		boolean flag = betweenDays - (60000) > 0;// 超时
		boolean flag1 = betweenDays1 - (60000) > 0;// 超时
		boolean flag2 = eq - (60000) == 0;//
		System.out.println(betweenDays);
		System.out.println(betweenDays1);
		System.out.println(eq);
		System.out.println(flag);
		System.out.println(flag1);
		System.out.println(flag2);
	}

	/**
	 * @描述：校验请求参数
	 * @param obj
	 * @param response
	 * @return
	 */
	protected boolean validatorRequestParam(Object obj, MsgModel response) {
		boolean flag = false;
		Validator validator = new Validator();
		List<ConstraintViolation> ret = validator.validate(obj);
		if (ret.size() > 0) {
			// 校验参数有误
			response.setStatus(MsgModel.ERROR);
			response.setMessage(ret.get(0).getMessageTemplate());
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据用户id查询权限树数据
	 * 
	 * @return PermTreeDTO
	 */
	@RequestMapping(value = "/getUserPerms", method = RequestMethod.GET)
	@ResponseBody
	public List<Permission> getUserPerms() {
		logger.debug("根据用户id查询限树列表！");
		List<Permission> pvo = null;
		MemberInfo existUser = (MemberInfo) SecurityUtils.getSubject().getPrincipal();
		if (null == existUser) {
			logger.debug("根据用户id查询限树列表！用户未登录");
			return pvo;
		}
		try {
			pvo = roleServiceImpl.getUserPerms(existUser.getMemberNo());
			// 生成页面需要的json格式
			logger.debug("根据用户id查询限树列表查询=pvo:" + pvo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据用户id查询权限树列表查询异常！", e);
		}
		return pvo;
	}

	@RequestMapping("getMember")
	@ResponseBody
	public MemberInfo getMember(@RequestParam("member_no") String member_no) {

		return memberInfoMapper.selectByPrimaryKey(member_no);
	}

	@RequestMapping(value = "updateMemberType", method = RequestMethod.POST)
	@ResponseBody
	public String updateMemberType(MemberInfo memberInfo) {
		if (null != memberInfo) {
			try {
				memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
				OperatorMemberExample example = new OperatorMemberExample();
				example.createCriteria().andMemberNoEqualTo(memberInfo.getMemberNo());
				if (3 == memberInfo.getMemberType()) {
					
					List<OperatorMember> operatorMember_list = peratorMemberMapper.selectByExample(example);
					if (operatorMember_list.size() == 0) {

						OperatorMember operatorMember = new OperatorMember();
						operatorMember.setOperatorMemberId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
						operatorMember.setMemberNo(memberInfo.getMemberNo());
						peratorMemberMapper.insertSelective(operatorMember);
					}
				}else {
					peratorMemberMapper.deleteByExample(example);
				}
				return "ok";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return "设置用户身份失败,请稍后重试";
	}

	@RequestMapping("/operatorMemberList")
	public ModelAndView toOperatorMemberList() {
		return new ModelAndView("member/operatorMemberList");
	}

	@RequestMapping("getOperatorMemberList")
	@ResponseBody
	public PageDataResult getOperatorMemberList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, UserSearchDTO userSearch) {
		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			pdr = memberInfoServiceImpl.getAllOperatorMemberList(userSearch, page, limit);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户列表查询异常！", e);
		}
		return pdr;
	}

	@RequestMapping(value = "updateIsShare", method = RequestMethod.POST)
	@ResponseBody
	public String updateIsShare(@RequestParam("is_share") String is_share, @RequestParam("member_no") String member_no) {
		if (StringUtils.isNotBlank(member_no)) {
			return memberInfoServiceImpl.updateIsShare(is_share, member_no);
		}
		return "设置失败,请稍后重试";
	}
}
