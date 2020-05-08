package com.example.demo.Service;

import com.example.demo.Dao.MessageDao;
import com.example.demo.entity.Message;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    /*  注入购物、授信额度、报销、支付、转账  等消息记录   */
    public boolean insert_message(String identification, Integer label, String commodity){
        Message message = new Message();
        message.setIdentification(identification);
        message.setLabel(label);
        switch (label){
            case 1:
                message.setContent("您的授信额度已获得提高！");
                break;
            case 2:
                message.setContent("您的报销已被受理，报销成功!");
                break;
            case 3:
                message.setContent("很抱歉，企业拒绝了您的报销申请!");
                break;
            case 4:
                message.setContent("成功支付" + commodity + "!");
                break;
            case 5:
                message.setContent("您完成了一笔转账！");
                break;
            default:
                System.out.println("label 异常");
                break;
        }
        Date time = new Date();
        message.setTime(time);
        message.setState(0);
        return messageDao.insert_message(message);
    }

    public Integer select_num(String id){
        return messageDao.select_num(id);
    }

    public boolean update_state(String id){
        return messageDao.update_state(id);
    }
}
