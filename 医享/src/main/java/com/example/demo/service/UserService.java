package com.example.demo.service;

import com.example.demo.pojo.form.LoginFormInfo;
import com.example.demo.pojo.form.SignUpFormInfo;
import com.example.demo.pojo.table.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserService
 * @Description: UserService的service层
 * @Author: 刘敬
 * @Date: 2019/6/5 19:48
 **/
public interface UserService {
    Map<String, Object> miniProgramLogin(LoginFormInfo loginFormInfo);

    User getUserInfo(long userId);

    int login(LoginFormInfo loginFormInfo, HttpServletResponse response, HttpServletRequest request);

    int signUp(SignUpFormInfo signUpFormInfo, HttpServletResponse response);

    void signOut(HttpServletResponse response);

    Map<String, Object> searchPatient(String searchKey);

    User findUserById(long uid);

    int patientInfoSubmit(User user, MultipartFile picture);

    int bankInfoSubmit(User user, MultipartFile picture);

    int doctorInfoSubmit(User user, MultipartFile picture);

    int insurerInfoSubmit(User user, MultipartFile picture);

    int researchInfoSubmit(User user, MultipartFile picture);

    List<User> findAllBank();

    int issue(long money, long bankId);
}
