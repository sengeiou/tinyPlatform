package com.wanliang.micro.service.impl.system;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanliang.micro.common.util.security.Digests;
import com.wanliang.micro.common.util.security.Encodes;
import com.wanliang.micro.common.util.uuid.UUIDUtil;
import com.wanliang.micro.entity.system.User;
import com.wanliang.micro.param.system.LoginParam;
import com.wanliang.micro.param.system.UserParam;
import com.wanliang.micro.persistence.Page;
import com.wanliang.micro.repository.system.UserRepository;
import com.wanliang.micro.result.PageResult;
import com.wanliang.micro.result.system.LoginResult;
import com.wanliang.micro.result.system.UserResult;
import com.wanliang.micro.service.system.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wanliang
 * @version 1.0
 * @date 2015/11/25
 * @modify
 * @copyright microPlatform
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "usercache", key = "#param.getLoginName()")
    public LoginResult login(LoginParam param) {
        User user = new User();
        user.setLoginName(param.getLoginName());
        user = userRepository.getByLoginName(param.getLoginName());
        if (user == null) {
            return null;
        }

//        boolean isflag = validatePassword(param.getPassword(), user.getPassword());
//        if (!isflag) {
//            return null;
//        }
        LoginResult loginResult = new LoginResult();
        loginResult.setNickname(user.getName());
        loginResult.setToken(param.getType() + "_" + UUIDUtil.randomUUID());
        return loginResult;
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        logger.debug("password:" + (Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword)));
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }


    public UserResult getUserByEmail(String email) {
        logger.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        User user = userRepository.getByLoginName(email);
        if (user==null){
            return null;
        }
        UserResult userResult=new UserResult();
        userResult.setLoginName(user.getLoginName());
        userResult.setMobile(user.getMobile());
        userResult.setEmail(user.getEmail());
        userResult.setName(user.getName());
        userResult.setPassword(user.getPassword());
        userResult.setId(user.getId());
        return userResult;
    }

    public UserResult getUserById(String id){
       User user= userRepository.get(id);
        UserResult userResult=new UserResult();
        userResult.setLoginName(user.getLoginName());
        userResult.setMobile(user.getMobile());
        userResult.setEmail(user.getEmail());
        userResult.setName(user.getName());
       // userResult.setPassword(user.getPassword());
        userResult.setId(user.getId());
        userResult.setPhoto(user.getPhoto());
        return userResult;
    }

    public List<UserResult> findAll(){
       List<User>  list= userRepository.findList(new User());
        List<UserResult> resultList=new ArrayList<>();
        for(User user:list){
            UserResult userResult=new UserResult();
            userResult.setLoginName(user.getLoginName());
            userResult.setMobile(user.getMobile());
            userResult.setEmail(user.getEmail());
            userResult.setName(user.getName());
            // userResult.setPassword(user.getPassword());
            userResult.setId(user.getId());
            userResult.setPhoto(user.getPhoto());
            resultList.add(userResult);
        }
        return resultList;
    }

    public PageResult<UserResult> findUser(UserParam userParam) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        //  Page
        //   user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));

        // 设置分页参数
       // user.setPage(page);
        // 执行分页查询
       // page.setList(userRepository.findList(user));
        User u=new User();
        Page<User> page=new Page<>(userParam.getCurPage(),userParam.getRepage(),userParam.getPageSize(),userParam.getSortName(),-2);
        u.setPage(page);
        List<User> list=userRepository.findList(u);
        page.setList(list);
        List<UserResult> resultList=new ArrayList<>();
        System.out.print(page);
        for(User user:list){
            UserResult userResult=new UserResult();
            userResult.setLoginName(user.getLoginName());
            userResult.setMobile(user.getMobile());
            userResult.setEmail(user.getEmail());
            userResult.setName(user.getName());
            // userResult.setPassword(user.getPassword());
            userResult.setId(user.getId());
            userResult.setCompanyName(user.getCompany().getName());
            userResult.setOfficeName(user.getOffice().getName());
            userResult.setPhoto(user.getPhoto());
            resultList.add(userResult);
        }
        PageResult<UserResult> pageResult=new PageResult<>();
        pageResult.setData(resultList);
        pageResult.setSuccess(true);
        pageResult.setTotalRows(page.getCount());
        pageResult.setCurPage(page.getPageNo());
        return pageResult;
    }



}
