package org.perennial.gst_hero.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Handler.PurchaseRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase history Generation scheduler
 * Date:   08:04:2025
 * Time:   09:41 AM
 */
@Slf4j
@Service
public class PurchaseHistoryGenerationScheduler {

    @Autowired
    PurchaseRequestHandler purchaseRequestHandler;

    /**
     * Scheduler to Write purchase data into excel file in every 1 minutes
     * @throws IOException if any interruption occurred
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void writePurchaseData() throws IOException {
        log.info("START :: CLASS :: PurchaseHistoryGenerationScheduler :: METHOD :: writePurchaseData");
       purchaseRequestHandler.processPurchaseData();
        log.info("END :: CLASS :: PurchaseHistoryGenerationScheduler :: METHOD :: writePurchaseData");

    }
}
