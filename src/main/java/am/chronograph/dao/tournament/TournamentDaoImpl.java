package am.chronograph.dao.tournament;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.domain.tournament.Tournament;

@Repository
public class TournamentDaoImpl extends GenericDaoImpl<Tournament> implements TournamentDao {

}
