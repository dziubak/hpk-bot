package com.bot.telegram.hpk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bot.telegram.hpk.component.dao.ReplacementDao;
import com.bot.telegram.hpk.component.model.api.Replacement;

@Service
public class ReplacementService {
	
	@Autowired
	private ReplacementDao replacementDao;

	public List<Replacement> getAllReplacements() {
		return replacementDao.getAllReplacements();
	}

}
