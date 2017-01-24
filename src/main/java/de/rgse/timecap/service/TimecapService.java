package de.rgse.timecap.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

import de.rgse.timecap.model.QTimeevent;
import de.rgse.timecap.model.Timeevent;

public class TimecapService {

	private static final QTimeevent TIMEEVENT = QTimeevent.timeevent;

	@PersistenceContext(unitName = "timecap-server")
	private EntityManager entityManager;

	public List<Timeevent> getTimeevents(String userId, String locationId) {
		JPAQuery query = new JPAQuery(entityManager).from(TIMEEVENT);

		if (userId != null) {
			query = query.where(TIMEEVENT.userId.eq(userId));
		}

		if (locationId != null) {
			query = query.where(TIMEEVENT.locationId.eq(locationId));
		}

		return query.list(TIMEEVENT);
	}

	public Timeevent createTimeevent(Timeevent timeevent) {
		entityManager.persist(timeevent);
		return timeevent;
	}
}
