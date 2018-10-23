package am.chronograph.web.controller.tournament;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import am.chronograph.service.tournament.TournamentService;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.tournament.TournamentBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from tournament.xhtml page...
 * 
 * @author davitpetrosyan
 *
 */
@Named("tournamentController")
@ViewScoped
public class TournamentController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -7097416932783060327L;

	@Inject
	@Spring
	private transient TournamentService tournamentService;

	private TournamentBean tournamentBean;
	private List<TournamentBean> tournaments;

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();

		tournamentBean = new TournamentBean();

		tournaments = tournamentService.getAll();
		
	}

	/**
	 * ActionListener method called when 'Create' or 'Update' clicked - to create
	 * new/update process...
	 */
	public void onCreateTournament() {
		if (!isValidTournament()) {
			return;
		}

		tournamentService.create(tournamentBean);
		tournaments = tournamentService.getAll();

		tournamentBean = new TournamentBean();

		addInfoMessage("tournamentSuccessSave");
	}

	/**
	 * ActionListener method called when Delete icon is clicked to delete selected
	 * ContractBean...
	 * 
	 * @param selectedContractBean
	 */
	public void onRemoveTournament(TournamentBean selectedTournamentBean) {
		tournamentService.delete(selectedTournamentBean.getId());
		tournaments = tournamentService.getAll();

		if (selectedTournamentBean.getId().equals(tournamentBean.getId())) {
			tournamentBean = new TournamentBean();
		}
	}
	
	
	/**
     * Method called when user clicks on any row in contracts table,
     * to edit selected exam...
     */
    public void onEditTournament(TournamentBean selectedTournamentBean) {
    	tournamentBean = new TournamentBean(selectedTournamentBean);
       
//        scrollTo("tournamentForm:tournamentCreatePanel");
    }

	/**
	 * Method for Person validation -- for inserting/updating corresponding data
	 * into database...
	 * 
	 * @return
	 */
	private boolean isValidTournament() {
		boolean noError = true;

		if (StringUtils.isBlank(tournamentBean.getName())) {
			addErrorMessage("tournamentForm:name", "contractValidationMandatory");

			noError = false;
		}

		if (tournamentBean.getStartDate() == null) {
			addErrorMessage("tournamentForm:startDate", "contractValidationMandatory");

			noError = false;
		}

		if (tournamentBean.getMaxPartCount() == null) {
			addErrorMessage("tournamentForm:maxPartCount", "contractValidationMandatory");

			noError = false;
		}

		return noError;
	}

	/**
	 * @return the tournamentBean
	 */
	public TournamentBean getTournamentBean() {
		return tournamentBean;
	}

	/**
	 * @param tournamentBean the tournamentBean to set
	 */
	public void setTournamentBean(TournamentBean tournamentBean) {
		this.tournamentBean = tournamentBean;
	}

	/**
	 * @return the tournaments
	 */
	public List<TournamentBean> getTournaments() {
		return tournaments;
	}

	/**
	 * @param tournaments the tournaments to set
	 */
	public void setTournaments(List<TournamentBean> tournaments) {
		this.tournaments = tournaments;
	}
}
