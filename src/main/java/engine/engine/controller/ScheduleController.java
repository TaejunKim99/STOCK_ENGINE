package engine.engine.controller;

import engine.engine.StockData;
import engine.engine.repository.StockRepository;
import engine.engine.service.StockEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.List;

import static engine.engine.propertis.Const.*;

@Slf4j
@Controller
public class ScheduleController {

    private final StockEngineService stockEngineService;

    public ScheduleController(StockEngineService stockEngineService) {
        this.stockEngineService = stockEngineService;
    }

    @Scheduled(fixedDelay = 1000000000)
    public void scheduledStockEngine() {
        log.info("***** scheduledStockEngine start *****");

        try {
            HttpURLConnection conn = stockEngineService.getHttpURLConnection(API_URL);
            String stockInfo = stockEngineService.getStockDataFromConnection(conn);

            List<StockData.Item> items = stockEngineService.parseStockData(stockInfo);
            stockEngineService.saveStockData(items);
            stockEngineService.updateStockOrder(items);

            log.info("***** scheduledStockEngine done *****");
        } catch (Exception e) {
            log.error("Error in scheduledStockEngine: {}", e.getMessage(), e);
        }
    }
    @Scheduled(fixedDelay = 1000000000)
    public void beforeSevenDaysPrice() {
        log.info("***** beforeSevenDaysPrice start *****");
        StringBuilder API_URL_LAST_WEEK = new StringBuilder(API_URL);

        String lastWeekDays = stockEngineService.lastDays(LAST_WEEK);
        API_URL_LAST_WEEK.append("&basDt=");
        API_URL_LAST_WEEK.append(lastWeekDays);

        try {
            HttpURLConnection conn = stockEngineService.getHttpURLConnection(API_URL_LAST_WEEK.toString());
            String stockInfo = stockEngineService.getStockDataFromConnection(conn);

            List<StockData.Item> items = stockEngineService.parseStockData(stockInfo);
            stockEngineService.saveStockSevenAgo(items);

            log.info("***** beforeSevenDaysPrice done *****");
        } catch (Exception e) {
            log.error("Error in beforeSevenDaysPrice: {}", e.getMessage(), e);
        }
    }



}
