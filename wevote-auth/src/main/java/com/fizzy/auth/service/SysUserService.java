package com.fizzy.auth.service;

import com.fizzy.auth.mapper.SysUserMapper;
import com.fizzy.auth.utils.MailUtil;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.utils.Result;
import com.fizzy.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:59
 */
@Service
public class SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(SysUser sysUser){
        return sysUserMapper.insertOne(sysUser);
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteById(long id){
        return sysUserMapper.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    public SysUser selectByUserId(long id){
        return sysUserMapper.selectByUserId(id);
    }

    /**
     * 根据用户名查询用户
     */
    public SysUser selectUserByName(String username){
        return sysUserMapper.selectUserByName(username);
    }

    /**
     * 根据启用状态
     */
    public List<SysUser> selectUserByEnable(String enable){
        return sysUserMapper.selectUserByEnable(enable);
    }

    /**
     * 根据用户名模糊查询
     */
    public List<SysUser> selectByUsernameLike(String username){
        return sysUserMapper.selectByUsernameLike(username);
    }

    /**
     * 查询所有数据
     */
    public List<SysUser> selectAll(SysUser sysUser){
        return sysUserMapper.selectAll(sysUser);
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(SysUser sysUser){
        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 修改用户名
     */
    public Result updateUsernameById(SysUser sysUser){
        if(selectUserByName(sysUser.getUsername()) != null) {
            return new Result(203,"用户名已被使用！");
        }
        if(sysUserMapper.updateById(sysUser)) {
            return new Result(200,"成功！");
        } else {
            return new Result(500,"失败！");
        }
    }

    /**
     * 修改邮箱
     */
    public Result updateEmailById(SysUser sysUser){
        SysUser user = new SysUser();
        user.setEmail(sysUser.getEmail());
        if(CollectionUtils.isEmpty(selectAll(user))) {
            return new Result(203,"邮箱已被使用！");
        }
        if(sysUserMapper.updateById(sysUser)) {
            return new Result(200,"成功！");
        } else {
            return new Result(500,"失败！");
        }
    }

    public Result sendEmail(String email){
        if(email == null || email.isEmpty()) {
            return new Result(203,"邮箱不能为空！");
        }

        SysUser user = new SysUser();
        user.setEmail(email);
        if(!CollectionUtils.isEmpty(selectAll(user))) {
            return new Result(203,"邮箱已被使用！");
        }
        try {
            String coed =  String.valueOf((int)((Math.random()*9+1)*100000));
            redisUtil.setExpire("mailVerifyCode:" + email,coed,30, TimeUnit.MINUTES);
            mailUtil.sendMail(email, "we-vote 邮箱验证", "邮箱验证，您的验证码为：" + coed + "。30分钟内有效！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500,"邮箱发送失败！");
        }

        return new Result(200,"成功！");
    }
}
