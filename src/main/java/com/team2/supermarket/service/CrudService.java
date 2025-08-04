package com.team2.supermarket.service;

import com.team2.supermarket.dto.PageResult;

import java.util.List;
import java.util.Map;

public interface CrudService <Dto,Id>{

    public String create(Dto dto);

    public Dto getById (Id id);

    public String update (Id id , Dto dto );

    public void  delete (Id id );

    public List<Dto> getAll();

    PageResult search(Map<String,String> params);


}
