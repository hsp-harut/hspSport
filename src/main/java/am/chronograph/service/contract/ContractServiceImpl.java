package am.chronograph.service.contract;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.contract.ContractDao;
import am.chronograph.dao.contract.ContractSearchCriteria;
import am.chronograph.dao.contract.ExcelContractIncomeDao;
import am.chronograph.domain.contract.Contract;
import am.chronograph.domain.contract.ExcelContractIncome;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultBean;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.contract.ContractSearchResultBean;
import am.chronograph.web.bean.contract.ExcelContractBean;
import am.chronograph.web.util.Constants.ContractStatus;
import am.chronograph.web.util.DateUtil;

/**
 * Implements ContractService interface...
 * 
 * @author HARUT
 */
@Service
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {
    
    private static final int OVERDUE_DAYS_NUMBER = 35;
    
    @Autowired
    private ContractDao contractDao;        
    
    @Autowired
    private ExcelContractIncomeDao excelContractIncomeDao;      
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#createUpdateContract(am.chronograph.web.bean.contract.ContractBean)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Long createUpdateContract(ContractBean contractBean, boolean newCopyCreated) {
        Contract contract = contractDao.save(initContractDomainByBean(contractBean));
        if(newCopyCreated) {
            contractDao.setCopyContractsByBank(contractBean.getBankAccountNumber());
        }
        
        /* In case when contract has been updated and its bank account has been changed.
         * So we have to check maybe the previous ban account was cause of other contracts
         * to get a copy status. So we need to check, and un-copy them if such exists*/
        if(contractBean.getId() != null && contractBean.isBankAccountChanged()) {
            
            /* id can be null in the method, but I am just setting to not have 
             * invalid case because of not trimming bank account...*/
            List<Contract> contractsByBankAccount = contractDao.getContractsByBankNotById(contractBean.getBankAccountNumberOld(), 
                                                                                          contractBean.getId());
            
            /*if there was only one contract - that means that it became copy because
             * of this new updated contract. So we have to make it back to normal status (no copy)...*/
            if(contractsByBankAccount.size() == 1) {
                Contract oldCopyContract = contractsByBankAccount.get(0);
                oldCopyContract.setCopy(false);
                
                contractDao.save(oldCopyContract);
            }
        }
        
        return contract.getId();
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#deleteContract(java.lang.Long)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteContract(Long contractId) {
        contractDao.delete(contractDao.getById(contractId));
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#updateOverdueContratcs()
     */
    @Scheduled(cron = "0 0 12 1/1 * ?")
//  @Scheduled(cron = "0 0/1 * 1/1 * ?")   // every minute - for testing...    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateContractsStatus() {
        
        /*Կարգավիճակ սյունակում նշել <մարված> այն տողերը որոնց 
         * Մնացորդ Սյունակը հավասար է 0-ի և նշել կանաչ գույնով*/
        contractDao.checkAndUpdateStatusToPaid();
        
        /*Artur Apyan message: geravchar - erb poxancvox gumar@ aveli shat er linum qan mnacord@*/
        contractDao.checkAndUpdateStatusToOverPaid();
        
        /*
         * OLD: 
         * կարգավիճակ սյունակում նշել <ժամկետանց>  բոլոր այն տողերը որոնց պարբերական վճարումներ 
         * սյունակում թիվ չի գումարվում 35 օր պարբերականությամբ առաջին անգամը հիմք ընդունելով 
         * պայմանագրի սկիզբ սյունակը եթե տվյալ տողի Մնացորդ սյունակը մեծ է 0ից և նշել դեղին գույնով
         * 
         * NEW:
         * Har jan 'kargavichak' syun@ darnuma 'jamketanc' ete 35 or sharunak iran parberakan vchar chi ekel. Ed pah@ ashxatuma, bayc ete asenq 40rd or@ vren poxa nstum, 'jamketanc'-@ chi veranum.
         * Aysinqn petqa vor ete jamketanci vra poxa nstum , jamketanc@ verana, u et 35 or hashvark@ sksi verjin poxancumic hashvac
         * Karaca bacatrem ? 🙂 
         * 
         * Harut: ete excelov poxa nstum ed amsativ@ verjin hashvarki vorn es hamarum?
         * erb exceln es import arel? te exceli meji grac amsativ@?
         * 
         * Artur: Har chisht@ iharke exceli meji amsativna , bayc ete tenc barda, 
         * principi karas nenc anes erb mutq es arel, vortev amen or qaxvacq en qshum
         * 
         * 
         * Get contracts which have started later than 35 days (the ones which have
         * start date and current time difference less than 35 days - can not be considered as Overdue).*/
        List<Contract> contracts = contractDao.getContractsStartedLaterThanDaysNumber(OVERDUE_DAYS_NUMBER);
        for(Contract contract : contracts) {
            
            /* Dont need this anymore, as in the contrct table we now store periodic payment last updated date,
             * and contracts above are those which must be overdue...*/
//          List<ExcelContractIncome> incomes = excelContractIncomeDao.getByContractImportedWithinDaysNumber(contract.getId(), OVERDUE_DAYS_NUMBER);
            
            /*last file uploaded later than 35 days - we need to set Overdue status to contract*/
//          if(incomes.isEmpty()) {                             
                contract.setStatus(ContractStatus.Overdue);
                contractDao.save(contract);
//          }
        }
        
//      int i = 0;
//      i++;
        //SELECT id, contract_start_date, contract_end_date FROM `contract` WHERE `status` is null AND datediff(NOW(), `contract_start_date`) >= 35
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.SearchBeanAware#search(am.chronograph.search.SearchCriteria)
     */
    @Override
    public SearchResultBean<ContractBean> search(SearchCriteria<Contract> criteria) {
        SearchResult<Contract> searchResult = contractDao.search(criteria);
        
        ContractSearchResultBean searchResultBean = new ContractSearchResultBean();
        searchResultBean.setCount(searchResult.getTotalResultCount());
        searchResultBean.setResult(initContractBeanListByDomainList(searchResult.getResult()));
        
        Object[] totals = contractDao.getTotals((ContractSearchCriteria)criteria);                
        searchResultBean.setTotalOfAmountTotal((Double)totals[0]);
        searchResultBean.setTotalOfPeriodicPayment((Double)totals[1]);
        
        searchResultBean.setTotalOfAmountOld((Double)totals[2]);
        searchResultBean.setTotalOfAmountNew((Double)totals[3]);
        searchResultBean.setTotalOfAmountRelax((Double)totals[4]);
        searchResultBean.setTotalOfAmountPersonal((Double)totals[5]);
        searchResultBean.setTotalOfAmountRemains((Double)totals[6]);
        
        searchResultBean.setReRenderJsFunction("refreshFooter()");
        
        return searchResultBean;
    }

    /*
     * (non-Javadoc)
     * @see am.chronograph.service.SearchBeanAware#getEmptyCriteria()
     */
    @Override
    public SearchCriteria<Contract> getEmptyCriteria() {
        return new ContractSearchCriteria();
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#getAllContracts()
     */
    public List<ContractBean> getAllContracts() {
        List<Contract> contracts = contractDao.getAll();
        
        return initContractBeanListByDomainList(contracts);
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#existContractByNumberNotById(java.lang.String, java.lang.Long)
     */
    public boolean existContractByNumberNotById(String contractNumber, Long id) {
        Contract contract = contractDao.getContractByNumberNotById(contractNumber, id);
        if(contract == null) {
            return false;
        }
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#existContractByBankNotById(java.lang.String, java.lang.Long)
     */
    public boolean existContractByBankNotById(String bankAccount, Long id) {
        List<Contract> contracts = contractDao.getContractsByBankNotById(bankAccount, id);
        if(contracts.isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#getContractById(java.lang.Long)
     */
    public ContractBean getContractById(Long contractId) {
        Contract contract = contractDao.getById(contractId);
        
        return initContractBeanByDomain(contract);
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.service.contract.ContractService#getContractHistory(java.lang.Long)
     */
    public List<ExcelContractBean> getContractHistory(Long contractId) {
        List<ExcelContractIncome> excelContracts = excelContractIncomeDao.getByContractId(contractId);
        
        return initExcelContractBeanListByDomainList(excelContracts);
    }
    
    /**
     * Initialize ExcelContractBean list by given domain list...
     * @param excelContracts
     * @return
     */
    private List<ExcelContractBean> initExcelContractBeanListByDomainList(List<ExcelContractIncome> excelContracts) {
        List<ExcelContractBean> excelContractBeanList = new ArrayList<ExcelContractBean>();
        for(ExcelContractIncome excelContract : excelContracts) {
            excelContractBeanList.add(initExcelContractBeanByDomain(excelContract));
        }
        
        return excelContractBeanList;
    }
    
    /**
     * Initialize ExcelContract Bean by given domain object...
     * @param excelContract
     * @return
     */
    private ExcelContractBean initExcelContractBeanByDomain(ExcelContractIncome excelContract) {
        ExcelContractBean excelContractBean = new ExcelContractBean();
        
        excelContractBean.setExcelName(excelContract.getExcel().getFileName());
        excelContractBean.setIncome(excelContract.getIncome());
        excelContractBean.setDateInExcel(DateUtil.getDateByLocalDateTime(excelContract.getDateInExcel()));
        excelContractBean.setCreatedDate(DateUtil.getDateByLocalDateTime(excelContract.getCreatedDate()));
        
        return excelContractBean;
    }
    
    /**
     * Initialize contract bean list by domain list... 
     * @param contracts
     * @return
     */
    private List<ContractBean> initContractBeanListByDomainList(List<Contract> contracts) {
        List<ContractBean> contractBeanList = new ArrayList<ContractBean>();
        for(Contract contract : contracts) {            
            contractBeanList.add(initContractBeanByDomain(contract));                       
        }
        
        return contractBeanList;
    }
    
    /**
     * Initialize contract bean by given domain...
     * @param contract
     * @return
     */
    private ContractBean initContractBeanByDomain(Contract contract) {
        ContractBean contractBean = new ContractBean();
        
        contractBean.setId(contract.getId());
        contractBean.setContractNumber(contract.getContractNumber());
        contractBean.setBankAccountNumber(contract.getBankAccountNumber());
        contractBean.setBankAccountNumberOld(contract.getBankAccountNumber());
        
        contractBean.setFirstName(contract.getFirstName());
        contractBean.setLastName(contract.getLastName());
        contractBean.setMiddleName(contract.getMiddleName());
        
        contractBean.setAddress(contract.getAddress());
        contractBean.setWorkingPlace(contract.getWorkingPlace());
        contractBean.setHotel(contract.getHotel());
        contractBean.setNightsNumber(contract.getNightsNumber());
        contractBean.setAgent(contract.getAgent());
        
        contractBean.setAmountOld(contract.getAmountOld().toString());
        contractBean.setAmountOldDouble(contract.getAmountOld());
        
        contractBean.setAmountNew(contract.getAmountNew().toString());
        contractBean.setAmountNewDouble(contract.getAmountNew());
        
        contractBean.setAmountRelax(contract.getAmountRelax().toString());
        contractBean.setAmountRelaxDouble(contract.getAmountRelax());
        
        contractBean.setAmountPersonal(contract.getAmountPersonal().toString());
        contractBean.setAmountPersonalDouble(contract.getAmountPersonal());
        
        contractBean.setAmountTotal(contract.getAmountTotal().toString());
        contractBean.setAmountTotalDouble(contract.getAmountTotal());
        
        contractBean.setPeriodicPayment(contract.getPeriodicPayment().toString());
        contractBean.setPeriodicPaymentDouble(contract.getPeriodicPayment());
        contractBean.setPeriodicPaymentOld(contract.getPeriodicPayment());
        
        contractBean.setAmountRemains(contract.getAmountRemains().toString());
        contractBean.setAmountRemainsDouble(contract.getAmountRemains());
        
        contractBean.setCopy(contract.isCopy());
        contractBean.setStatus(contract.getStatus());
        
        contractBean.setEnterDate(DateUtil.getDateByLocalDateTime(contract.getEnterDate()));
        contractBean.setStartDate(DateUtil.getDateByLocalDateTime(contract.getStartDate()));
        contractBean.setEndDate(DateUtil.getDateByLocalDateTime(contract.getEndDate()));        
        contractBean.setPeriodicLastUpdatedAt(DateUtil.getDateByLocalDateTime(contract.getPeriodicLastUpdatedAt()));
        
        return contractBean;
    }
    
    /**
     * Initialize Contract dsomain by bean...
     * @param contractBean
     * @return
     */
    private Contract initContractDomainByBean(ContractBean contractBean) {      
        Contract contract = (contractBean.getId() != null) ? contractDao.getById(contractBean.getId()) : new Contract();
        
        contract.setId(contractBean.getId());
        contract.setContractNumber(contractBean.getContractNumber());
        contract.setBankAccountNumber(contractBean.getBankAccountNumber());
        
        contract.setFirstName(contractBean.getFirstName());
        contract.setLastName(contractBean.getLastName());
        contract.setMiddleName(contractBean.getMiddleName());
        
        contract.setAddress(contractBean.getAddress());
        contract.setWorkingPlace(contractBean.getWorkingPlace());
        contract.setHotel(contractBean.getHotel());
        contract.setNightsNumber(contractBean.getNightsNumber());
        contract.setAgent(contractBean.getAgent());
        
        contract.setAmountOld(Double.parseDouble(contractBean.getAmountOld()));
        contract.setAmountNew(Double.parseDouble(contractBean.getAmountNew()));
        contract.setAmountRelax(Double.parseDouble(contractBean.getAmountRelax()));
        contract.setAmountPersonal(Double.parseDouble(contractBean.getAmountPersonal()));
        contract.setAmountTotal(Double.parseDouble(contractBean.getAmountTotal()));
        
        contract.setEnterDate(DateUtil.getLocalDateTimeByDate(contractBean.getEnterDate()));
        contract.setStartDate(DateUtil.getLocalDateTimeByDate(contractBean.getStartDate()));
        contract.setEndDate(DateUtil.getLocalDateTimeByDate(contractBean.getEndDate()));
        
        contract.setPeriodicPayment(ChronoUtil.getDoubleOrZeroByString(contractBean.getPeriodicPayment()));
        contract.initAmountRemainsAndStatus(contractBean.isPeriodicPaymentChanged() ? LocalDateTime.now() : null);
        
        contract.setCopy(contractBean.isCopy());                        
        
        return contract;
    }      
}
