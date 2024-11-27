package com.dashtap.DASHTAP.services.impl;

import com.dashtap.DASHTAP.models.*;
import com.dashtap.DASHTAP.payload.requests.AddVehicleRequest;
import com.dashtap.DASHTAP.payload.requests.EditVehicleRequest;
import com.dashtap.DASHTAP.repositories.*;
import com.dashtap.DASHTAP.services.interfac.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service("vehicleService")
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final BrandRepository brandRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleImageRepository vehicleImageRepository;
    private final FuelTypeRepository fuelTypeRepository;

    @Override
    public List<Vehicle> findAvailableVehicles() {
        return vehicleRepository.findByAvailable(true);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public boolean existsById(Long vehicleID) {
        return vehicleRepository.existsById(vehicleID);
    }

    @Override
    public Vehicle getVehicleById(Long vehicleID) {
        return vehicleRepository.getVehicleById(vehicleID);
    }

    private Brand addBrand(String brandName) {
        return Optional.ofNullable(brandRepository.findByName(brandName)).orElseGet(() -> brandRepository.save(new Brand(brandName)));
    }

    private VehicleModel addModel(String modelName) {
        return Optional.ofNullable(vehicleModelRepository.findByName(modelName)).orElseGet(() -> vehicleModelRepository.save(new VehicleModel(modelName)));
    }

    @Override
    @Transactional
    public void add(AddVehicleRequest vehicleRequest) {
        Brand brand = addBrand(vehicleRequest.getBrand());
        VehicleModel model = addModel(vehicleRequest.getVehicleModel());

        FuelType fuelType = fuelTypeRepository.findById(vehicleRequest.getFuelType()).orElseThrow(() -> new RuntimeException("Error: Fuel type is not found."));
        VehicleImage carImage = vehicleImageRepository.findById(1L).orElseThrow(() -> new RuntimeException("Error: Vehicle image is not found"));

        Vehicle vehicle = new Vehicle(
                brand,
                model,
                vehicleRequest.getColor(),
                vehicleRequest.getYear(),
                vehicleRequest.getMileage(),
                fuelType,
                vehicleRequest.getHorsePower(),
                vehicleRequest.getCapacity(),
                vehicleRequest.getDescription(),
                vehicleRequest.getVehicleNumber(),
                vehicleRequest.getPrice(),
                true,
                carImage
        );
        vehicleRepository.save(vehicle);
    }


    @Override
    public void changeImage(Long vehicleID, MultipartFile file) throws IOException {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleID);
        Long imageID = vehicle.getVehicleImage().getImageID();

        VehicleImage vehicleImage = vehicleImageRepository.save(new VehicleImage(file.getBytes()));
        vehicle.setVehicleImage(vehicleImage);

        deleteImage(imageID);
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void delete(Vehicle vehicle) {
        deleteModel(vehicle.getVehicleModel());
        deleteBrand(vehicle.getBrand());
        deleteImage(vehicle.getVehicleImage().getImageID());

        vehicleRepository.deleteById(vehicle.getId());
    }

    @Override
    public void changeStatus(Vehicle vehicle) {
        vehicle.setAvailable(!vehicle.isAvailable());
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void update(Long vehicleID, EditVehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleID);

        if(!vehicleRequest.getBrand().equals(vehicle.getBrand().getName())){
            Brand brand = vehicle.getBrand();
            vehicle.setBrand(addBrand(vehicleRequest.getBrand()));

            if(!vehicleRepository.existsByBrandName(brand.getName())){
                brandRepository.deleteByName(brand.getName());
            }
        }

        if(!vehicleRequest.getModel().equals(vehicle.getVehicleModel().getName())){
            VehicleModel vehicleModel = vehicle.getVehicleModel();
            vehicle.setVehicleModel(addModel(vehicleRequest.getModel()));

            if(!vehicleRepository.existsByModelName(vehicleModel.getName())){
                vehicleModelRepository.deleteByName(vehicleModel.getName());
            }
        }

        if(!vehicleRequest.getCapacity().equals(vehicle.getCapacity())){
            vehicle.setCapacity(vehicleRequest.getCapacity());
        }

        if(!vehicleRequest.getHorsePower().equals(vehicle.getHorsePower())){
            vehicle.setHorsePower(vehicleRequest.getHorsePower());
        }

        if(!vehicleRequest.getYear().equals(vehicle.getYear())){
            vehicle.setYear(vehicleRequest.getYear());
        }

        if(!vehicleRequest.getMileage().equals(vehicle.getMileage())){
            vehicle.setMileage(vehicleRequest.getMileage());
        }

        if(!vehicleRequest.getPrice().equals(vehicle.getPrice())){
            vehicle.setPrice(vehicleRequest.getPrice());
        }

        if(!vehicleRequest.getFuelType().equals(vehicle.getFuelType().getId())){
            vehicle.setFuelType(fuelTypeRepository.findById(vehicleRequest.getFuelType())
                    .orElseThrow(() -> new RuntimeException("Error: Fuel type is not found.")));
        }

        vehicleRepository.save(vehicle);
    }

    private void deleteImage(Long imageID){
        // CarImage with ID = 1 is the default image for new cars. For this reason, it must be protected from deletion
        if(imageID != 1) {
            vehicleImageRepository.deleteById(imageID);
        }
    }

    private void deleteModel(VehicleModel model){
        if(vehicleRepository.countByModelName(model.getName()) == 1){
            vehicleModelRepository.deleteById(model.getId());
        }
    }

    private void deleteBrand(Brand brand){
        if(vehicleRepository.countByBrandName(brand.getName()) == 1){
            brandRepository.deleteById(brand.getId());
        }
    }

}
