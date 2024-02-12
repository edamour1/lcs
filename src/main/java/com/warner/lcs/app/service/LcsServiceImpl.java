package com.warner.lcs.app.service;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.repository.LcsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LcsServiceImpl implements LcsService {

    @Autowired
    LcsRepository lcsRepository;

    @Override
    public Zipcode saveZipcode(Zipcode zipcode, City city) throws Exception { return this.lcsRepository.saveZipcode(zipcode,city); }

    @Override
    public Zipcode getZipcodesById(int id) throws Exception { return this.lcsRepository.getZipcodesById(id); }

    @Override
    public List<Zipcode> getZipcodesByCity(String city) throws Exception { return this.lcsRepository.getZipcodesByCity(city); }

    @Override
    public List<Zipcode> getAllZipcodes() throws Exception { return this.lcsRepository.getAllZipcodes(); }

    @Override
    public Treatment saveTreatment(Treatment treatment) throws Exception { return this.lcsRepository.saveTreatment(treatment); }

    @Override
    public Treatment getTreatmentById(int id) throws Exception { return this.lcsRepository.getTreatmentById(id); }

    @Override
    public Treatment updateTreatment(Treatment treatment) throws Exception { return this.lcsRepository.updateTreatment(treatment); }

    @Override
    public List<Treatment> getAllTreatments() throws Exception { return this.lcsRepository.getAllTreatments(); }

    @Override
    public List<State> getAllStates() throws Exception { return this.lcsRepository.getAllStates(); }

    @Override
    public City saveCity(City city) throws Exception { return this.lcsRepository.saveCity(city); }

    @Override
    public List<City> getCities() throws Exception { return this.lcsRepository.getCities(); }

    @Override
    public Admin saveAdmin(Admin admin) throws Exception { return this.lcsRepository.saveAdmin(admin); }

    @Override
    public Admin getAdminById(int id) throws Exception { return this.lcsRepository.getAdminById(id); }

    @Override
    public List<Admin> getAllAdmin() throws Exception { return this.lcsRepository.getAllAdmin(); }

    @Override
    public Client saveClient(Client client) throws Exception { return this.lcsRepository.saveClient(client); }

    @Override
    public Client updateClient(Client client) throws Exception { return this.lcsRepository.updateClient(client); }

    @Override
    public Client getClientById(int id) throws Exception { return this.lcsRepository.getClientById(id); }

    @Override
    public List<Client> getClients() throws Exception { return this.lcsRepository.getClients(); }

}
