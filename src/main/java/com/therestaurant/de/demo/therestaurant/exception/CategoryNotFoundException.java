package com.therestaurant.de.demo.therestaurant.exception;

public class CategoryNotFoundException extends  RuntimeException
{
    public CategoryNotFoundException(Integer id)
    {
        super("Could not find category " + id);
    }
}
