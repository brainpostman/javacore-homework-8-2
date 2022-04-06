import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request1 = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=BgBx8G7vo2tjVrBwFeVBBbkqEcFeBOpPDCuwZe6w");
        CloseableHttpResponse response = httpClient.execute(request1);
        NASAResponse obj = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {});
        HttpGet request2 = new HttpGet(obj.getUrl());
        String[] name = obj.getUrl().split("/");
        CloseableHttpResponse response2 = httpClient.execute(request2);
        FileOutputStream fos = new FileOutputStream(name[6]);
        response2.getEntity().writeTo(fos);
        fos.close();
    }
}
