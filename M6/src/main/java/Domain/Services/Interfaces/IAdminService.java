package Domain.Services.Interfaces;

import java.util.List;

import Domain.Entities.Admin;

public interface IAdminService {
    
    Admin create(Admin admin);

    Admin update(Long adminId, Admin updatedAdmin);

    List <Admin> readAll() throws Exception;

    Admin getById(Long AdminId) throws Exception;

    boolean getDeleteById(Long AdminId);

}
