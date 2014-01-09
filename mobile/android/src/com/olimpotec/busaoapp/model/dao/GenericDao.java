package com.olimpotec.busaoapp.model.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.olimpotec.busaoapp.helper.Database;

public abstract class GenericDao<E, ID>  
{ 
	protected Dao<E, ID> dao;
	
	private Class<E> type;
	
	@SuppressWarnings("unchecked")
	public GenericDao() throws SQLException
	{
		this.type = (Class<E>) ((ParameterizedType) getClass()  
                .getGenericSuperclass()).getActualTypeArguments()[0];  
		
		dao = DaoManager.createDao(Database.singleton().getConnectionSource(), type);
	}
	
	public List<E> getAll() 
	{
		try
		{
			List<E> result = dao.queryForAll();
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<E> getByPage(int offset, int maxRows) 
	{
		try
		{
			List<E> result = dao.query(dao.queryBuilder().limit(Long.valueOf(maxRows)).offset(Long.valueOf(offset)).prepare());
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public E getById(ID id) 
	{
		try{
			E obj = dao.queryForId(id);
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public void insert(E obj) 
	{
		try{
			dao.create(obj);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void delete(E obj) 
	{
		try{
			dao.delete(obj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void update(E obj) 
	{
		try{
			dao.update(obj); 
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
}
