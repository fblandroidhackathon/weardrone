package caprica6.fblandroidhackathon.org.caprica6;

import retrofit.http.Body;
import retrofit.http.POST;

public interface NodeService {

    @POST("/cmd")
    void sendCommand(@Body CommandJson cmdJson);

}

