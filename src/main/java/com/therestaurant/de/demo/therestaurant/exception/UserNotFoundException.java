package com.therestaurant.de.demo.therestaurant.exception;

public class UserNotFoundException extends  RuntimeException
{
    public UserNotFoundException(Integer id)
    {
        super("Could not find user " + id);
    }
}
