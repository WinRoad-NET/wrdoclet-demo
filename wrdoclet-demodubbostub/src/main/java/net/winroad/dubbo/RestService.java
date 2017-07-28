package net.winroad.dubbo;

import javax.validation.constraints.NotNull;

import java.util.List;

public interface RestService {
    /**
     * 获取当前热门明星
     * @param count 获取数目
     * @return 热门明星名称
     */
    List<String> getHotStars(int count,
                             String appid,
                             @NotNull String securityKey);
}
