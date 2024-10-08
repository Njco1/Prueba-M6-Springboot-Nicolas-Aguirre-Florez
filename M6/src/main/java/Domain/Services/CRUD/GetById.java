package Domain.Services.CRUD;

public interface GetById<Entity, ID>{
    public Entity getById(ID id);
}
