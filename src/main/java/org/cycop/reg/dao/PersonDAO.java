package org.cycop.reg.dao;

import java.util.List;

import org.cycop.reg.dataobjects.Person;

public interface PersonDAO {

    public void saveOrUpdate(Person contact);

    public void delete(Long personId);

    public List<Person> get(Long personId);

    public List<Person> list();
}