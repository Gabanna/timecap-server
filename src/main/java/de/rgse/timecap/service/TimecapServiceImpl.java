package de.rgse.timecap.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

import de.rgse.timecap.model.QTimeevent;
import de.rgse.timecap.model.Timeevent;

public class TimecapServiceImpl implements TimecapService {

	private static final QTimeevent TIMEEVENT = QTimeevent.timeevent;

	@PersistenceContext(unitName = "timecap-server")
	private EntityManager entityManager;

	@Override
	public List<Timeevent> getTimeevents(String userId, String locationId, int offset, int limit, int day, int month,
			int year) {
		JPAQuery query = new JPAQuery(entityManager).from(TIMEEVENT);

		if (userId != null) {
			query = query.where(TIMEEVENT.userId.eq(userId));
		}

		if (locationId != null) {
			query = query.where(TIMEEVENT.locationId.eq(locationId));
		}

		if (day >= 0) {
			query = query.where(TIMEEVENT.instant.dayOfMonth().eq(day));
		}

		if (month >= 0) {
			query = query.where(TIMEEVENT.instant.month().eq(month));
		}

		if (year >= 0) {
			query = query.where(TIMEEVENT.instant.year().eq(year));
		}

		if (offset >= 0) {
			query.offset(offset);
		}

		if (limit > 0) {
			query.limit(limit);
		}

		return query.orderBy(TIMEEVENT.instant.asc()).list(TIMEEVENT);
	}

	@Override
	public Timeevent createTimeevent(Timeevent timeevent) {
		entityManager.persist(timeevent);
		return timeevent;
	}
}
