package org.myftp.gattserver.grass3.model.service;

import java.util.List;

/**
 * Interface, přes který se model modulu předávají k registraci třídy entit
 * 
 * @author gatt
 * 
 */
public interface IEntityService {

	public List<Class<?>> getDomainClasses();

}
