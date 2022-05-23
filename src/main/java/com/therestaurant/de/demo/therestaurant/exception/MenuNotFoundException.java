package com.therestaurant.de.demo.therestaurant.exception;

public class MenuNotFoundException extends  RuntimeException
{
    public MenuNotFoundException(Integer id)
    {
        super("Could not find category " + id);
    }
}
