package com.epam.keikom.dao.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;

import javax.annotation.Resource;

@Repository
public class DomainMap
{
	@Resource(name = "auditoriumsMap")
	public Map<String, Auditorium> auditoriumMap;


	public Map<String, Auditorium> getAuditoriumMap()
	{
		return auditoriumMap;
	}

	public void setAuditoriumMap(Map<String, Auditorium> auditoriumMap)
	{
		this.auditoriumMap = auditoriumMap;
	}


}
