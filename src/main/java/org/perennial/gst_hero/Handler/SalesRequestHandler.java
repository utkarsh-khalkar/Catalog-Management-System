package org.perennial.gst_hero.Handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.perennial.gst_hero.DTO.SalesDTO;
import org.perennial.gst_hero.DTO.SalesParameterDTO;
import org.perennial.gst_hero.Entity.Sales;
import org.perennial.gst_hero.Entity.SalesParameter;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.mapper.SalesMapper;
import org.perennial.gst_hero.mapper.SalesParameterMapper;
import org.perennial.gst_hero.service.SalesParameterService;
import org.perennial.gst_hero.service.SalesService;
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
 * Title:  Sales Request Handler Class
 * Date:   03-04-2025
 * Time:   09-54 AM
 */
@Slf4j
@Component
public class SalesRequestHandler {

    @Autowired
    private SalesService salesService;

    @Autowired
    private SalesParameterService salesParameterService;

    @Autowired
    private UserService userService;

    /**
     * Method to save sales details in database
     * @param salesDTO object to save
     */
    public void saveSalesDetails(SalesDTO salesDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SalesCategory :: "+salesDTO.getCategoryCode());
        Optional<User> user=userService.findUserById(salesDTO.getUserId());
        Sales sales= SalesMapper.toModel(salesDTO,user.get());
        salesService.save(sales);
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SalesCategory :: "+salesDTO.getCategoryCode());
    }

    /**
     * Method to find user by id
     * @param userId to find
     * @return true if present otherwise false
     */
    public boolean findUserById(long userId) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: findUserById :: userId :: "+userId);
        Optional<User> user=userService.findUserById(userId);
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: findUserById :: userId :: "+userId);
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
     * Method to save sales parameter
     * @param salesParameterDTO to save
     */
    public void saveSalesParameterDetails(SalesParameterDTO salesParameterDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesParameterDetails :: USER_ID :: "+salesParameterDTO.getUserId());
        SalesParameter salesParameter= SalesParameterMapper.toModel(salesParameterDTO);
        salesParameterService.saveSalesParameter(salesParameter);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesParameterDetails :: USER_ID :: "+salesParameterDTO.getUserId());
    }

    /**
     * Method to process sales data
     */
    public void salesDataProcessing() throws IOException {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: salesDataProcessing");
        List<SalesParameter> salesParameters=salesParameterService.findAllSalesParameterByStatus("OPEN");
        int baseBatchSize=100;
        int maxBatchSize=1000;


        for (SalesParameter salesParameter : salesParameters) {
            int totalRecord=salesService.getCountOfSalesRecord(salesParameter.getFinancialYear(),salesParameter.getStartDate(),salesParameter.getEndDate(),salesParameter.getMonth(),salesParameter.getUserId());
            int offset=0;
            List<Sales> salesBatch;
            int batchSize=Math.min(baseBatchSize+(totalRecord/1000),maxBatchSize);
            try {
                do{
                    salesBatch=salesService.findSalesDetailsWithBatchSize(salesParameter.getFinancialYear(),salesParameter.getStartDate(),salesParameter.getEndDate(),salesParameter.getMonth(),salesParameter.getUserId(),batchSize,offset);
                    if (!salesBatch.isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss");
                        String formattedTime = LocalDateTime.now().format(formatter);
                        String fileName = "Sales_History_Updated_At_" + formattedTime + ".xlsx";

                        writeSalesData(salesBatch,fileName);
                        offset+=batchSize;

                    }
                }while (!salesBatch.isEmpty());

                // updating status after writing data into file
                salesParameter.setStatus("DONE");
                salesParameter.setUpdatedAt(LocalDate.now());
                salesParameterService.saveSalesParameter(salesParameter);
            }catch (Exception e) {
                e.printStackTrace();
            }


        }
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: salesDataProcessing");
    }

    /**
     *
     * @param salesBatch batch of data to write in excel
     * @param filename name of xml file
     * @throws IOException if any interruption o while writing data
     */
    public void writeSalesData(List<Sales> salesBatch, String filename) throws IOException {
        log.info("START :: CLASS :: SalesParameterServiceImpl :: METHOD :: writeSalesData :: salesBatchSize ::"+salesBatch.size());

        try {

            // work book creation
            XSSFWorkbook workbook=new XSSFWorkbook();

            FileOutputStream fileOutputStream=new FileOutputStream(filename);

            Sheet sheet=workbook.createSheet("Sales History");

            // creating sheet header row
            Row rowHeader=sheet.createRow(0);
            rowHeader.createCell(0).setCellValue("SALES ID");
            rowHeader.createCell(1).setCellValue("CATEGORY CODE");
            rowHeader.createCell(2).setCellValue("PRODUCT CODE");
            rowHeader.createCell(3).setCellValue("SALES DATE");
            rowHeader.createCell(4).setCellValue("FINANCIAL YEAR");
            rowHeader.createCell(5).setCellValue("PRODUCT PRICE");
            rowHeader.createCell(6).setCellValue("QUANTITY");


            // create cell style for comma separated value
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

            // Create cell style for date-time format
            CellStyle cellStyle1 = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));

            int rowIndex=1;
            for (Sales sales : salesBatch) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(sales.getSales_id());
                row.createCell(1).setCellValue(sales.getCategoryCode());
                row.createCell(2).setCellValue(sales.getProductCode());
                Cell cell0=row.createCell(3);
                cell0.setCellValue(sales.getCreatedAt());
                cell0.setCellStyle(cellStyle1);
                row.createCell(4).setCellValue(sales.getFinancialYear());
                Cell cell=row.createCell(5);
                cell.setCellValue(sales.getProductPrice());
                cell.setCellStyle(cellStyle);
                row.createCell(6).setCellValue(sales.getQuantity());
            }
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("END :: CLASS :: SalesParameterServiceImpl :: METHOD :: writeSalesData :: salesBatchSize ::"+salesBatch.size());
    }

    /**
     * Method to check if the report is already generated
     * @param salesParameterDTO to check parameters are already present
     * @return true if already generated, else return false
     */
    public boolean isReportGenerated(SalesParameterDTO salesParameterDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: isReportGenerated :: SalesParameter: {}", salesParameterDTO);

        List<SalesParameter> salesParameters = salesParameterService.findAllSalesParameterByStatus("DONE");

        String currentFinancialYear="2024-2025";
        boolean isGenerated = false;
        for (SalesParameter salesParameter : salesParameters) {
            if (salesParameter.getFinancialYear().equals(salesParameterDTO.getFinancialYear()) && !salesParameterDTO.getFinancialYear().equals(currentFinancialYear)) {
                isGenerated = true;
                break;
            }
        }

        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: isReportGenerated :: SalesParameter: {}", salesParameterDTO);
        return isGenerated;
    }

}
