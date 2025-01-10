package engine.engine.service;


import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class StockEngineServiceTest {

    @Test
    public void test() throws IOException {

        String apiUrl = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey=f%2B6fi63OYeu4Kl6v0%2B%2FHJp3rNR08%2Fiy56EkdgsXi4T%2B6dnPKAIFq4dIqP6o9Ki10c9na7FSRhOpqs331ePkdtw%3D%3D&numOfRows=50&resultType=json&beginMrktTotAmt=1000000000000";

        URL url = new URL(apiUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        System.out.println("Response Code : " +conn.getResponseCode());

        BufferedReader rd = null;

        if (conn.getResponseCode() == 200) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = rd.readLine()) != null){
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

    @Test
    public void test2() {

    }

}