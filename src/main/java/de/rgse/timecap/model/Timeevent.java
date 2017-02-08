package de.rgse.timecap.model;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.rgse.timecap.service.DateParser;

@Entity
public class Timeevent {

	static {
		Locale.setDefault(Locale.GERMANY);
	}

	@Transient
	private final Logger LOGGER = LogManager.getLogManager().getLogger(getClass().getSimpleName());

	@Id
	@JsonInclude(Include.NON_NULL)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = DateParser.PATTERN)
	private Calendar instant;

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
		if (null == instant) {
			instant = Calendar.getInstance(Locale.GERMANY);
		}
	}

	public String getId() {
		return id;
	}

	public Calendar getInstant() {
		return instant;
	}

	public String getUserId() {
		return userId;
	}

	public String getLocationId() {
		return locationId;
	}

}
