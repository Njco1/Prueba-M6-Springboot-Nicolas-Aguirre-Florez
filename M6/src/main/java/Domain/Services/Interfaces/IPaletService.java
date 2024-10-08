package Domain.Services.Interfaces;

import java.util.List;

import Domain.Entities.Palet;

public interface IPaletService {

    Palet create(Palet palet);

    Palet update(Long paletId, Palet updatePalet);

    List <Palet> readAll() throws Exception;

    Palet getById(Long paletId) throws Exception;

    boolean getDeleteById(Long paletId);
}
