package Domain.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Domain.Entities.Palet;
import Domain.Services.Interfaces.IPaletService;
import Infrastructure.Persistence.PaletRepository;

@Service
public class PaletService implements IPaletService {

    private final PaletRepository paletRepository;

    public PaletService(PaletRepository paletRepository){
        this.paletRepository = paletRepository;
    }
    
    @Override
    @Transactional
    public Palet create(Palet palet) {
        try{
            Palet paletCreated = Palet.builder()
                    .maxWeight(palet.getMaxWeight())
                    .state(palet.getState())
                    .build();
            return paletRepository.save(paletCreated);

        }catch (Exception e){
            throw new RuntimeException("ERROR: Palet not be created", e);

        }
    }

    @Override
    @Transactional  
    public Palet update(Long paletId, Palet updatedPalet) {

        try {
            Optional<Palet> existingPalet = paletRepository.findById(paletId);

            if (existingPalet.isPresent()) {
                Palet paletToUpdate = existingPalet.get();

                paletToUpdate.setMaxWeight(updatedPalet.getMaxWeight());
                paletToUpdate.setState(updatedPalet.getState());

                return paletRepository.save(paletToUpdate);
            } else {
                throw new RuntimeException("ERROR: Palet not found for update");
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR: Could not update the palet", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Palet> readAll() throws Exception {

        try{
            return paletRepository.findAll();

        }catch (DataAccessException e){
            throw new Exception("ERROR: Palet not have obtain from DATABASE");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Palet getById(Long PaletId) throws Exception {

        try {
            Optional<Palet> palet = paletRepository.findById(PaletId);

            if (palet.isPresent()) {
                return palet.get();
            } else {
                throw new RuntimeException("ERROR: Palet not found with ID " + PaletId);
            }

        } catch (Exception e) {
            throw new Exception("ERROR: Could not retrieve palet with ID " + PaletId, e);
        }
    }

    @Override
    @Transactional
    public boolean getDeleteById(Long PaletId) {

        try{
            paletRepository.deleteById(PaletId);
            if (paletRepository.findById(PaletId) != null){
                return true;
            }
            return false;

        }catch (Exception e){
            throw new RuntimeException("ERROR: Palet was not delete by ID");
        }
    }
}
