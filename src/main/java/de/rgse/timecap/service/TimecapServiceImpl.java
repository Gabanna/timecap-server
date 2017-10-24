package de.rgse.timecap.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

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

		if(year > -1) {
			Calendar lowerThreshold = new GregorianCalendar(year, month >= 0 ? month : 0, day >= 0 ? day : 1);
			Calendar upperThreshold = new GregorianCalendar(year, month >= 0 ? month : 11, day >= 0 ? day : getMaximum(year, month));
		
			query = query.where(TIMEEVENT.time.between(lowerThreshold.getTimeInMillis(), upperThreshold.getTimeInMillis()));
		}
		
		if (userId != null) {
			query = query.where(TIMEEVENT.userId.eq(userId));
		}

		if (locationId != null) {
			query = query.where(TIMEEVENT.locationId.eq(locationId));
		}

		if (offset >= 0) {
			query.offset(offset);
		}

		if (limit > 0) {
			query.limit(limit);
		}

		return query.orderBy(TIMEEVENT.time.asc()).list(TIMEEVENT);
	}

	@Override
	public Timeevent createTimeevent(Timeevent timeevent) {
		entityManager.persist(timeevent);
		return timeevent;
	}

	@Override
	public List<Timeevent> getTimeevents(String userId, Date start, Date end) {
		BooleanExpression expression = null;
		
		if(userId != null) {
			expression = TIMEEVENT.userId.eq(userId);
		}
		
		if(start != null) {
			BooleanExpression clause = TIMEEVENT.time.goe(start.getTime());
			expression = expression == null ? clause : expression.and(clause);
		}
		
		if(end != null) {
			BooleanExpression clause = TIMEEVENT.time.lt(end.getTime());
			expression = expression == null ? clause : expression.and(clause);
		}
		
		JPAQuery query = new JPAQuery(entityManager).from(TIMEEVENT);
		
		if(expression != null) {
			query = query.where(expression);
		}
		
		return query.list(TIMEEVENT);
	}
	
	private int getMaximum(int year, int month) {
		Calendar calendar = new GregorianCalendar(Locale.getDefault());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
