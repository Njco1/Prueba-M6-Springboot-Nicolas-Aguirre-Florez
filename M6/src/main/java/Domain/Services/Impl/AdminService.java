package Domain.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import Domain.Entities.Admin;
import Domain.Services.Interfaces.IAdminService;
import Infrastructure.Persistence.AdminRepository;

@Service
public class AdminService implements IAdminService {


    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }


    @Override
    @Transactional
    public Admin create(Admin admin) {
        try{
            Admin adminCreated = Admin.builder()
                    .name(admin.getName())
                    .lastName(admin.getLastName())
                    .email(admin.getEmail())
                    .password(admin.getPassword())
                    .build();
            return adminRepository.save(adminCreated);

        }catch (Exception e){
            throw new RuntimeException("ERROR: Admin not be created", e);

        }
    }

    @Override
    @Transactional  
    public Admin update(Long adminId, Admin updatedAdmin) {

        try {
            Optional<Admin> existingAdmin = adminRepository.findById(adminId);

            if (existingAdmin.isPresent()) {
                Admin adminToUpdate = existingAdmin.get();

                adminToUpdate.setName(updatedAdmin.getName());
                adminToUpdate.setLastName(updatedAdmin.getLastName());
                adminToUpdate.setEmail(updatedAdmin.getEmail());
                adminToUpdate.setPassword(updatedAdmin.getPassword());

                return adminRepository.save(adminToUpdate);
            } else {
                throw new RuntimeException("ERROR: Admin not found for update");
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR: Could not update the admin", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> readAll() throws Exception {

        try{
            return adminRepository.findAll();

        }catch (DataAccessException e){
            throw new Exception("ERROR: Admin not have obtain from DATABASE");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getById(Long AdminId) throws Exception {

        try {
            Optional<Admin> admin = adminRepository.findById(AdminId);

            if (admin.isPresent()) {
                return admin.get();
            } else {
                throw new RuntimeException("ERROR: Admin not found with ID " + AdminId);
            }

        } catch (Exception e) {
            throw new Exception("ERROR: Could not retrieve admin with ID " + AdminId, e);
        }
    }

    @Override
    @Transactional
    public boolean getDeleteById(Long AdminId) {

        try{
            adminRepository.deleteById(AdminId);
            if (adminRepository.findById(AdminId) != null){
                return true;
            }
            return false;

        }catch (Exception e){
            throw new RuntimeException("ERROR: Admin was not delete by ID");
        }
    }
    
}
