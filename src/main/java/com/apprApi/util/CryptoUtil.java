package com.apprApi.util;

import com.apprApi.util.kisa.KISA_SEED_CBC;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Class CryptoUtil
 * @Author uschoe
 * @Date 2021.12.21
 */
@Component
public class CryptoUtil {

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

    private final Environment env;

    public CryptoUtil(Environment env){
        this.env = env;
    }

    /* 양방향 암호화 */
    public String encSeed(String msg) {

        String encStr = "";

        String encKey = env.getProperty("enc.seed.encKey");
        String vecKey = env.getProperty("enc.seed.decKey");
        int offset = Integer.parseInt(env.getProperty("enc.seed.offset"));

        if(null != msg && !"".equals(msg)) {

            byte[] pbszUserKey = null;
            byte[] pbszIV = null;
            byte[] msgToByte = null;

            try {

                pbszUserKey = encKey.getBytes("UTF-8");
                pbszIV = vecKey.getBytes("UTF-8");
                msgToByte = msg.getBytes("UTF-8");

            } catch (UnsupportedEncodingException uee) {

                StackTraceElement[] stes = uee.getStackTrace();

                for(StackTraceElement str : stes) {
                    logger.error(String.valueOf(str));
                };

            };

            int msgLength = msgToByte.length;
            byte[] encMsg;

            try {
                encMsg = KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, pbszIV, msgToByte, offset, msgLength);
                encStr = Base64.encodeBase64String(encMsg);
            } catch(Exception e) {
                StackTraceElement[] stes = e.getStackTrace();
                for(StackTraceElement str : stes) {
                    logger.error(String.valueOf(str));
                };
                encStr = msg;
            };
        } else {
            encStr = msg;
        };

        return encStr;
    };

    /* 양방향 복호화 */
    public String decSeed(String encMsg) {

        String decStr = "";

        String encKey = env.getProperty("enc.seed.encKey");
        String vecKey = env.getProperty("enc.seed.decKey");
        int offset = Integer.parseInt(env.getProperty("enc.seed.offset"));

        if(!encMsg.isEmpty()) {

            byte[] pbszUserKey = null;
            byte[] pbszIV = null;

            try {
                pbszIV = vecKey.getBytes("UTF-8");
                pbszUserKey = encKey.getBytes("UTF-8");
            } catch (UnsupportedEncodingException uee) {

                StackTraceElement[] stes = uee.getStackTrace();

                for(StackTraceElement str : stes) {
                    logger.error(String.valueOf(str));
                };

            };

            byte[] msgToByte = Base64.decodeBase64(encMsg);
            int msgLength = msgToByte.length;
            byte[] decMsg;

            try {

                decMsg = KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, pbszIV, msgToByte, offset, msgLength);
                decStr = new String(decMsg, StandardCharsets.UTF_8);

            } catch(Exception e) {

                StackTraceElement[] stes = e.getStackTrace();

                for(StackTraceElement str : stes) {
                    logger.error(String.valueOf(str));
                };

                decStr = encMsg;

            };

        } else {
            decStr = encMsg;
        };

        return decStr;
    }

}
