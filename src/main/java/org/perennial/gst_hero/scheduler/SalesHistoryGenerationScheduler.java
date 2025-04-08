package org.perennial.gst_hero.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Handler.PurchaseRequestHandler;
import org.perennial.gst_hero.Handler.SalesRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author: Utkarsh Khakar
 * Title:  Sales history generation scheduler
 * Date:   04-04-2025
 * Time:   09:31
 */
@Slf4j
@Service
public class SalesHistoryGenerationScheduler {

    @Autowired
    private SalesRequestHandler salesRequestHandler;

    /**
     * Scheduler to Write sales data into excel file in every 1 minutes
     * @throws IOException if any interruption occurred
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void writeSalesData() throws IOException {
        log.info("START :: CLASS :: SalesHistoryGenerationScheduler :: METHOD :: writeSalesData");
        salesRequestHandler.salesDataProcessing();
        log.info("END :: CLASS :: SalesHistoryGenerationScheduler :: METHOD :: writeSalesData");
    }
}
