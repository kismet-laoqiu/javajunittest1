package com.example.demo.service.Impl;

import com.example.demo.chaincode.AccountChainCode;
import com.example.demo.config.Config;
import com.example.demo.dao.OauthDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.userInfo.*;
import com.example.demo.pojo.form.LoginFormInfo;
import com.example.demo.pojo.form.SignUpFormInfo;
import com.example.demo.pojo.table.Oauth;
import com.example.demo.pojo.table.User;
import com.example.demo.pojo.table.userInfo.*;
import com.example.demo.pojo.util.Account;
import com.example.demo.service.UserService;
import com.example.demo.util.CipherUtil;
import com.example.demo.util.TokenUtil;
import com.example.demo.util.cloudStorage.CloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.util.RSAUtil.*;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/5 19:48
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private OauthDao oauthDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PatientInfoDao patientInfoDao;

    @Autowired
    private DoctorInfoDao doctorInfoDao;

    @Autowired
    private BankInfoDao bankInfoDao;

    @Autowired
    private InsurerInfoDao insurerInfoDao;

    @Autowired
    private ResearchInfoDao researchInfoDao;

    //小程序登录
    @Override
    public Map<String, Object> miniProgramLogin(LoginFormInfo loginFormInfo) {
        Map<String, Object> map = new HashMap<>();
        Oauth oauth = oauthDao.findByRoleAndOauthTypeAndOauthId(loginFormInfo.getType(), "email", loginFormInfo.getEmail());
        if (oauth == null) {
            map.put("code", 201);
        } else if (!CipherUtil.validatePassword(oauth.getCredential(), loginFormInfo.getPassword())) {
            map.put("code", 202);
        } else {
            oauth.setLastLoginTime(new Date());
            oauthDao.save(oauth);
            map.put("code", 200);
            Map<String, Object> data = new HashMap<>();
            data.put("userId", oauth.getUser().getId());
            map.put("data", data);
        }
        return map;
    }

    //获取用户信息
    @Override
    public User getUserInfo(long userId) {
        return userDao.findById(userId).get();
    }

    //登录
    @Override
    public int login(LoginFormInfo loginFormInfo, HttpServletResponse response, HttpServletRequest request) {
        Oauth oauth = oauthDao.findByRoleAndOauthTypeAndOauthId(loginFormInfo.getType(), "email", loginFormInfo.getEmail());
        if (oauth == null) {
            return 201;
        } else if (!CipherUtil.validatePassword(oauth.getCredential(), loginFormInfo.getPassword())) {
            return 202;
        } else {
            oauth.setLastLoginTime(new Date());
            oauthDao.save(oauth);
            String str = TokenUtil.generateToken(oauth.getUser().getId() + "");
            Cookie cookie = new Cookie(loginFormInfo.getType() + "_token", str);
            cookie.setMaxAge(60 * 60 * 24 * 5);// 设置为5天
            cookie.setPath("/");
            response.addCookie(cookie);
            return 200;
        }
    }

    //注册
    @Override
    public int signUp(SignUpFormInfo signUpFormInfo, HttpServletResponse response) {
        Oauth temp = oauthDao.findByRoleAndOauthTypeAndOauthId(signUpFormInfo.getType(), "email", signUpFormInfo.getEmail());
        if (temp == null) {
            User user = new User();
            user.setRole(signUpFormInfo.getType());
            try {
                Map<String, Object> keyPair = genKeyPair();
                user.setPublicKey(getPublicKey(keyPair));
                user.setPrivateKey(getPrivateKey(keyPair));
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setName(signUpFormInfo.getEmail());
            user.setAvatarUrl("https://yizhiapp.oss-cn-beijing.aliyuncs.com/APP/user_avatar.png");
            switch (signUpFormInfo.getType()) {
                case "patient":
                    PatientInfo patientInfo = new PatientInfo();
                    patientInfoDao.save(patientInfo);
                    user.setPatientInfo(patientInfo);
                    break;
                case "doctor":
                    DoctorInfo doctorInfo = new DoctorInfo();
                    doctorInfoDao.save(doctorInfo);
                    user.setDoctorInfo(doctorInfo);
                    break;
                case "insurer":
                    InsurerInfo insurerInfo = new InsurerInfo();
                    insurerInfoDao.save(insurerInfo);
                    user.setInsurerInfo(insurerInfo);
                    break;
                case "research":
                    ResearchInfo researchInfo = new ResearchInfo();
                    researchInfoDao.save(researchInfo);
                    user.setResearchInfo(researchInfo);
                    break;
                case "bank":
                    BankInfo bankInfo = new BankInfo();
                    bankInfoDao.save(bankInfo);
                    user.setBankInfo(bankInfo);
                    break;
                default:
                    break;
            }
            userDao.save(user);
            Account account = new Account(user.getId().toString(), user.getRole(), user.getName(), user.getAccountBalance());
            try {
                AccountChainCode.createAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Date date = new Date();
            Oauth oauth = new Oauth();
            oauth.setUser(user);
            oauth.setRole(signUpFormInfo.getType());
            oauth.setOauthType("email");
            oauth.setOauthId(signUpFormInfo.getEmail());
            oauth.setCredential(CipherUtil.generatePassword(signUpFormInfo.getPassword()));
            oauth.setCreateTime(date);
            oauth.setLastLoginTime(date);
            oauthDao.save(oauth);
            Cookie cookie = new Cookie(signUpFormInfo.getType() + "_token", TokenUtil.generateToken(oauth.getUser().getId() + ""));
            cookie.setMaxAge(60 * 60 * 24 * 5);// 设置为5天
            cookie.setPath("/");
            response.addCookie(cookie);
            return 200;
        } else {
            return 201;
        }
    }

    //退出
    @Override
    public void signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //搜索患者
    @Override
    public Map<String, Object> searchPatient(String searchKey) {//直接传的一个值
        Oauth temp = oauthDao.findByRoleAndOauthTypeAndOauthId("patient", "email", searchKey);
        Map<String, Object> map = new HashMap<>();
        if (temp == null) {
            map.put("state", 201);
        } else {
            map.put("state", 200);
            Map<String, Object> data = new HashMap<>();
            data.put("patientId", temp.getUser().getId());
            data.put("name", temp.getUser().getName());
            data.put("email", searchKey);
            map.put("data", data);
        }
        return map;
    }

    @Override
    public User findUserById(long uid) {
        return userDao.findById(uid).get();
    }


    //银行信息提交
    @Override
    public int bankInfoSubmit(User user, MultipartFile picture) {
        User u = userDao.findById(user.getId()).get();
        BankInfo bankInfo = u.getBankInfo();
        u.updateInfo(user);
        bankInfo.updateInfo(user.getBankInfo());
        u.setBankInfo(bankInfo);
        if (!picture.isEmpty()) {
            String fileName = picture.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            u.setAvatarUrl(CloudStorage.uploadMultipartFile(picture, "userAvatar/" + u.getId() + suffixName));
        }
        userDao.save(u);
        return 200;
    }

    //医生信息提交
    @Override
    public int doctorInfoSubmit(User user, MultipartFile picture) {
        User u = userDao.findById(user.getId()).get();
        DoctorInfo doctorInfo = u.getDoctorInfo();
        u.updateInfo(user);
        doctorInfo.updateInfo(user.getDoctorInfo());
        u.setDoctorInfo(doctorInfo);
        if (!picture.isEmpty()) {
            String fileName = picture.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            u.setAvatarUrl(CloudStorage.uploadMultipartFile(picture, "userAvatar/" + u.getId() + suffixName));
        }
        userDao.save(u);
        return 200;
    }

    //保险公司信息提交
    @Override
    public int insurerInfoSubmit(User user, MultipartFile picture) {
        User u = userDao.findById(user.getId()).get();
        InsurerInfo insurerInfo = u.getInsurerInfo();
        u.updateInfo(user);
        insurerInfo.updateInfo(user.getInsurerInfo());
        u.setInsurerInfo(insurerInfo);
        if (!picture.isEmpty()) {
            String fileName = picture.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            u.setAvatarUrl(CloudStorage.uploadMultipartFile(picture, "userAvatar/" + u.getId() + suffixName));
        }
        userDao.save(u);
        return 200;
    }

    //患者信息提交
    @Override
    public int patientInfoSubmit(User user, MultipartFile picture) {
        User u = userDao.findById(user.getId()).get();
        PatientInfo patientInfo = u.getPatientInfo();
        u.updateInfo(user);
        patientInfo.updateInfo(user.getPatientInfo());
        u.setPatientInfo(patientInfo);
        if (!picture.isEmpty()) {
            String fileName = picture.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            u.setAvatarUrl(CloudStorage.uploadMultipartFile(picture, "userAvatar/" + u.getId() + suffixName));
        }
        userDao.save(u);
        return 200;
    }

    //研究机构信息提交
    @Override
    public int researchInfoSubmit(User user, MultipartFile picture) {
        User u = userDao.findById(user.getId()).get();
        ResearchInfo researchInfo = u.getResearchInfo();
        u.updateInfo(user);
        researchInfo.updateInfo(user.getResearchInfo());
        u.setResearchInfo(researchInfo);
        if (!picture.isEmpty()) {
            String fileName = picture.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            u.setAvatarUrl(CloudStorage.uploadMultipartFile(picture, "userAvatar/" + u.getId() + suffixName));
        }
        userDao.save(u);
        return 200;
    }

    //列出可选银行
    @Override
    public List<User> findAllBank() {
        return userDao.findByRole("bank");
    }

    //添加充值记录
    @Override
    public int issue(long money, long bankId) {
        User bank = userDao.findById(bankId).get();
        AccountChainCode.issue(bank.getId().toString(), Config.rechargeRate * money + "");
        bank.setAccountBalance(bank.getAccountBalance() + Config.rechargeRate * money);
        userDao.save(bank);
        return 200;
    }

}
