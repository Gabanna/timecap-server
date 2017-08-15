package de.rgse.timecap.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.rgse.timecap.model.Timeevent;
import de.rgse.timecap.service.TimecapServiceImpl;

@Named
@Path("/time-events")
@RequestScoped
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimecapResource {

	private static final Logger LOGGER = LogManager.getLogManager().getLogger(TimecapResource.class.getSimpleName());

	@Inject
	private TimecapServiceImpl timecapService;

	@GET
	public Response getTimeevents(@QueryParam("userId") String userId, @QueryParam("locationId") String locationId,
			@QueryParam("offset") @DefaultValue("-1") int offset, @DefaultValue("-1") @QueryParam("limit") int limit,
			@DefaultValue("-1") @QueryParam("day") int day, @DefaultValue("-1") @QueryParam("month") int month,
			@DefaultValue("-1") @QueryParam("year") int year) {
		ResponseBuilder response = null;

		try {
			List<Timeevent> timeevents = timecapService.getTimeevents(userId, locationId, offset, limit, day, month,
					year);
			response = timeevents.isEmpty() ? Response.noContent() : Response.ok(timeevents);

		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "", exception);
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("error", exception.getMessage());

			response = Response.serverError().entity(new Gson().toJson(jsonObject));
		}

		return response.build();
	}

	@POST
	public Response createTimeevent(Timeevent timeevent) {
		ResponseBuilder response = null;

		try {
			Timeevent event = timecapService.createTimeevent(timeevent);
			response = Response.ok(event);

		} catch (Exception exception) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("error", exception.getMessage());

			response = Response.serverError().entity(new Gson().toJson(jsonObject));
		}

		return response.build();
	}
	
	@POST
	@Path("migrate")
	public Response migrate(List<Timeevent> timeevents) {
		Gson gson = new Gson();

		List<String> success = new ArrayList<>();
		List<String> fail = new ArrayList<>();
		
		for (Timeevent timeevent : timeevents) {
			try {
				Timeevent event = timecapService.createTimeevent(timeevent);
				success.add(event.getId());
				
			} catch (Exception exception) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("error", exception.getMessage());				
				jsonObject.addProperty("event", timeevent.getId());	
			}
		}

		JsonObject result = new JsonObject();
		result.addProperty("successfull", gson.toJson(success));
		result.addProperty("fail", gson.toJson(fail));
		
		return Response.ok(result.toString()).build();
	}
}
