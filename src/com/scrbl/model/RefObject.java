package com.scrbl.model;

//----------------------------------------------------------------------------------------
//	This class is used to simulate the ability to pass arguments by reference in Java.
//----------------------------------------------------------------------------------------
public final class RefObject<T>
{
	public T argvalue;
	public RefObject(T refarg)
	{
		argvalue = refarg;
	}
}