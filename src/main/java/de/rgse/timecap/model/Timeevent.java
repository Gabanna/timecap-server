package de.rgse.timecap.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Timeevent {

	@Transient
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy-MM-dd'T'HH:mm:ss");

	@Transient
	private final Logger LOGGER = LogManager.getLogManager().getLogger(getClass().getSimpleName());

	@Id
	@JsonInclude(Include.NON_NULL)
	private String id;

	@JsonInclude(Include.NON_NULL)
	private String instant;

	@Transient
	@JsonIgnore
	private Calendar instantAsCalendar;

	private String userId;

	private String locationId;

	Timeevent() {
	}

	public Timeevent(String userId, String locationId) {
		this.userId = userId;
		this.locationId = locationId;
	}

	@PrePersist
	private void prePersist() {
		id = UUID.randomUUID().toString();
		instant = DATE_FORMAT.format(new Date());
	}

	public String getId() {
		return id;
	}

	public String getInstant() {
		return instant;
	}

	public Calendar getInstantAsCalendar() {
		try {
			if (instantAsCalendar == null && instant != null) {
				instantAsCalendar = GregorianCalendar.getInstance();
				instantAsCalendar.setTime(DATE_FORMAT.parse(instant));
			}
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "Error while parsing instant", e);
		}

		return instantAsCalendar;
	}

	public String getUserId() {
		return userId;
	}

	public String getLocationId() {
		return locationId;
	}

}
