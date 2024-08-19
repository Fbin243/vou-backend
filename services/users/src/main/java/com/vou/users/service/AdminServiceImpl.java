package com.vou.users.service;

import com.vou.users.dao.AdminRepository;
import com.vou.users.entity.Admin;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository theAdminRepository) {
        adminRepository = theAdminRepository;
    }

    @Override
    @Transactional
    public void saveAdmin(Admin theAdmin) {
        adminRepository.save(theAdmin);
    }

    @Override
    public Admin findAdminById(String theId) {
        Optional<Admin> result = adminRepository.findById(theId);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Did not find admin id - " + theId);
        }
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional
    public void updateAdmin(Admin theAdmin) {
        adminRepository.save(theAdmin);
    }

    @Override
    @Transactional
    public void deleteAdminById(String theId) {
        adminRepository.deleteById(theId);
    }
}
