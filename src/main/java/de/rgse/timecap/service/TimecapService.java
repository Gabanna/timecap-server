package de.rgse.timecap.service;

import java.util.Date;
import java.util.List;

import de.rgse.timecap.model.Timeevent;

public interface TimecapService {

	Timeevent createTimeevent(Timeevent timeevent);

	List<Timeevent> getTimeevents(String userId, String locationId, int offset, int limit, int day, int month,
			int year);

	List<Timeevent> getTimeevents(String userId, Date start, Date end);

}
