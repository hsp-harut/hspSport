package am.chronograph.web.controller.tournament;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.tournament.TournamentService;
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
