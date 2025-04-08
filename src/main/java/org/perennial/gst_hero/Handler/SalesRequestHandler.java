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
import org.perennial.gst_hero.exception.SalesRecordNotFoundException;
import org.perennial.gst_hero.exception.UserNotFoundException;
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
    public long saveSalesDetails(SalesDTO salesDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SalesDTO:{} :: ",salesDTO);
        Optional<User> user=userService.findUserById(salesDTO.getUserId());
        Sales sales= SalesMapper.toModel(salesDTO,user.get());
        Sales sales1=salesService.saveSalesDetails(sales);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SalesDTO:{} :: ",salesDTO);
        return sales1.getSales_id();
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
     * Method to save sales parameter
     * @param salesParameterDTO to save
     */
    public void saveSalesParameterDetails(SalesParameterDTO salesParameterDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesParameterDetails :: USER_ID :: "
                +salesParameterDTO.getUserId());
        SalesParameter salesParameter= SalesParameterMapper.toModel(salesParameterDTO);
        salesParameterService.saveSalesParameter(salesParameter);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesParameterDetails :: USER_ID :: "
                +salesParameterDTO.getUserId());
    }

    /**
     * Method to process sales data
     */
    public void salesDataProcessing() throws IOException {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: salesDataProcessing");
        List<SalesParameter> salesParameters=salesParameterService.findAllSalesParameterByStatus("OPEN");
        int baseBatchSize=100;
        int maxBatchSize=1000;

        if (salesParameters !=null)
        {
            for (SalesParameter salesParameter : salesParameters) {
                int userId=salesParameter.getUserId();
                int totalRecord=salesService.getCountOfSalesRecord(salesParameter.getFinancialYear(),
                        salesParameter.getStartDate(),salesParameter.getEndDate(),salesParameter.getMonth(),userId);
                int offset=0;
                List<Sales> salesBatch;
                int batchSize=Math.min(baseBatchSize+(totalRecord/1000),maxBatchSize);
                try {
                    do{
                        salesBatch=salesService.findSalesDetailsWithBatchSize(salesParameter.getFinancialYear(),
                                salesParameter.getStartDate(),salesParameter.getEndDate(),salesParameter.getMonth(),
                                userId,batchSize,offset);
                        if (!salesBatch.isEmpty()) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy hh-mm-ss");
                            String formattedTime = LocalDateTime.now().format(formatter);
                            String fileName = "Sales_History_" + userId + "_At_" + formattedTime + ".xlsx";

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
        }else {
            log.info("MESSAGE :: No Parameter Found to Write Sales History ::CLASS :: SalesRequestHandler :: " +
                    "METHOD :: salesDataProcessing");
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
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: writeSalesData :: salesBatchSize ::"+salesBatch.size());

        try {

            // work book creation
            XSSFWorkbook workbook=new XSSFWorkbook();

            FileOutputStream fileOutputStream=new FileOutputStream(filename);

            Sheet sheet=workbook.createSheet("Sales History");

            // creating sheet header row
            Row rowHeader=sheet.createRow(0);
            rowHeader.createCell(0).setCellValue("SALES ID");
            rowHeader.createCell(1).setCellValue("CATEGORY NAME");
            rowHeader.createCell(2).setCellValue("PRODUCT NAME");
            rowHeader.createCell(3).setCellValue("SALES DATE");
            rowHeader.createCell(4).setCellValue("FINANCIAL YEAR");
            rowHeader.createCell(5).setCellValue("PRODUCT PRICE");
            rowHeader.createCell(6).setCellValue("QUANTITY");
            rowHeader.createCell(7).setCellValue("TOTAL AMOUNT");


            // create cell style for comma separated value
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

            // Create cell style for date-time format
            CellStyle cellStyle1 = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("dd-mm-yyyy "));

            int rowIndex=1;
            for (Sales sales : salesBatch) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(sales.getSales_id());
                row.createCell(1).setCellValue(sales.getCategoryName());
                row.createCell(2).setCellValue(sales.getProductName());
                Cell cell0=row.createCell(3);
                cell0.setCellValue(sales.getCreatedAt());
                cell0.setCellStyle(cellStyle1);
                row.createCell(4).setCellValue(sales.getFinancialYear());
                Cell cell=row.createCell(5);
                cell.setCellValue(sales.getProductPrice());
                cell.setCellStyle(cellStyle);
                row.createCell(6).setCellValue(sales.getQuantity());
                Cell cell1=row.createCell(7);
                cell1.setCellValue(sales.getTotalPrice());
                cell1.setCellStyle(cellStyle);
            }
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: writeSalesData :: salesBatchSize ::"+salesBatch.size());
    }



    /**
     * Method to delete sales details by sales date
     * @param saleDate to delete sales details
     */
    public void deleteSalesDetailsBySaleDate(LocalDate saleDate) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: deleteSalesDetailsBySaleDate :: Sale Date:{}",saleDate);
        salesService.deleteBySaleDate(saleDate);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: deleteSalesDetailsBySaleDate :: Sale Date:{}",saleDate);
    }


    /**
     * Method to Check financial year is current or not
     * @param financialYear to check is current financial or not
     * @return true if current financial year else false
     */
    public boolean isCurrentFinancialYear(String financialYear) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: isCurrentFinancialYear :: Financial Year:{}",
                financialYear);
        boolean isCurrFinancialYear=false;
        if (financialYear.equals("2025-2026")) {
            isCurrFinancialYear=true;
        }
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: isCurrentFinancialYear :: Financial Year:{}",
                financialYear);
        return isCurrFinancialYear;

    }

    /**
     * Method to update sales details in database
     * @param salesDTO object to update
     */
    public void updateSalesDetails(long salesId,SalesDTO salesDTO) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: updateSalesDetails :: SalesDTO:{} :: ",salesDTO);
        Sales sales=salesService.findBySalesId(salesId);

        Optional<User> user=userService.findUserById(salesDTO.getUserId());

        sales.setCategoryName(salesDTO.getCategoryName());
        sales.setProductName(salesDTO.getProductName());
        sales.setQuantity(salesDTO.getQuantity());
        sales.setUpdatedAt(LocalDate.now());
        sales.setFinancialYear(salesDTO.getFinancialYear());
        sales.setProductPrice(salesDTO.getProductPrice());
        double totalPrice= salesDTO.getProductPrice()*salesDTO.getQuantity();
        sales.setTotalPrice(totalPrice);

        sales.setUser(user.get());
        salesService.saveSalesDetails(sales);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: updateSalesDetails :: SalesDTO:{} :: ",salesDTO);
    }

    /**
     * Method to check sales record is present or not
     * @param salesID to check sales record
     * @return true if sales record present else false
     */
    public boolean isRecordExist(long salesID) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: isRecordExist Sales ID:{}",salesID);
        boolean isRecordExist=false;
        Sales sales=salesService.findBySalesId(salesID);
        if (sales != null) {
            isRecordExist=true;
        }
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: isRecordExist Sales ID:{}",salesID);
        return isRecordExist;
    }

    /**
     * Method to check report is generated or not
     * @param financialYear to find report is generated
     * @return true if report is generated else false
     */
    public boolean isReportGenerated(String financialYear) {
        log.info("START :: CLASS :: SalesRequestHandler :: METHOD:: isReportGenerated :: FinancialYear:{}",financialYear);
        boolean isReportGenerated=false;
        SalesParameter salesParameter=salesParameterService.findByFinancialYear(financialYear);
        if (salesParameter != null) {
            isReportGenerated=true;
        }
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD:: isReportGenerated:{}",financialYear);
        return isReportGenerated;
    }

}
