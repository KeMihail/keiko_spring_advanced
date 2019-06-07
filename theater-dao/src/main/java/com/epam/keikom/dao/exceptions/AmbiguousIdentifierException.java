package com.epam.keikom.dao.exceptions;

public class AmbiguousIdentifierException extends Exception
{
	public AmbiguousIdentifierException(String errorMessage)
	{
		super(errorMessage);
	}
}
