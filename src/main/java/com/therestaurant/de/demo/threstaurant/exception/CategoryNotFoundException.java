package com.therestaurant.de.demo.threstaurant.exception;

public class CategoryNotFoundException extends  RuntimeException
{
    public CategoryNotFoundException(Integer id)
    {
        super("Could not find category " + id);
    }
}
