package net.winroad.dubbo;

import com.alibaba.dubbo.config.annotation.Service;

import javax.ws.rs.*;
import java.util.List;

@Path("popular")
@Service(version = "1.0.0", protocol = {"rest"}, retries = 0)
public class RestServiceImpl implements RestService{

    @Override
    @GET
    @Path("hotstar")
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
    public List<String> getHotStars(int count,
                                    @javax.ws.rs.QueryParam(QueryParamKeys.APP_ID) String appid,
                                    @HeaderParam(QueryParamKeys.SECURITY_KEY) String securityKey) {
        return null;
    }
}
