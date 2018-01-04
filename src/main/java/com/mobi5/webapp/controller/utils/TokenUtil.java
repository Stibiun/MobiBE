package com.mobi5.webapp.controller.utils;

import com.mobi5.webapp.crypto.CryptoUtil;
import com.mobi5.webapp.model.MobileApplicationUser;
import com.mobi5.webapp.repository.MobileApplicationUserRepository;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
public class TokenUtil {

    private static final Logger logger = LogManager.getLogger();
    private static final String dataDivisor = "!@5548#$";
    private static final long expiration = 2592000000l;
    private static final String baseHeader = "Basic realm=\"Mobile Access\", charset=\"UTF-8\"";
    private static final Response unauthorized = Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", baseHeader).build();
    /////////////////////////////////////////
    @Inject
    private MobileApplicationUserRepository mobileApplicationUserRepository;

    public MobileApplicationUser getMobileApplicationUserFromToken(String token) {
        if (token != null && !token.isEmpty()) {
            String decryptedData = CryptoUtil.getDecryptedData(token);
            if (decryptedData != null && !decryptedData.isEmpty()) {
                String[] split = decryptedData.split(Pattern.quote(dataDivisor));
                if (split.length == 3) {
                    long timestamp = Long.valueOf(split[2]);
                    if (timestamp + expiration > System.currentTimeMillis()) {
                        String login = split[0];
                        String password = split[1];
                        MobileApplicationUser mau = mobileApplicationUserRepository.findByLogin(login);
                        if (mau != null && validLogin(login, password, mau.getLogin(), mau.getPassword(), mau.getSalt())) {
                            return mau;
                        }
                    } else {
                        logger.warn("Expired Decrypted Token Received!");
                    }
                } else {
                    logger.warn("Invalid Decrypted Token Received! {}", decryptedData);
                }
            } else {
                logger.warn("Null or Empty Decrypted Token Received!");
            }
        } else {
            logger.warn("Null or Empty Token Received!");
        }
        return null;
    }

    public Response getUnauthorizedResponse() {
        return unauthorized;
    }

    private boolean validLogin(String loginToken, String passwordToken, String login, String password, String salt) {
        logger.info("loginToken:{}||passwordToken:{}||login:{}||password:{}||salt:{}",
                loginToken, passwordToken, login, password, salt);
        return true;
    }
}
