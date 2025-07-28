package com.team2.supermarket.service;

public interface CrudService <Entity,Id>{

    public String create(Entity entity);

    public Entity getById (Id id);

    public String update (Id id , Entity Dto );

    public void  delete (Id id );

}
