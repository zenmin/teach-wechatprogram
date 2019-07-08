package com.teach.wecharprogram.components.business;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

/**
 * @Describle This Class Is 微信接口工具
 * @Author ZengMin
 * @Date 2019/5/27 10:07
 */
@Component
@Slf4j
public class WxUtil {

    // 微信登录
    public static final String WX_LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    // getAccessToken
    public static final String WX_GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    // 获取小程序码
    public static final String WX_GET_QRCODE = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";
    @Value("${wx.appid}")
    String APPID;

    @Value("${wx.appsecret}")
    String APPSECRET;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 属性	类型	说明
     * openid	string	用户唯一标识
     * session_key	string	会话密钥
     * unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     * errcode	number	错误码
     * errmsg	string	错误信息
     *
     * @param jsCode
     * @return 微信登录
     */
    public Object authCode2Session(String jsCode) {
        String url = String.format(WX_LOGIN_API, APPID, APPSECRET, jsCode);
        String forObject = restTemplate.getForObject(url, String.class);
        Map map = StaticUtil.readToMap(forObject, "微信服务器异常！");
        Object errCode = map.get("errcode");
        if (Objects.isNull(errCode)) {
            return map;
        } else {
            log.error("微信请求异常：{}", map.get("errmsg").toString());
            String errorMsg = "系统繁忙，请稍后再试！";
            switch (errCode.toString()) {
                case "40029":
                    errorMsg = "验签无效！";
                    break;
                case "45011":
                    errorMsg = "请求频率过快，请稍后再试！";
                    break;
                case "40163":
                    errorMsg = "验签已过期，请刷新重试！";
                default:
                    break;
            }
            throw new CommonException(DefinedCode.WX_LOGIN_ERROR, errorMsg);
        }
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    public String authGetAccessToken() {
        // 先去缓存获取
        String acceccToken = redisUtil.get(CacheConstant.WX_ACCESSTOKEN);
        if (StringUtils.isNotBlank(acceccToken)) {
            return acceccToken;
        }
        String url = String.format(WX_GET_ACCESSTOKEN, APPID, APPSECRET);
        String forObject = restTemplate.getForObject(url, String.class);
        Map map = StaticUtil.readToMap(forObject, "微信服务器异常！");
        Object o = map.get("errcode");
        if (Objects.isNull(o)) {
            String accessToken = map.get("access_token").toString();
            redisUtil.setAsync(CacheConstant.WX_ACCESSTOKEN, accessToken, CacheConstant.WX_ACCESSTOKEN_EXPIRE);
            return accessToken;
        } else {
            log.error("微信请求异常：{}", map.get("errmsg").toString());
            return null;
        }
    }

    /**
     * 获取小程序码
     * 属性       	类型	        默认值	必填	    说明
     * access_token	string		        是   	接口调用凭证
     * scene	    string		        是   	最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * page     	string	    主页	    否	        必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
     * width	    number	    430	    否	    二维码的宽度，单位 px，最小 280px，最大 1280px
     * auto_color	boolean	false	    否	    自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     * line_color	Object	 {"r":0,"g":0,"b":0}否	auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     * is_hyaline	boolean	false	    否	    是否需要透明底色，为 true 时，生成透明底色的小程序
     *
     * @return
     */
    public ByteArrayInputStream getQCCode() {
        // 获取ak
        String accessToken = this.authGetAccessToken();
        Map<String, Object> map = Maps.newHashMap();
//        map.put("page","pages/addProduct/main");152
        map.put("auto_color", true);
        map.put("scene", "name=zm");
        map.put("line_color", ImmutableMap.of("r", 255, "g", 155, "b", 0));
//        map.put("is_hyaline", true);
        String url = String.format(WX_GET_QRCODE, accessToken);
        try {
            byte[] bytes = HttpClientUtil.sendPostJsonByte(url, JSONObject.toJSONString(map), "UTF-8");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(DefinedCode.WX_SERVER_ERROR, "服务器繁忙，请稍后再试");
        }
    }

    /**
     * @param url
     * @param map
     * @return 微信支付公共返回
     */
    private Map<String, String> wxPayCommonReturn(String url, Map<String, Object> map) {
        String xmlString = XMLUtils.convertJsonToXMLString(JSONUtil.toJSONString(map), "xml").replace("\r", "")
                .replace("\n", "");
        String result = HttpClientUtil.sendPostJson(url, xmlString, "UTF-8");
        try {
            result = new String(result.getBytes("ISO8859-1"),
                    "UTF-8").replace("\r", "").replace("\n", "");
            Map<String, String> resultMap = XMLUtils.xmlToMap(result);
            String result_code = resultMap.get("result_code");
            String return_code = resultMap.get("return_code");
            String err_code_des = resultMap.get("err_code_des");
            if (result_code.equals("SUCCESS")) {
                if (return_code.equals("SUCCESS"))
                    return resultMap;
                else
                    throw new CommonException(DefinedCode.WX_SERVER_ERROR, err_code_des);
            } else {
                throw new CommonException(DefinedCode.WX_SERVER_ERROR, err_code_des);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
