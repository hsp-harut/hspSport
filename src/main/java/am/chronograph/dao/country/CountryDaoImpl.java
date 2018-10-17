package am.chronograph.dao.country;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.domain.country.Country;

@Repository
public class CountryDaoImpl extends GenericDaoImpl<Country> implements CountryDao {
}
