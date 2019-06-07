package com.epam.keikom.dao.exceptions;

public class UnknownIdentifierException extends Exception
{
	public UnknownIdentifierException(String errorMessage)
	{
		super(errorMessage);
	}
}
