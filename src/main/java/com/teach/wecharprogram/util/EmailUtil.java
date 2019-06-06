package com.teach.wecharprogram.util;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Describle This Class Is 邮件发送工具类
 * @Author ZengMin
 * @Date 2019/2/17 13:47
 */
@Component
@Slf4j
public class EmailUtil {

    /**
     * 发送邮件主方法 不要直接调
     * @param userTitle
     * @param receiveUser
     * @param content
     */
    @Async
    public void sendMail(String userTitle,String receiveUser, String content) {
        try {
            String username = "";
            String password = "";
            String host = "";
            String title = "";

            //设置发件属性
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setUsername(username);
            javaMailSender.setPassword(password);
            javaMailSender.setHost(host);
            Properties properties = new Properties();
            properties.setProperty("spring.mail.properties.mail.smtp.ssl.enable", "true");
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setJavaMailProperties(properties);

            //邮件内容
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, false);  //multipart=true 表示这是一个可以上传附件的消息
            mimeMessageHelper.setTo(receiveUser);     //收件人地址不对  会抛出550 Invalid Addresses
            mimeMessageHelper.setText(content, true);     //表明这是一个html片段
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setValidateAddresses(false);
            // 添加邮件附件
            // mimeMessageHelper.addAttachment("cs.png",new File("C:\\Users\\74170\\Pictures\\FLAMING MOUNTAIN.png"));
            javaMailSender.send(mimeMailMessage);
            log.info("邮件发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(DefinedCode.ERROR,"邮件发送失败");
        }

    }

}
