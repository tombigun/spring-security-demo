package com.security.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Api认证过滤链
 */
public class ApiAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthorizationFilter.class);

    private static final String ENCODING = "UTF-8";

    private String mappingURL = "";
    private boolean checkAuth = true;
    private long timeInterval = 10 * 60 * 1000l;// 10分钟
    private Map<String, String> keys = new HashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkAuth && request.getRequestURI().matches(mappingURL)) {
            if (checkAuth(request, response))
                filterChain.doFilter(request, response);
        } else
            filterChain.doFilter(request, response);
    }

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Authorization: 授权编号:时间戳:签名
        final String authorization = request.getHeader("X-Authorization");

        LOGGER.info("authorization:{}", authorization);

        if (authorization == null) {
            writeError(response, "认证信息:未发现[Auth-01]");

            return false;
        }

        final String[] auths = authorization.split(":");

        if (auths.length != 3) {
            writeError(response, "认证信息:格式错误[Auth-02]");

            return false;
        }

        // 签名 = HmacSHA256(授权编号 + "." + 时间戳 + "." + base64UrlEncode(URI), 授权密钥)
        final String uri = request.getRequestURI();
        final String sqbh = auths[0];
        final String sjc = auths[1];
        final String auth_1 = auths[2];

        if (!keys.containsKey(sqbh)) {
            writeError(response, "认证信息:授权编号错误[Auth-03]");

            return false;
        }

        if (!sjc.matches("[0-9]+")) {
            writeError(response, "认证信息:时间戳错误[Auth-04]");

            return false;
        }

        final long time_1 = Long.valueOf(sjc);
        final long time_2 = System.currentTimeMillis();
        final long t1 = time_2 - time_1;

        if (Math.abs(t1) > timeInterval) {
            writeError(response, "认证信息:超过有效时间[Auth-05]");

            return false;
        }

        String auth_2 = HmacSHA256.encode(sqbh, keys.get(sqbh), sjc, uri);

        LOGGER.info("期望签名:{},实际签名:{},检验状态:{}", auth_2, auth_1, "成功");
        if (!auth_1.toUpperCase().equals(auth_2.toUpperCase())) {
            LOGGER.info("期望签名:{},实际签名:{},检验状态:{}", auth_2, auth_1, "失败");
            writeError(response, "认证信息:签名信息错误[Auth-06]");

            return false;
        }

        return true;
    }

    private void writeError(HttpServletResponse response, String s) throws IOException {
        response.setStatus(403);
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().println(s);
    }

    public void setCheckAuth(boolean checkAuth) {
        this.checkAuth = checkAuth;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    public void setApikeys(String apikeys) {
        String[] ss = apikeys.split(";");
        for (String key: ss) {
            String[] s = key.split(":", 2);
            if(s.length == 2)
                keys.put(s[0], s[1]);
        }
    }

    static class HmacSHA256 {

        public static String encode(String sqbh, String sqmy, String sjc, String uri) {
            String hash = null;

            try {
                String message = sqbh + "." + sjc + "." + new String(Base64.encode(uri.getBytes()), ENCODING);

                Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
                SecretKeySpec secret_key = new SecretKeySpec(sqmy.getBytes(), "HmacSHA256");
                sha256_HMAC.init(secret_key);

                hash = new String(Base64.encode(sha256_HMAC.doFinal(message.getBytes())), ENCODING);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            return hash;
        }

    }

}
