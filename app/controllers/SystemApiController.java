package controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import lib.APIException;
import models.Core;
import models.SystemJob;
import play.mvc.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemApiController extends AuthenticatedController {

    public static Result fields() {
        try {
            Set<String> fields = Core.getMessageFields();

            Map<String, Set<String>> result = Maps.newHashMap();
            result.put("fields", fields);

            return ok(new Gson().toJson(result)).as("application/json");
        } catch (IOException e) {
            return internalServerError("io exception");
        } catch (APIException e) {
            return internalServerError("api exception " + e);
        }
    }

    public static Result jobs() {
        try {
            List<Object> jobs = Lists.newArrayList();
            for(SystemJob j : SystemJob.all()) {
                Map<String, Object> job = Maps.newHashMap();

                job.put("id", j.getId());
                job.put("percent_complete", j.getPercentComplete());

                jobs.add(job);
            }

            Map<String, Object> result = Maps.newHashMap();
            result.put("jobs", jobs);

            return ok(new Gson().toJson(result)).as("application/json");
        } catch (IOException e) {
            return internalServerError("io exception");
        } catch (APIException e) {
            return internalServerError("api exception " + e);
        }
    }

}
