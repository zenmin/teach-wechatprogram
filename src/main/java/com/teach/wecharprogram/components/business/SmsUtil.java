package com.teach.wecharprogram.components.business;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/16 19:32
 */
@Component
@Slf4j
public class SmsUtil {

    @Value("${aliyun.accesskeyid}")
    String ACCESS_KEY_ID;

    @Value("${aliyun.accesskeysecret}")
    String ACCESS_KEY_SECRET = "";

    @Value("${aliyun.signName}")
    String SIGNNAME;

    @Value("${aliyun.templateCode}")
    String TEMPLATECODE;


    /**
     * @param phone
     * @param param
     */
    @Async
    public void sendSms(String phone, Map param) {
        //加载配置
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(SIGNNAME);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(TEMPLATECODE);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(JSONObject.toJSONString(param));
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //        request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            acsClient.getAcsResponse(request).getCode();
        } catch (ClientException e) {
            log.error("短信发送失败！");
            e.printStackTrace();
            throw new CommonException(DefinedCode.ERROR, "短信发送失败，请重试！");
        }
    }

}
