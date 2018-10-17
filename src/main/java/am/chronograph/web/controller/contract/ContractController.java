package am.chronograph.web.controller.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;

import am.chronograph.domain.contract.Contract;
import am.chronograph.service.contract.ContractService;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.contract.ExcelContractBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.model.SearchAwareBeanLazyModel;

/**
 * Controller class will handle actions from contractManagement.xhtml page...
 * 
 * @author HARUT
 */
@Named("contractController")
@ViewScoped
public class ContractController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -5789665108121498023L;

	
	
	
//	private static final Map<String, Integer> BANK_ACCOUNT_LENGTH_MAP = new HashMap<String, Integer>();
//	static{
//	    BANK_ACCOUNT_LENGTH_MAP.put("115", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("163", 12);
//	    BANK_ACCOUNT_LENGTH_MAP.put("118", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("193", 16);
//	    BANK_ACCOUNT_LENGTH_MAP.put("151", 16);
//	    BANK_ACCOUNT_LENGTH_MAP.put("241", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("223", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("247", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("160", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("161", 14);
//	    BANK_ACCOUNT_LENGTH_MAP.put("205", 12);	          
//    }
	
	private static final int MIN_BANK_ACCOUNT_NUMBER_LENGTH = 12;
	
	@Inject
	@Spring
	private transient ContractService contractService;
	
	/*show hide columns filtering...*/
	private boolean filterable;
	
	private ContractBean contractBean;
	private LazyDataModel<ContractBean> contractsModel;
	
	private String contractNumberForHistory;
	private List<ExcelContractBean> excelContractImports;
	
	/*dynamically show hide columns of p:dataTable*/
	private List<Boolean> columnsVisibility;
	
	private boolean existsWithSameBankAccount;
	
	private static final List<String> hotels = new ArrayList<String>();	
	static {
		hotels.add("Գեղարքունիքի Մարզ");
		hotels.add("Տավուշի Մարզ");
		hotels.add("Արմավիրի Մարզ");
		hotels.add("Արագածոտնու Մարզ");
		hotels.add("Սյունիքի Մարզ");
		hotels.add("Երևան");
		hotels.add("ԼՂՀ");
		hotels.add("Լոռու Մարզ");
		hotels.add("Արարատի Մարզ");
		hotels.add("Կոտայքի Մարզ");
		hotels.add("Շիրակի Մարզ");
		hotels.add("Վայոց Ձորի Մարզ");
	}
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();			
		
		/*for(int i = 0; i < 100; i++) {
		    ContractBean contractBean= new ContractBean();
		    		    
	        contractBean.setContractNumber("ContractNumber" + i);
	        contractBean.setBankAccountNumber("BankAccount" + i);
	        
	        contractBean.setFirstName("Հարություն" + i);
	        contractBean.setLastName("Սարգսյան" + i);
	        contractBean.setMiddleName("Ստեփան" + i);
	        
	        contractBean.setAddress("" + i);
	        contractBean.setWorkingPlace("" + i);
	        contractBean.setHotel("" + i);
	        contractBean.setNightsNumber(i);
	        contractBean.setAgent("" + i);
	        
	        contractBean.setAmountOld(i + 1d + "");
	        contractBean.setAmountNew(i + 2d + "");
	        contractBean.setAmountRelax(i + 3d + "");
	        contractBean.setAmountPersonal(i + 4d + "");
	        contractBean.setAmountTotal(4 * i + 10d + "");
	        
	        contractBean.setPeriodicPayment(i + 100d + "");
	        contractBean.setAmountRemains(i + 50d + "");
	        	        
	        if(i % 3 == 0) {
	        	contractBean.setCopy(true);
	        	contractBean.setStatus(ContractStatus.Overdue);
	        } else if(i % 4 == 0) {
	        	contractBean.setStatus(ContractStatus.Paid);
	        } else if(i % 5 == 0) {
	        	contractBean.setStatus(ContractStatus.Overpaid);
	        }
	        	        
	        
	        contractBean.setStartDate(DateUtil.getDateBeforeAfterDays(new Date(), i, true));
	        contractBean.setEndDate(DateUtil.getDateBeforeAfterDays(new Date(), i, false));	        
		    
		    contractService.createUpdateContract(contractBean, false);
		}*/
		
		contractBean = new ContractBean();
		
		contractsModel = new SearchAwareBeanLazyModel<ContractBean, Contract, ContractService>(contractService);
		columnsVisibility = Arrays.asList(true, true, true, false, true, true, false, true, false, true, false,
										  false, false, false, true, true, true, true, false, false, false,
										  true);
	}
	
	/**
	 * Listener to show hide columns via p:columnToggler
	 * @param e
	 */
	public void onToggle(ToggleEvent e) {
		int index = (Integer) e.getData();
		boolean visibility = e.getVisibility() == Visibility.VISIBLE; 
		
		columnsVisibility.set(index, visibility);
    }
	
	/**
	 * ActionLIstener method called when user clicks on "New contract" button - to navigate to corresponding UI...
	 */
	public void onScrollToCreateContract() {
	    contractBean = new ContractBean();
	    
		scrollTo("contractForm:contractCreatePanel");
	}
	
	/**
     * Method called when user clicks on any row in contracts table,
     * to edit selected exam...
     */
    public void onEditContract(ContractBean selectedContractBean) {
        contractBean = new ContractBean(selectedContractBean);
        contractBean.removeLastTwoDoubleFromDoubleStrings();
        
        scrollTo("contractForm:contractCreatePanel");
    }
    
    /**
     * Method called when user clicks on any row in contracts table,
     * to edit selected exam...
     */
    public void onHistory(ContractBean selectedContractBean) {
    	contractNumberForHistory = selectedContractBean.getContractNumber();
    	excelContractImports = contractService.getContractHistory(selectedContractBean.getId());
    }    
    
    /**
     * ActionListener method called when 'Create' or 'Update' clicked - to create new/update process...
     */
    public void onCreateUpdateContract() {
        if(!isValidContract()) {
            return;
        }
        
        /* anyway, lets once more calculate total before saving...*/
        contractBean.calulateTotalAmount();
        
        contractService.createUpdateContract(contractBean, false);
        
        contractBean = new ContractBean();        
        
        addInfoMessage("contractSuccessSave");
    }
    
    /**
     * ActionListener method called when 'Ok' is clicked from the confirmation box, 
     * when trying to create/update Contract with already existing bank account 'Create' or 'Update' clicked - to create new/update process...
     */
    public void onCreateUpdateConfirmed() {
        contractBean.setCopy(true);
        
        /* anyway, lets once more calculate total before saving...*/
        contractBean.calulateTotalAmount();
        
        contractService.createUpdateContract(contractBean, true);
        
        contractBean = new ContractBean();        
        
        addInfoMessage("contractSuccessSave");
    }
    
    /**
     * Listener method called when any of amount fields is changed...
     */
    public void onAmountChanged() {       
        contractBean.calulateTotalAmount();
    }
    
    /**
     * Action method will redirect to importData.xhtml page...
     * @return
     */
    public String onOpenUploadPage() {
        return "importData?faces-redirect=true";
    }
    
    /**
     * Find hotels from the list...
     * @param query
     * @return
     */
    public List<String> onCompleteHotels(String query) {        
        List<String> filteredHotels = new ArrayList<String>();         
        for (String hotel : hotels) {            
            if(hotel.toLowerCase().startsWith(query.toLowerCase())) {
                filteredHotels.add(hotel);
            }
        }
         
        return filteredHotels;
    }
    
    /**
     * Change number value cells to Number type (as they exported by default as text)... 
     * @param document
     */
    public void postProcessXLS(Object document) {
        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);              
        for (Row row : sheet) {
            if(row == null) {
                continue;
            }
            
            //Old Amount
            setNumberTypeToCell(row.getCell(10));
            
            //New Amount
            setNumberTypeToCell(row.getCell(11));
            
            //Relax Amount
            setNumberTypeToCell(row.getCell(12));
            
            //Personal Amount
            setNumberTypeToCell(row.getCell(13));
            
            //Total Amount
            setNumberTypeToCell(row.getCell(14));
            
            //Periodic Amount
            setNumberTypeToCell(row.getCell(15));
            
            //Remaining Amount
            setNumberTypeToCell(row.getCell(16));
        }                 
    }
    
    /**
     * Set NUmber type to cell
     * @param cell
     */
    private void setNumberTypeToCell(Cell cell) {
        Double doubleValue = getCellDoubleValue(cell);
        if(doubleValue == null) {
            return;
        }
        
        //this has to be done to temporarily blank out the cell value
        //because setting the type to numeric directly will cause 
        //an IllegalStateException because POI stupidly thinks
        //the cell is text because it was exported as such by PF...
        cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        
        if(doubleValue != null) {
            cell.setCellValue(doubleValue);
        }
    }
    
    /**
     * Get date value from cell.
     * @param cell
     * @return
     */
    private Double getCellDoubleValue(Cell cell) {       
        String doubleString = cell.getStringCellValue();            
        
        try {
            return Double.parseDouble(doubleString.replaceAll(",", ""));
        } catch(NumberFormatException e) {
            return null;
        }       
    }
    
    /**
     * Method for Person validation -- for inserting/updating
     * corresponding data into database...
     * @return
     */
    private boolean isValidContract() {
        existsWithSameBankAccount = false;
        boolean noError = true;
        
        if(StringUtils.isBlank(contractBean.getContractNumber())) {
            addErrorMessage("contractForm:contractNumber", "contractValidationMandatory");
            
            noError = false;
        } else if (contractService.existContractByNumberNotById(contractBean.getContractNumber(), contractBean.getId())) {
            addErrorMessage("contractForm:contractNumber", "contractValidationUnique");
            
            noError = false;
        }
        
        if(contractBean.getEnterDate() == null) {
            addErrorMessage("contractForm:enterDate", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(contractBean.getStartDate() == null) {
            addErrorMessage("contractForm:startDate", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(contractBean.getEndDate() == null) {
            addErrorMessage("contractForm:endDate", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(StringUtils.isBlank(contractBean.getFirstName())) {
            addErrorMessage("contractForm:firstName", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(StringUtils.isBlank(contractBean.getLastName())) {
            addErrorMessage("contractForm:lastName", "contractValidationMandatory");
            
            noError = false;
        }
        
        /*Հայրանուն -- *-ը հանել*/
//        if(StringUtils.isBlank(contractBean.getMiddleName())) {
//            addErrorMessage("contractForm:middleName", "contractValidationMandatory");
//            
//            noError = false;
//        }
        
        /*Հասցե -- *-ը դնել*/
        if(StringUtils.isBlank(contractBean.getAddress())) {
            addErrorMessage("contractForm:address", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(StringUtils.isBlank(contractBean.getWorkingPlace())) {
            addErrorMessage("contractForm:workingPlace", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(ChronoUtil.getDoubleByString(contractBean.getAmountOld()) == null) {
            addErrorMessage("contractForm:amountOld", "contractValidationDouble");
            
            noError = false;
        }
        
        if(ChronoUtil.getDoubleByString(contractBean.getAmountNew()) == null) {
            addErrorMessage("contractForm:amountNew", "contractValidationDouble");
            
            noError = false;
        }
        
        if(ChronoUtil.getDoubleByString(contractBean.getAmountRelax()) == null) {
            addErrorMessage("contractForm:amountRelax", "contractValidationDouble");
            
            noError = false;
        }
        
        if(ChronoUtil.getDoubleByString(contractBean.getAmountPersonal()) == null) {
            addErrorMessage("contractForm:amountPersonal", "contractValidationDouble");
            
            noError = false;
        }
        
//        if(contractBean.getId() != null && ChronoUtil.getDoubleByString(contractBean.getPeriodicPayment()) == null) {
//            addErrorMessage("contractForm:periodicPayment", "contractValidationDouble");
//            
//            noError = false;
//        }
        
        if(contractBean.getId() != null && ChronoUtil.getDoubleByString(contractBean.getAmountRemains()) == null) {
            addErrorMessage("contractForm:amountRemains", "contractValidationDouble");
            
            noError = false;
        }
        
        if(!isValidBankAccount()) {            
            noError = false;
        }
        
        if(contractBean.getNightsNumber() == null) {
            addErrorMessage("contractForm:nightsNumber", "contractValidationMandatory");
            
            noError = false;
        }
        
        if(StringUtils.isBlank(contractBean.getAgent())) {
            addErrorMessage("contractForm:agent", "contractValidationMandatory");
            
            noError = false;
        }
        
        return noError;
    }
    
    /**
     * Validates bank account...    
     * @return
     */
    private boolean isValidBankAccount() {
        String accountNumber = contractBean.getBankAccountNumber();        
        if(StringUtils.isBlank(accountNumber)) {
            addErrorMessage("contractForm:bankAccountNumber", "contractValidationMandatory");
            
            return false;
        } 
        
        if(accountNumber.length() < MIN_BANK_ACCOUNT_NUMBER_LENGTH) {
            addErrorMessage("contractForm:bankAccountNumber", "contractValidationBankInvalid");
            
            return false;
        }
        
        /*String first3chars = accountNumber.substring(0, 3);
        if(!BANK_ACCOUNT_LENGTH_MAP.containsKey(first3chars)) {
            addErrorMessage("contractForm:bankAccountNumber", "contractValidationBankInvalid");
            
            return false;
        }
        
        if(accountNumber.length() != BANK_ACCOUNT_LENGTH_MAP.get(first3chars)) {
            addErrorMessage("contractForm:bankAccountNumber", "contractValidationBankInvalidLength", first3chars, BANK_ACCOUNT_LENGTH_MAP.get(first3chars));
            
            return false;
        }*/
        
        /*if copy contract is updated, and its bank account is changed*/
        if(contractBean.isCopy() && contractBean.isBankAccountChanged()) {
        	contractBean.setCopy(false);
        }
        
        if(!contractBean.isCopy() && contractService.existContractByBankNotById(accountNumber, contractBean.getId())) {
            existsWithSameBankAccount = true;
            
            return false;
        }
        
        return true;
    }
    
    /**
	 * Method called when 'Cancel' button clicked.
	 */
	public void onCancel() {
		contractBean = new ContractBean();
		
		scrollTo("contractForm:contractListPanel");
	}
    
    /**
     * ActionListener method called when Delete icon is clicked to delete selected ContractBean...
     * @param selectedContractBean
     */
    public void onRemoveContract(ContractBean selectedContractBean) {
        contractService.deleteContract(selectedContractBean.getId());
        
        if(selectedContractBean.getId().equals(contractBean.getId())) {
            contractBean = new ContractBean();
        }
    }
    
    /**
     * ActionListener method called when user enables/disables data table filtering option...
     */
    public void onShowHideFilter() {
		filterable = !filterable;
	}

	/**
	 * @return the filterable
	 */
	public boolean isFilterable() {
		return filterable;
	}

	/**
	 * @param filterable the filterable to set
	 */
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	/**
     * @return the contractBean
     */
    public ContractBean getContractBean() {
        return contractBean;
    }

    /**
     * @param contractBean the contractBean to set
     */
    public void setContractBean(ContractBean contractBean) {
        this.contractBean = contractBean;
    }    

	/**
     * @return the contractsModel
     */
    public LazyDataModel<ContractBean> getContractsModel() {
        return contractsModel;
    }

    /**
     * @param contractsModel the contractsModel to set
     */
    public void setContractsModel(LazyDataModel<ContractBean> contractsModel) {
        this.contractsModel = contractsModel;
    }          
    
    /**
	 * @return the contractNumberForHistory
	 */
	public String getContractNumberForHistory() {
		return contractNumberForHistory;
	}

	/**
	 * @param contractNumberForHistory the contractNumberForHistory to set
	 */
	public void setContractNumberForHistory(String contractNumberForHistory) {
		this.contractNumberForHistory = contractNumberForHistory;
	}

	/**
	 * @return the excelContractImports
	 */
	public List<ExcelContractBean> getExcelContractImports() {
		return excelContractImports;
	}

	/**
	 * @param excelContractImports the excelContractImports to set
	 */
	public void setExcelContractImports(List<ExcelContractBean> excelContractImports) {
		this.excelContractImports = excelContractImports;
	}

	/**
	 * @return the columnsVisibility
	 */
	public List<Boolean> getColumnsVisibility() {
		return columnsVisibility;
	}

	/**
	 * @param columnsVisibility the columnsVisibility to set
	 */
	public void setColumnsVisibility(List<Boolean> columnsVisibility) {
		this.columnsVisibility = columnsVisibility;
	}

    /**
     * @return the existsWithSameBankAccount
     */
    public boolean isExistsWithSameBankAccount() {
        return existsWithSameBankAccount;
    }

    /**
     * @param existsWithSameBankAccount the existsWithSameBankAccount to set
     */
    public void setExistsWithSameBankAccount(boolean existsWithSameBankAccount) {
        this.existsWithSameBankAccount = existsWithSameBankAccount;
    } 
}
