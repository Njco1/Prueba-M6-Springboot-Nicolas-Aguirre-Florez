package Domain.Services.Interfaces;

import java.util.List;

import Domain.Entities.Load;

public interface ILoadService {

    Load create(Load load);

    Load update(Long loadId, Load updatedLoad);

    List <Load> readAll() throws Exception;

    Load getById(Long loadId) throws Exception;

    boolean getDeleteById(Long loadId);
}
