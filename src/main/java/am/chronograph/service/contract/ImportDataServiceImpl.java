package am.chronograph.service.contract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.contract.ContractDao;
import am.chronograph.dao.contract.ExcelContractIncomeDao;
import am.chronograph.dao.contract.ImportExcelSearchCriteria;
import am.chronograph.dao.contract.ImportedExcelDao;
import am.chronograph.domain.contract.Contract;
import am.chronograph.domain.contract.ExcelContractIncome;
import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultBean;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.bean.contract.ImportedExcelBean;
import am.chronograph.web.bean.upload.UploadedFileBean;
import am.chronograph.web.util.DateUtil;

/**
 * Implements ImportDataService interface...
 * 
 * @author HARUT
 */
@Service
@Transactional(readOnly = true)
public class ImportDataServiceImpl implements ImportDataService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataServiceImpl.class);
    
    /**
     * The step attachments folder.
     */
    private static final String CONTRACT_EXCEL_FOLDER = "IMPORTED_EXCELS";
    
    private static final String ORIGIN_FOLDER = "ORIGINAL";
    private static final String REMAINING_FOLDER = "REMAINING";        

    @Autowired
    private ImportedExcelDao importedExcelDao;
    
    @Autowired
    private ContractDao contractDao;
    
    @Autowired
    private ExcelContractIncomeDao excelContractIncomeDao;
    
    @Value("${storage.path}")
    private String storageHome;
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#deleteFile(java.lang.Long)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteFile(Long id) {
    	ImportedExcel excel = importedExcelDao.getById(id);
        importedExcelDao.delete(excel);
        
        String dirPath = getFileDirPath(excel.getFileName());
        ChronoUtil.deleteDirectory(dirPath);
    }        
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.SearchBeanAware#search(am.chronograph.search.SearchCriteria)
     */
    @Override
    public SearchResultBean<ImportedExcelBean> search(SearchCriteria<ImportedExcel> criteria) {
        SearchResult<ImportedExcel> searchResult = importedExcelDao.search(criteria);
        
        SearchResultBean<ImportedExcelBean> searchResultBean = new SearchResultBean<>();
        searchResultBean.setCount(searchResult.getTotalResultCount());
        searchResultBean.setResult(initBeanListByDomainList(searchResult.getResult()));
                
        return searchResultBean;
    }

    /*
     * (non-Javadoc)
     * @see am.chronograph.service.SearchBeanAware#getEmptyCriteria()
     */
    @Override
    public SearchCriteria<ImportedExcel> getEmptyCriteria() {        
        return new ImportExcelSearchCriteria();
    }

    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#existFileByNameNotById(java.lang.String, java.lang.Long)
     */
    @Override
    public boolean existFileByName(String name) {
        ImportedExcel excel = importedExcelDao.getByName(name);
        if(excel == null) {
            return false;
        }
        
        return true;
    }    
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#existFileByName(java.lang.String, java.util.List)
     */
    @Override
    public boolean existFileByName(String fileName, List<String> agentTypes) {
    	String fileNameWithoutFormat = getFileNameWithoutFormat(fileName);
    	
    	List<ImportedExcel> excels= importedExcelDao.getFileLikeName(fileNameWithoutFormat);
    	for(ImportedExcel excel : excels) {
    		String excelFileName = getFileNameWithoutFormat(excel.getFileName());
    		if(!isCorrectFile(fileNameWithoutFormat, excelFileName)) {
    			continue;
    		}
    		
    		if(isFileNameContainAgentType(excelFileName, agentTypes)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Checks whether imported excel has been imported for one of given agent types or not...
     * @param importedExcelName
     * @param agentTypes
     * @return
     */
    private boolean isFileNameContainAgentType(String importedExcelName, List<String> agentTypes) {
    	for(String agentType : agentTypes) {
    		if(importedExcelName.contains("_" + agentType)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Checks whether excel file is has the name which we are trying to check for validness regarding importing for specified agents.
     * e.g. 
     * original file name == "qaxvacq"
     * excel file name can be "qaxvacq_N" or "qaxvacq_T_V"
     * BUT excel file name == "qaxvacq2" - is not the one we are interested in.
     * 
     * @param fileNameWithoutFormat
     * @param excelFileNameWithoutFormat
     * @return
     */
    private boolean isCorrectFile(String fileNameWithoutFormat, String excelFileNameWithoutFormat) {
    	String remaningPart = excelFileNameWithoutFormat.replace(fileNameWithoutFormat, "");
    	remaningPart = remaningPart.replace("_N", "");
    	remaningPart = remaningPart.replace("_T", "");
    	remaningPart = remaningPart.replace("_V", "");
    	remaningPart = remaningPart.replace("_S", "");
    	
    	return remaningPart.isEmpty();
    }
    
    /**
     * Get excel file name wouthout format part...
     * @param excelFileName
     * @return
     */
    private String getFileNameWithoutFormat(String excelFileName) {
    	return excelFileName.substring(0, excelFileName.indexOf(".xl"));
    }

    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#getFileById(java.lang.Long)
     */
    @Override
    public ImportedExcelBean getFileById(Long id) {
        ImportedExcel excel = importedExcelDao.getById(id);
        
        return initBeanByDomain(excel);
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#getFileContent(java.lang.String)
     */
    @Override
    public InputStream getFileContent(String fileName, boolean original) {
    	String path = String.join(File.separator, getFileDirPath(fileName), original ? ORIGIN_FOLDER : REMAINING_FOLDER, original ? fileName : initFileRemainName(fileName));
    	
    	try {    		    		
			return Files.newInputStream(Paths.get(path));
		} catch (final IOException ioex) {
			LOGGER.warn("Problem occured while opening file: {} {}", path, ioex);
		}
		
		return null;
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ImportDataService#importFile(org.primefaces.model.UploadedFile)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void importFile(UploadedFileBean uploadedFileBean, List<String> agentTypes) throws ChronoDataException{
        String fileName = uploadedFileBean.getFileName();
        String originPath = String.join(File.separator, getFileDirPath(fileName), ORIGIN_FOLDER);                
        try {
            Files.createDirectories(Paths.get(originPath));
            
            /*Save file into system...*/
            originPath = String.join(File.separator, originPath, fileName);
            uploadedFileBean.write(originPath);         
        } catch (Exception ex) {
            LOGGER.error("Error while storing file at {} {}", originPath, ex);
            throw new ChronoDataException("Error while storing file " + originPath);
        }
        
        ImportedExcel excel = new ImportedExcel();
        excel.setFileName(fileName);
        excel.setSize(Double.valueOf(uploadedFileBean.getSize()));
        excel.setContentType(uploadedFileBean.getContentType());
        
        importedExcelDao.save(excel);
        
        try {
        	doImport(fileName, originPath, excel, agentTypes);
        } catch(ChronoDataException e) {
        	LOGGER.info("DELETTING UPLOADED FILE DIR FROM SYSTEM, AS THE FILE WAS INVALID...");
        	ChronoUtil.deleteDirectory(getFileDirPath(fileName));
        	
        	throw(e);
        }
    }        
    
    /**
     * Համակարգը նույնականացնում է ներբեռնվող ֆայլի D սյունակում նշված հաշվեհամարը, 
     * ծրագրում առկա հաշվեհամարի հետ և G սյունակում նշված գումարը գումարում է Պարբերական վճարումներ 
     * սյունակի թվին՝ նշելով նաև ներբեռնվող ֆայլի A սյունակում նշված ամսաթիվը:
     * 
     * Parses Excel file and made needed changes in our database...
     * @param uploadedFile
     */
    private void doImport(String fileName, String path, ImportedExcel excel, List<String> agentTypes) throws ChronoDataException {
        try (        		
//        		InputStream is = new FileInputStream(new File(path));
//        		Workbook workbook = StreamingReader.builder()
//                                                .rowCacheSize(10)                       // number of rows to keep in memory (defaults to 10)
//                                                .bufferSize(4096)                       // buffer size to use when reading InputStream to file (defaults to 1024)                                             
//                                                .open(is);   // InputStream or File for XLSX file (required)
        		        		
        		Workbook workbook = WorkbookFactory.create(new File(path));
        ) {  
            List<ImportExcelModel> notMatchedRows = new ArrayList<ImportExcelModel>();
            
            int rowCount = 0;
            int rowsUpdated = 0;
            int rowsSkippedZeroIncome = 0;
            int rowsSkippedNoContract = 0;
            
            Sheet sheet = workbook.getSheetAt(0);                               
            for (Row row : sheet) {
                ++rowCount;                                
                  
                /*we continue parsing if one row was never populated thus null.*/
                if(row == null) {
                    continue;
                }
                
                Cell cellDate = row.getCell(0);
                Cell cellBankAccount = row.getCell(3);
                Cell cellIncome = row.getCell(6);
                
                /*skip header*/
                if(rowCount == 1 && cellDate.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                    continue;
                }
                
                /*skup empty rows (before tha last one), and also the last - total row*/
                if(cellDate == null || cellBankAccount == null || cellIncome == null) {
                	LOGGER.info("SKIPPIN EMPTY ROW (OR THE LAST ROW). ROW NUMBER: " + rowCount + " FILE: " + fileName);
                	
                	continue;
                }
                
                Date dateValue = getCellDateValue(cellDate);
                String bankAccount = getCellStringValue(cellBankAccount);
                Double income = getCellDoubleValue(cellIncome);
                if(dateValue == null || bankAccount == null || income == null) {
                    LOGGER.error("ERROR!!! INVALID EXCEL ROW DATA...");
                    
                    throw new ChronoDataException("Առկա է սխալ տվյալ բեռնվող ֆայլի մեջ։ Տող " + rowCount + ":");
                }
                
                bankAccount = bankAccount.trim();
                
                /*Եթե ներբեռնվող ֆայլի G սյունը հավասար է 0-ի անտեսել այդ տողերը*/
                if(income.doubleValue() == 0) {
                    ++rowsSkippedZeroIncome;
                    
                    continue;
                }
                                
                List<Contract> contracts = bankAccount.length() >= ChronoUtil.getValidBankAccountLength(bankAccount) ? contractDao.getContractsByBank(bankAccount, agentTypes) : null;
                
                /*No contract found by given bank account...*/
                if(CollectionUtils.isEmpty(contracts)) {
                    notMatchedRows.add(initExcelModel(row));                    
                    ++rowsSkippedNoContract;
                    
                    continue;
                }
                
                updateContractsPeriodic(contracts, excel, income, dateValue);                                                
                ++rowsUpdated;
            }
            
            /*when there are rows that didn't match any contract,
             * we create a new excel with those rows...*/
            if(!notMatchedRows.isEmpty()) {
                String remainingPath  = String.join(File.separator, getFileDirPath(fileName), REMAINING_FOLDER);
                
                saveRemainingExcel(workbook, remainingPath, initFileRemainName(fileName), notMatchedRows);
            }
            
            excel.setRows(rowCount);
            excel.setRowsUpdated(rowsUpdated);
            excel.setRowsSkipped(rowsSkippedZeroIncome);
            excel.setRowsNoFound(rowsSkippedNoContract);            
        } catch(IOException | InvalidFormatException e) {
            LOGGER.error("ERROR Creating WORKBOOK to parse Excel via POI Streamin api... ");
        }
    }
    
    /**
     * OLD VERSION: Here we had a requirement to subtract from the Contract with the HIGHEST amount remains...
     * Here we get contracts ordered by amount remains DESC (Highes one is the first element in the list)...
     * 
     * Updates periodic payments of contracts...
     * @param contracts
     * @param excel
     * @param income
     */
//    private void updateContractsPeriodic(List<Contract> contracts, ImportedExcel excel, Double income, Date dateFromExcel) {
//    	
//    	for(int i = 0; i < contracts.size(); i++) {
//    		Contract contract = contracts.get(i);
//    		
//    		/* 
//    		 * 1 case: 
//    		 * if contract is already in Paid or Overpaid status,
//    		 * then no logic is needed, just add periodic amount of the first one (which is same time 
//    		 * with the greatest amounRemains data) and finish...
//    		 * 
//    		 * 2 case: 
//    		 * when amount remains is enough for this income, then again
//    		 * we have to update current contract and finish...
//    		 */
//    		if(contract.getAmountRemains() >= income) {
//    			updateContractPeriodic(contract, excel, income, dateFromExcel);
//    			
//    			return;
//    		}
//    		
//    		/* When next contract also has no status (that means have positive amount remains data),
//    		 * then we have set periodic payment of current contract equals to its amount remains,
//    		 * and the rest part of the income pass to the next coming contract...*/
//    		if((contracts.size() > (i + 1)) && contracts.get(i + 1).getAmountRemains() > 0) {
//    			income = ChronoUtil.subtract(income, contract.getAmountRemains());
//    			updateContractPeriodic(contract, excel, contract.getAmountRemains(), dateFromExcel);    			    			
//    			
//    			continue;
//    		}
//    		
//    		/*if this was the last contract in the list, or the next one has already
//    		 * any status (amount remains data <= 0), then just add all income
//    		 * as a periodic payment for this contract...*/
//    		updateContractPeriodic(contract, excel, income, dateFromExcel);
//    		
//    		return;
//    	}    	    	
//    }
    
    /**
     * NEW VERSION: Հաշվեհամարի կրկնվելու դեպքում , գումարը հանի այն տողից, որի «Մուտք համակարգ»-ի ամսաթիվը ավելի փոքր է (շուտ):
     * Here we get contracts ordered by contract_enter_date...
     * 
     * Updates periodic payments of contracts...
     * @param contracts
     * @param excel
     * @param income
     */
    private void updateContractsPeriodic(List<Contract> contracts, ImportedExcel excel, Double income, Date dateFromExcel) {       
        for(int i = 0; i < contracts.size(); i++) {
            Contract contract = contracts.get(i);
            
            /* 
             * 1 case: 
             * when amount remains is enough for this income, then again
             * we have to update current contract and finish...
             */
            if(contract.getAmountRemains() >= income) {
                updateContractPeriodic(contract, excel, income, dateFromExcel);
                
                return;
            }
            
            /* 2 case:
             * when this is the last contract in the list, then we have to just 
             * to add remaining income to its periodic payment and finish...*/
            if((contracts.size() == (i + 1))) {
                updateContractPeriodic(contract, excel, income, dateFromExcel);
                
                return;
            }
            
            /* 3 case:
             * when contract has positive amount remains, then we have set periodic payment 
             * of current contract equals to its amount remains,
             * and the rest part of the income pass to the next coming contract...*/
            if(contract.getAmountRemains() > 0) {
                income = ChronoUtil.subtract(income, contract.getAmountRemains());
                updateContractPeriodic(contract, excel, contract.getAmountRemains(), dateFromExcel);                                
                
                continue;
            }
        }               
    }
    
    /**
     * Updates periodic payment of given contract and also adds corresponding ExcelContract relationship...
     * @param contract
     * @param excel
     * @param income
     */
    private void updateContractPeriodic(Contract contract, ImportedExcel excel, Double income, Date dateFromExcel) {
        LocalDateTime localDateFromExcel = DateUtil.getLocalDateTimeByDate(dateFromExcel);
        
    	/*Համակարգը նույնականացնում է ներբեռնվող ֆայլի D սյունակում նշված հաշվեհամարը, 
         * ծրագրում առկա հաշվեհամարի հետ և G սյունակում նշված գումարը գումարում է 
         * Պարբերական վճարումներ սյունակի թվին՝ նշելով նաև ներբեռնվող ֆայլի A սյունակում նշված ամսաթիվը:*/
//        contract.setPeriodicPayment(contract.getPeriodicPayment() + income);
        contract.setPeriodicPayment(ChronoUtil.add(contract.getPeriodicPayment(), income));
        contract.initAmountRemainsAndStatus(localDateFromExcel);
        contractDao.save(contract);
        
        /*Save also corresponding data into excel_contract_income table...*/
        ExcelContractIncome exContIncome = new ExcelContractIncome(excel, contract, income, localDateFromExcel);
        excelContractIncomeDao.save(exContIncome);
    }        
    
    /**
     * Initialize remaining file name...
     * @param fileName
     * @return
     */
    private String initFileRemainName(String fileName) {
    	return fileName.endsWith(".xlsx") ? fileName.replace(".xlsx", "_remain.xls") : fileName.replace(".xl", "_remain.xl");
    }
    
    /**
     * Create Excel file with not matched excel rows...
     * @param notMatchedRows
     */
    private void saveRemainingExcel(Workbook wb, String dirPath, String fileName, List<ImportExcelModel> notMatchedRows) {
        if(fileName.endsWith(".xlsx")) {
            fileName = fileName.replace(".xlsx", ".xls");
        }
        
        Workbook workbook = new HSSFWorkbook();     
        
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 256 * 12);
        sheet.setColumnWidth(1, 256 * 20);
        sheet.setColumnWidth(2, 256 * 8);
        sheet.setColumnWidth(3, 256 * 20);
        sheet.setColumnWidth(4, 256 * 20);
        sheet.setColumnWidth(5, 256 * 20);
        sheet.setColumnWidth(6, 256 * 20);
        sheet.setColumnWidth(7, 256 * 25);
        CellStyle headerCellStyle = getHeaderCellStyle(workbook);               
        
        //Prepare Header
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(18);
        
        createCell(workbook, headerRow, 0, "Ամսաթիվ", headerCellStyle);         
        createCell(workbook, headerRow, 1, "Փաստ.N", headerCellStyle);
        createCell(workbook, headerRow, 2, "ԳՏ", headerCellStyle);
        createCell(workbook, headerRow, 3, "Հաշիվ", headerCellStyle);
        createCell(workbook, headerRow, 4, "Նպատակ", headerCellStyle);
        createCell(workbook, headerRow, 5, "Ելք", headerCellStyle);                       
        createCell(workbook, headerRow, 6, "Մուտք", headerCellStyle);
        createCell(workbook, headerRow, 7, "Վճարող/Շահառու", headerCellStyle);       
        //~Prepare Header
    
        //Prepare Content
        CellStyle numericCellStyle = getNumericCellStyle(workbook);
        sheet.setDefaultColumnStyle(5, numericCellStyle);
        sheet.setDefaultColumnStyle(6, numericCellStyle);        
        
        //Align all columns to right (by default numeric columns are aligned to right...)
        CellStyle alignRightCellStyle = getAlignRightCellStyle(workbook);
        sheet.setDefaultColumnStyle(0, alignRightCellStyle);
        sheet.setDefaultColumnStyle(1, alignRightCellStyle);
        sheet.setDefaultColumnStyle(2, alignRightCellStyle);
        sheet.setDefaultColumnStyle(3, alignRightCellStyle);
        sheet.setDefaultColumnStyle(4, alignRightCellStyle);
        sheet.setDefaultColumnStyle(7, alignRightCellStyle);
        
        //Date cell style...
        CellStyle dateCellStyle = getDateCellStyle(workbook);
     
        //Generate content
        Row row = null;         
        for(int i = 0; i < notMatchedRows.size(); i++){
            ImportExcelModel notMatchedRow = notMatchedRows.get(i);
            
            row = sheet.createRow(i + 1);           
            
            createCell(workbook, row, 0, notMatchedRow.getDealDate(), dateCellStyle);
            createCell(workbook, row, 1, notMatchedRow.getDocNumber(), null);
            createCell(workbook, row, 2, notMatchedRow.getGt(), null);
            createCell(workbook, row, 3, notMatchedRow.getAccount(), null);
            createCell(workbook, row, 4, notMatchedRow.getPurpose(), null);                                        
            createCell(workbook, row, 5, notMatchedRow.getOutcome(), null);
            createCell(workbook, row, 6, notMatchedRow.getIncome(), null);
            createCell(workbook, row, 7, notMatchedRow.getPayer(), null);
        }
        
        try {
	        Files.createDirectories(Paths.get(dirPath));
	        try (FileOutputStream outputStream = new FileOutputStream(String.join(File.separator, dirPath, fileName))) {
	            workbook.write(outputStream);
	        } 
        } catch(IOException e) {
            LOGGER.error("ERROR CREATING REMAINING DATA EXCEL FILE...");
        }
    }        
    
    /**
     * Create Cell for given row, by given index, label and style.
     * @param row
     * @param index
     * @param label
     * @param cellStyle
     */
    private void createCell(Workbook wb, Row row, int index, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(index);
        if(cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        
        if(value == null) {
            return;
        }
        
        if(value instanceof String) {
            cell.setCellValue((String)value);
        } else if(value instanceof Double) {
            cell.setCellValue((Double)value);
        } else {
            cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            
            cell.setCellValue((Date)value);
            cell.setCellStyle(cellStyle);
        }                
    }
    
    /**
     * Get cell style which will align data to right.
     * @param workbook
     * @return
     */
    private CellStyle getAlignRightCellStyle(Workbook workbook) {
        CellStyle alignRightCellStyle = workbook.createCellStyle();
        
        alignRightCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        
        return alignRightCellStyle;
    }
    
    /**
     * Get Date cell style...
     * @param workbook
     * @return
     */
    private CellStyle getDateCellStyle(Workbook workbook) {
        CellStyle dateCellStyle = workbook.createCellStyle();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
        
        return dateCellStyle;
    }
    
    /**
     * Get cell style for numeric Cells -- such as Price, Total Price, Amount...
     * @param workbook
     * @return
     */
    private CellStyle getNumericCellStyle(Workbook workbook) {                          
        CellStyle numericCellStyle = workbook.createCellStyle();
        
        DataFormat dataFormat = workbook.createDataFormat();
        numericCellStyle.setDataFormat(dataFormat.getFormat("#,##0.0"));
        
        return numericCellStyle;
    }
    
    /**
     * Get cell style of the header of XLS report.
     * @param workbook
     * @return
     */
    private CellStyle getHeaderCellStyle(Workbook workbook) {                           
        CellStyle headerCellStyle = createBorderedStyle(workbook);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);     

        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        
        return headerCellStyle;
    }
    
    /**
     * Create bordered style :) from http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/ss/examples/BusinessPlan.java
     * @param workbook
     * @return
     */
    private CellStyle createBorderedStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        return style;
    }    
    
    /**
     * Initialize bean list by given domain list...
     * @param importedExcels
     * @return
     */
    private List<ImportedExcelBean> initBeanListByDomainList(List<ImportedExcel> importedExcels) {
        List<ImportedExcelBean> excelBeanList = new ArrayList<ImportedExcelBean>();
        for(ImportedExcel excel : importedExcels) {
            excelBeanList.add(initBeanByDomain(excel));
        }
        
        return excelBeanList;
    }

    /**
     * Initialize bean by given domain...
     * @param excel
     * @return
     */
    private ImportedExcelBean initBeanByDomain(ImportedExcel excel) {
        ImportedExcelBean excelBean = new ImportedExcelBean();
        
        excelBean.setId(excel.getId());
        excelBean.setFileName(excel.getFileName());
        excelBean.setRows(excel.getRows());
        excelBean.setRowsUpdated(excel.getRowsUpdated());
        excelBean.setRowsSkipped(excel.getRowsSkipped());
        excelBean.setRowsNoFound(excel.getRowsNoFound());
        excelBean.setSize(excel.getSize());
        excelBean.setContentType(excel.getContentType());
        excelBean.setCreatedDate(DateUtil.getDateByLocalDateTime(excel.getCreatedDate()));
        
        if(excel.getRowsNoFound() > 0) {
        	excelBean.setFileRemainName(initFileRemainName(excel.getFileName()));
        }
        
        return excelBean;
    }
    
    /**
     * Get date value from cell.
     * @param cell
     * @return
     */
    private Date getCellDateValue(Cell cell) {
        if(cell == null) {
            return null;
        }
        
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {        	
    		return cell.getDateCellValue();        	
        }
        
        return null;
    }
    
    /**
     * Get date value from cell.
     * @param cell
     * @return
     */
    private String getCellStringValue(Cell cell) {
        if(cell == null) {
            return null;
        }
        
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {        	
        	String doubleString = String.valueOf(cell.getNumericCellValue());
        	
        	return ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(doubleString);
        }
        
        return cell.getStringCellValue();
    }
    
    /**
     * Get date value from cell.
     * @param cell
     * @return
     */
    private Double getCellDoubleValue(Cell cell) {
        if(cell == null) {
            return null;
        }
        
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {            
            return ChronoUtil.roundDouble(cell.getNumericCellValue(), 3);            
        }
        
        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String doubleString = cell.getStringCellValue();            
            
            try {
                return ChronoUtil.roundDouble(Double.parseDouble(doubleString.replaceAll(",", "")), 3);
            } catch(NumberFormatException e) {
                return null;
            }
        }
        
        return null;
    }
    
    private ImportExcelModel initExcelModel(Row row) {        
        Cell cellDate = row.getCell(0);
        Cell cellDocNumber = row.getCell(1);
        Cell cellGt = row.getCell(2);
        Cell cellBankAccount = row.getCell(3);
        Cell cellPurpose = row.getCell(4);
        Cell cellOutcome = row.getCell(5);
        Cell cellIncome = row.getCell(6);
        Cell cellPayer = row.getCell(7);
        
        ImportExcelModel excelModel = new ImportExcelModel();
        
        excelModel.setDealDate(getCellDateValue(cellDate));
        excelModel.setDocNumber(getCellStringValue(cellDocNumber));
        excelModel.setGt(getCellStringValue(cellGt));
        excelModel.setAccount(getCellStringValue(cellBankAccount));
        excelModel.setPurpose(getCellStringValue(cellPurpose));
        excelModel.setOutcome(getCellDoubleValue(cellOutcome));
        excelModel.setIncome(getCellDoubleValue(cellIncome));
        excelModel.setPayer(getCellStringValue(cellPayer));
        
        return excelModel;
    }

    /**
     * Get directory path of the given file...
     * @param fileName
     * @return
     */
    private String getFileDirPath(String fileName) {
    	return String.join(File.separator, storageHome, CONTRACT_EXCEL_FOLDER, getFileDirName(fileName));
    }
    
    /**
     * Get directory name for the given file name...
     * @param fileName
     * @return
     */
    private String getFileDirName(String fileName) {
    	return fileName.substring(0, fileName.lastIndexOf("."));
    }    
    
    /**
     * Will represent row data from imported excel..
     * Will use this class for creating new Excel file with unmatched contracts inside...
     * @author HARUT
     */
    public class ImportExcelModel {
        
        private Date dealDate;
        private String docNumber;
        private String gt;
        private String account;
        private String purpose;
        private Double outcome;
        private Double income;
        private String payer;
        
        /**
         * Default constructor...
         */
        public ImportExcelModel() {            
        }
        
        /**
         * @return the dealDate
         */
        public Date getDealDate() {
            return dealDate;
        }
        
        /**
         * @param dealDate the dealDate to set
         */
        public void setDealDate(Date dealDate) {
            this.dealDate = dealDate;
        }
        
        /**
         * @return the docNumber
         */
        public String getDocNumber() {
            return docNumber;
        }
        
        /**
         * @param docNumber the docNumber to set
         */
        public void setDocNumber(String docNumber) {
            this.docNumber = docNumber;
        }
        
        /**
         * @return the gt
         */
        public String getGt() {
            return gt;
        }
        
        /**
         * @param gt the gt to set
         */
        public void setGt(String gt) {
            this.gt = gt;
        }
        
        /**
         * @return the account
         */
        public String getAccount() {
            return account;
        }
        
        /**
         * @param account the account to set
         */
        public void setAccount(String account) {
            this.account = account;
        }                
        
        /**
         * @return the purpose
         */
        public String getPurpose() {
            return purpose;
        }

        /**
         * @param purpose the purpose to set
         */
        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        /**
         * @return the outcome
         */
        public Double getOutcome() {
            return outcome;
        }
        
        /**
         * @param outcome the outcome to set
         */
        public void setOutcome(Double outcome) {
            this.outcome = outcome;
        }
        
        /**
         * @return the income
         */
        public Double getIncome() {
            return income;
        }
        
        /**
         * @param income the income to set
         */
        public void setIncome(Double income) {
            this.income = income;
        }
        
        /**
         * @return the payer
         */
        public String getPayer() {
            return payer;
        }
        
        /**
         * @param payer the payer to set
         */
        public void setPayer(String payer) {
            this.payer = payer;
        }                
    }
}
