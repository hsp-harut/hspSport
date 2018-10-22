package am.chronograph.service.tournament;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.tournament.TournamentDao;
import am.chronograph.domain.tournament.Tournament;
import am.chronograph.web.bean.tournament.TournamentBean;
import am.chronograph.web.util.DateUtil;

/**
 * Implements TournamentService interface...
 * @author davitpetrosyan
 *
 */
@Service
@Transactional(readOnly = true)
public class TournamentServiceImpl implements TournamentService {

	@Autowired
	private TournamentDao tournamentDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.tournament.TournamentService#create(am.chronograph.web
	 * .bean.tournament.TournamentBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void create(TournamentBean tournamentBean) {
		tournamentDao.save(initDomainByBean(tournamentBean));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.tournament.TournamentService#update(am.chronograph.web
	 * .bean.tournament.TournamentBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(TournamentBean tournamentBean) {
		tournamentDao.save(initDomainByBean(tournamentBean));

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Tournament tournament = tournamentDao.getById(id);

		tournamentDao.delete(tournament);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.tournament.TournamentService#getAll()
	 */
	@Override
	public List<TournamentBean> getAll() {

		List<Tournament> tournaments = tournamentDao.getAll();

		return initBeanListByDomainList(tournaments);

	}

	/**
	 * Initialize Domain by given Bean...
	 * 
	 * @param tournamentBean
	 * @return
	 */
	private Tournament initDomainByBean(TournamentBean tournamentBean) {
		Tournament tournament = (tournamentBean.getId() != null) ? tournamentDao.getById(tournamentBean.getId())
				: new Tournament();

		tournament.setId(tournamentBean.getId());
		tournament.setName(tournamentBean.getName());
		tournament.setStartDate(DateUtil.getLocalDateTimeByDate(tournamentBean.getStartDate()));
		tournament.setMaxPartCount(tournamentBean.getMaxPartCount());

		return tournament;
	}

	/**
	 * Initialize Bean List by given Domain list...
	 * 
	 * @param countries
	 * @return
	 */
	private List<TournamentBean> initBeanListByDomainList(List<Tournament> tournaments) {
		List<TournamentBean> tournamentBeans = new ArrayList<>();
		for (Tournament tournament : tournaments) {
			tournamentBeans.add(initBeanByDomain(tournament));
		}

		return tournamentBeans;
	}

	/**
	 * Initialize Bean by given Domain...
	 * 
	 * @param country
	 * @return
	 */
	private TournamentBean initBeanByDomain(Tournament tournament) {
		TournamentBean tournamentBean = new TournamentBean();

		tournamentBean.setId(tournament.getId());
		tournamentBean.setName(tournament.getName());
		tournamentBean.setStartDate(DateUtil.getDateByLocalDateTime(tournament.getStartDate()));
		tournamentBean.setMaxPartCount(tournament.getMaxPartCount());

		return tournamentBean;
	}
}
