package Domain.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import Domain.Entities.Load;
import Domain.Services.Interfaces.ILoadService;
import Infrastructure.Persistence.LoadRepository;

@Service
public class LoadService implements ILoadService {

    private final LoadRepository loadRepository;

    public LoadService(LoadRepository loadRepository){
        this.loadRepository = loadRepository;
    }

    @Override
    @Transactional
    public Load create(Load load) {
        try{
            Load loadCreated = Load.builder()
                    .weight(load.getWeight())
                    .dimensions(load.getDimensions())
                    .state(load.getState())
                    .build();
            return loadRepository.save(loadCreated);

        }catch (Exception e){
            throw new RuntimeException("ERROR: Load not be created", e);

        }
    }

    @Override
    @Transactional  
    public Load update(Long loadId, Load updatedLoad) {

        try {
            Optional<Load> existingLoad = loadRepository.findById(loadId);

            if (existingLoad.isPresent()) {
                Load loadToUpdate = existingLoad.get();

                loadToUpdate.setWeight(updatedLoad.getWeight());
                loadToUpdate.setDimensions(updatedLoad.getDimensions());
                loadToUpdate.setState(updatedLoad.getState());

                return loadRepository.save(loadToUpdate);
            } else {
                throw new RuntimeException("ERROR: Load not found for update");
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR: Could not update the load", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Load> readAll() throws Exception {

        try{
            return loadRepository.findAll();

        }catch (DataAccessException e){
            throw new Exception("ERROR: Load not have obtain from DATABASE");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Load getById(Long LoadId) throws Exception {

        try {
            Optional<Load> load = loadRepository.findById(LoadId);

            if (load.isPresent()) {
                return load.get();
            } else {
                throw new RuntimeException("ERROR: Load not found with ID " + LoadId);
            }

        } catch (Exception e) {
            throw new Exception("ERROR: Could not retrieve load with ID " + LoadId, e);
        }
    }

    @Override
    @Transactional
    public boolean getDeleteById(Long LoadId) {

        try{
            loadRepository.deleteById(LoadId);
            if (loadRepository.findById(LoadId) != null){
                return true;
            }
            return false;

        }catch (Exception e){
            throw new RuntimeException("ERROR: Load was not delete by ID");
        }
    }


    
}
