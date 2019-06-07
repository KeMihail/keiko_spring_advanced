package com.epam.keikom.service.impl;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.keikom.dao.domain.Auditorium;
import com.epam.keikom.service.IAuditoriumService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toCollection;

@Service
public class AuditoriumService implements IAuditoriumService
{

	@Value("#{domainMap.getAuditoriumMap()}")
	public Map<String, Auditorium> auditoriumMap;

	public void setAuditoriumMap(Map<String, Auditorium> auditoriumMap)
	{
		this.auditoriumMap = auditoriumMap;
	}

	@Nonnull
	@Override
	public Set<Auditorium> getAll()
	{

		return auditoriumMap.values().stream().collect(toCollection(HashSet::new));
	}

	@Nullable
	@Override
	public Auditorium getByName(@Nonnull String name)
	{
		return auditoriumMap.get(name);
	}
}
