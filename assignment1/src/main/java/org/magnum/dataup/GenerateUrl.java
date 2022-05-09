package org.magnum.dataup;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class GenerateUrl {

    private String getDataUrl(long videoId){
        return getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
    }

    public static String getUrlBaseForLocalServer() {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return "http://" + request.getServerName()
                + ((request.getServerPort() != 80) ? ":" + request.getServerPort() : "");
    }
}
