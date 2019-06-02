package com.bot.telegram.hpk.services;

import com.bot.telegram.hpk.services.util.ScheduleUIConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.xml.ws.Response;
import java.util.Map;

@Service
public class TimetableService {

	private final static String GET_TIMETABLE_TIME_URL = "/timetable/time";

	@Value("${hpk.api.url}")
	private String hpkApiUrl;

	@PostConstruct
	public void init(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(hpkApiUrl.concat(GET_TIMETABLE_TIME_URL),
				HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, String>>() {});
		ScheduleUIConstants.PAIR_TIME = responseEntity.getBody();
	}

}
