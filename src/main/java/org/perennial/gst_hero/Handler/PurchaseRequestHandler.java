package org.perennial.gst_hero.Handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.perennial.gst_hero.DTO.PurchaseDTO;
import org.perennial.gst_hero.DTO.PurchaseParameterDTO;
import org.perennial.gst_hero.DTO.SalesParameterDTO;
import org.perennial.gst_hero.Entity.*;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.mapper.PurchaseMapper;
import org.perennial.gst_hero.mapper.PurchaseParameterMapper;
import org.perennial.gst_hero.service.PurchaseParameterService;
import org.perennial.gst_hero.service.PurchaseService;
import org.perennial.gst_hero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Request Handler class
 * Date:   07-04-2025
 * Time:   10:08 AM
 */
@Slf4j
@Component
public class PurchaseRequestHandler {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseParameterService purchaseParameterService;

    /**
     * Method to save purchase details to db
     * @param purchaseDTO to save purchase details
     */
    public void savePurchaseDetails(PurchaseDTO purchaseDTO) {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: savePurchaseDetails :: PURCHASE DETAILS ::"+purchaseDTO);
        Optional<User> user=userService.findUserById(purchaseDTO.getUserId());
        Purchase purchase= PurchaseMapper.toPurchase(purchaseDTO,user.get());
        purchaseService.savePurchaseDetails(purchase);
        log.info("END  :: CLASS :: PurchaseRequestHandler :: METHOD :: savePurchaseDetails :: PURCHASE DETAILS ::"+purchaseDTO);
    }
    /**
     * Method to find user by id
     * @param userId to find
     * @return true if present otherwise false
     */
    public boolean findUserById(long userId) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: findUserById :: userId :: "+userId);
        Optional<User> user=userService.findUserById(userId);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: findUserById :: userId :: "+userId);
        return user.isPresent();
    }

    /**
     * @param data       that you want pass
     * @param message    as response
     * @param httpStatus status code
     * @param status     1means pass 0 means fail
     * @param <T>        accepts any type of data string number
     * @return api response
     */
    public <T> ApiResponseDTO<T> apiResponse(T data, String message, int httpStatus, int status) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: apiResponse");

        ApiResponseDTO<T> apiResponseDTO = new ApiResponseDTO<>(
                data,
                message,
                httpStatus,
                status
        );

        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: apiResponse");
        return apiResponseDTO;
    }

    /**
     * Method to save purchase parameter details
     * @param purchaseParameterDTO object to save purchase parameters
     */
    public void savePurchaseParameter(PurchaseParameterDTO purchaseParameterDTO) {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: savePurchaseParameter :: purchaseDTO:{} :: ",purchaseParameterDTO);
        PurchaseParameter purchaseParameter= PurchaseParameterMapper.toModel(purchaseParameterDTO);
        purchaseParameterService.savePurchaseParameter(purchaseParameter);
        log.info("END :: CLASS :: PurchaseRequestHandler :: METHOD :: savePurchaseParameter :: purchaseDTO:{} :: ",purchaseParameterDTO);
    }

    public void processPurchaseData() {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: processPurchaseData");
        List<PurchaseParameter> purchaseParameters=purchaseParameterService.findAllPurchaseParameterByStatus("OPEN");
        int baseBatchSize=100;
        int maxBatchSize=10000;

        for (PurchaseParameter purchaseParameter : purchaseParameters) {
            int totalRecord=purchaseService.getCountOfPurchaseDetails(purchaseParameter.getFinancialYear(),purchaseParameter.getMonth(),purchaseParameter.getSellerName(),purchaseParameter.getUserId());
            int offset=0;

            int batchSize=Math.min(baseBatchSize+(totalRecord/1000),maxBatchSize);
            List<Purchase> purchasesBatch;
            try {

                do {
                    purchasesBatch=purchaseService.findPurchaseDetailsWithBatchSize(purchaseParameter.getFinancialYear(),purchaseParameter.getMonth(),purchaseParameter.getSellerName(),purchaseParameter.getUserId(),batchSize,offset);
                    if (!purchasesBatch.isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss");
                        String formattedTime = LocalDateTime.now().format(formatter);
                        String fileName = "Purchase_History_Created_At_" + formattedTime + ".xlsx";

                        writePurchaseData(purchasesBatch,fileName);
                        offset+=batchSize;
                    }
                } while (!purchasesBatch.isEmpty());
                // updating status after writing data
                purchaseParameter.setStatus("DONE");
                purchaseParameter.setUpdatedAt(LocalDate.now());
                purchaseParameterService.savePurchaseParameter(purchaseParameter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("END :: CLASS :: PurchaseRequestHandler :: METHOD :: processPurchaseData");
    }

    /**
     *
     * @param purchaseBatch batch of data to write in excel
     * @param filename name of xml file
     * @throws IOException if any interruption o while writing data
     */
    public void writePurchaseData(List<Purchase>  purchaseBatch, String filename) throws IOException {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: writePurchaseData :: purchaseBatchSize ::"+purchaseBatch.size());

        try {

            // work book creation
            XSSFWorkbook workbook=new XSSFWorkbook();

            FileOutputStream fileOutputStream=new FileOutputStream(filename);

            Sheet sheet=workbook.createSheet("Purchase  History");

            // creating sheet header row
            Row rowHeader=sheet.createRow(0);
            rowHeader.createCell(0).setCellValue("PURCHASE ID");
            rowHeader.createCell(1).setCellValue("CATEGORY CODE");
            rowHeader.createCell(2).setCellValue("PRODUCT CODE");
            rowHeader.createCell(3).setCellValue("PURCHASE DATE");
            rowHeader.createCell(4).setCellValue("FINANCIAL YEAR");
            rowHeader.createCell(5).setCellValue("PRICE");
            rowHeader.createCell(6).setCellValue("QUANTITY");
            rowHeader.createCell(7).setCellValue("SELLER NAME");


            // create cell style for comma separated value
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

            // Create cell style for date-time format
            CellStyle cellStyle1 = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd "));

            int rowIndex=1;
            for (Purchase purchase : purchaseBatch) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(purchase.getPurchase_id());
                row.createCell(1).setCellValue(purchase.getCategoryCode());
                row.createCell(2).setCellValue(purchase.getProduct_code());
                Cell cell=row.createCell(3);
                cell.setCellValue(purchase.getCreatedAt());
                cell.setCellStyle(cellStyle1);
                row.createCell(4).setCellValue(purchase.getFinancialYear());
                Cell cell1=row.createCell(5);
                cell1.setCellValue(purchase.getPrice());
                cell1.setCellStyle(cellStyle);
                row.createCell(6).setCellValue(purchase.getQuantity());
                row.createCell(7).setCellValue(purchase.getSellerName());

            }
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("END :: CLASS :: PurchaseRequestHandler :: METHOD :: writePurchaseData :: purchaseBatchSize ::"+purchaseBatch.size());
    }
    /**
     * Method to check if the report is already generated
     * @param purchaseParameterDTO to check parameters are already present
     * @return true if already generated, else return false
     */
    public boolean isReportGenerated(PurchaseParameterDTO purchaseParameterDTO) {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: isReportGenerated :: purchaseParameterDTO ::"+purchaseParameterDTO);

        List<PurchaseParameter> purchaseParameters=purchaseParameterService.findAllPurchaseParameterByStatus("DONE");

        String currentFinancialYear="2024-2025";
        boolean isGenerated = false;
        for (PurchaseParameter purchaseParameter : purchaseParameters) {
            if (purchaseParameter.getFinancialYear().equals(purchaseParameterDTO.getFinancialYear()) && !purchaseParameterDTO.getFinancialYear().equals(currentFinancialYear)) {
                isGenerated = true;
                break;
            }
        }
        log.info("END :: CLASS :: PurchaseRequestHandler :: METHOD :: isReportGenerated :: purchaseParameterDTO ::"+purchaseParameterDTO);
        return isGenerated;
    }

    /**
     * Method to delete purchase details by purchase date
     * @param purchaseDate to delete purchase details
     */
    public void deletePurchaseDetailsByPurchaseDate(LocalDate purchaseDate) {
        log.info("START :: CLASS :: PurchaseRequestHandler :: METHOD :: deletePurchaseDetailsByPurchaseDate :: Date:{}",purchaseDate);
        purchaseService.deletePurchaseDetailsByPurchaseDate(purchaseDate);
        log.info("END :: CLASS :: PurchaseRequestHandler :: METHOD :: deletePurchaseDetailsByPurchaseDate :: Date:{}",purchaseDate);
    }


}
