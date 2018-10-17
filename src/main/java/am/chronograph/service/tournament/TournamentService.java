package am.chronograph.service.tournament;

import java.util.List;
import am.chronograph.web.bean.tournament.TournamentBean;

/**
 * Provides services for getting, managing Tournaments.
 * 
 * @author davitpetrosyan
 *
 */
public interface TournamentService {

	/**
	 * Creates given tournament...
	 * 
	 * @param countryBean
	 */
	void create(TournamentBean tournamentBean);

	/**
	 * Updates tournament by given one...
	 * 
	 * @param countryBean
	 */
	void update(TournamentBean tournamentBean);

	/**
	 * Delets tournament by given id...
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all tournaments...
	 * 
	 * @return
	 */
	List<TournamentBean> getAll();
}
