package engine.engine.service;
import com.google.gson.Gson;
import engine.engine.StockData;
import engine.engine.entity.Stock;
import engine.engine.entity.StockOrder;
import engine.engine.repository.StockOrderRepository;
import engine.engine.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static engine.engine.propertis.Const.API_URL;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StockEngineService{

    private final StockRepository stockRepository;
    private final StockOrderRepository stockOrderRepository;

    public HttpURLConnection getHttpURLConnection(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    public String getStockDataFromConnection(HttpURLConnection conn) throws IOException {
        BufferedReader reader;
        if (conn.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder stockInfo = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stockInfo.append(line);
        }

        reader.close();
        conn.disconnect();
        return stockInfo.toString();
    }

    public List<StockData.Item> parseStockData(String stockInfo) {
        Gson gson = new Gson();
        StockData stockData = gson.fromJson(stockInfo, StockData.class);
        return stockData.response.body.items.itemList;
    }

    public void saveStockData(List<StockData.Item> items) {
        for (StockData.Item item : items) {
            Stock stock = stockRepository.findByStockName(item.itmsNm);
            if (stock == null) {
                stock = new Stock();
                stock.setStockName(item.itmsNm);
                stock.setStockPrice(Integer.valueOf(item.clpr));
                stock.setBasDt(item.basDt);

                stock.setVs(item.vs);
                stock.setFltRt(item.fltRt);
                stock.setMkp(item.mkp);
                stock.setHipr(item.hipr);
                stock.setLopr(item.lopr);

                stockRepository.save(stock);
            } else {
                stock.setStockPrice(Integer.valueOf(item.clpr));
                stock.setStockName(item.itmsNm);
                stock.setBasDt(item.basDt);

                stock.setVs(item.vs);
                stock.setFltRt(item.fltRt);
                stock.setMkp(item.mkp);
                stock.setHipr(item.hipr);
                stock.setLopr(item.lopr);

                stockRepository.save(stock);
            }
        }
    }

    public void saveStockSevenAgo(List<StockData.Item> items) {
        try {
            for (StockData.Item item : items) {
                Stock stock = stockRepository.findByStockName(item.itmsNm);
                if (stock == null) {
                    stock = new Stock();
                    stock.setStockName(item.itmsNm);
                    stock.setStockPrice(Integer.valueOf(item.clpr));
                    stock.setBasDt(item.basDt);

                    stock.setVs(item.vs);
                    stock.setFltRt(item.fltRt);
                    stock.setMkp(item.mkp);
                    stock.setHipr(item.hipr);
                    stock.setLopr(item.lopr);

                    stockRepository.save(stock);
                } else if (null != item.clpr && !item.clpr.isEmpty()){
                    stock.setPriceLastWeek(Integer.valueOf(item.clpr));
                    stockRepository.save(stock);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String lastDays(Integer days) {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.minusDays(days);
        return localDate.toString().replaceAll("-", "");
    }

    public void updateStockOrder(List<StockData.Item> items) {
        try {
            for (StockData.Item item : items) {
                List<StockOrder> stockOrder = stockOrderRepository.findByStockName(item.itmsNm);
                if (null != stockOrder) {
                    for (StockOrder stock : stockOrder) {
                        stock.setCurrentStockPrice(Integer.valueOf(item.clpr));
                        stock.setCurrentTotalPrice(stock.getCurrentStockPrice() * stock.getStockCount());
                        stockOrderRepository.save(stock);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
