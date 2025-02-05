package com.dashtap.DASHTAP.config;

import com.dashtap.DASHTAP.models.Enums.*;
import com.dashtap.DASHTAP.models.*;
import com.dashtap.DASHTAP.repositories.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Configuration
public class RepositoryInitializer {
    private final FuelTypeRepository fuelTypeRepository;
    private final RoleRepository roleRepository;
    private final VehicleImageRepository vehicleImageRepository;
    private final RentalStatusRepository rentalStatusRepository;
    private final PasswordEncoder encoder;

    public RepositoryInitializer(FuelTypeRepository fuelTypeRepository, RoleRepository roleRepository,
                                 VehicleImageRepository vehicleImageRepository, RentalStatusRepository rentalStatusRepository, PasswordEncoder encoder) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.roleRepository = roleRepository;
        this.vehicleImageRepository = vehicleImageRepository;
        this.rentalStatusRepository = rentalStatusRepository;
        this.encoder = encoder;
    }

    @Bean
    InitializingBean init(UserRepository userRepository) {
        return () -> {
            if(fuelTypeRepository.findAll().isEmpty()){
                fuelTypeRepository.save(new FuelType(FuelTypeEnum.FUEL_DIESEL));
                fuelTypeRepository.save(new FuelType(FuelTypeEnum.FUEL_GASOLINE));
                fuelTypeRepository.save(new FuelType(FuelTypeEnum.FUEL_LPG));
                fuelTypeRepository.save(new FuelType(FuelTypeEnum.FUEL_ELECTRIC));
                fuelTypeRepository.save(new FuelType(FuelTypeEnum.FUEL_HYBRID));
            }

            if(rentalStatusRepository.findAll().isEmpty()){
                rentalStatusRepository.save(new RentalStatus(RentalStatusEnum.STATUS_ACCEPTED));
                rentalStatusRepository.save(new RentalStatus(RentalStatusEnum.STATUS_CANCELLED));
                rentalStatusRepository.save(new RentalStatus(RentalStatusEnum.STATUS_PENDING));
                rentalStatusRepository.save(new RentalStatus(RentalStatusEnum.STATUS_REJECTED));
            }

            if(roleRepository.findAll().isEmpty()){
                roleRepository.save(new Role(RoleEnum.ROLE_ADMIN));
                roleRepository.save(new Role(RoleEnum.ROLE_USER));
                roleRepository.save(new Role(RoleEnum.ROLE_DRIVER));
                roleRepository.save(new Role(RoleEnum.ROLE_OWNER));
            }

            if(vehicleImageRepository.findAll().isEmpty()){
                vehicleImageRepository.save(new VehicleImage(imageFromURLToByteArray(new URL("https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg"))));
            }

            if(userRepository.findAll().isEmpty()){
                Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                User admin = new User("admin", "admin@gmail.com", encoder.encode("admin"), adminRole);
                userRepository.save(admin);

                Role driverRole = roleRepository.findByName(RoleEnum.ROLE_DRIVER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                User driver = new User("driver", "driver@gmail.com", encoder.encode("driver"), driverRole);
                userRepository.save(driver);

                Role ownerRole = roleRepository.findByName(RoleEnum.ROLE_OWNER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                User owner = new User("owner", "owner@gmail.com", encoder.encode("owner"), ownerRole);
                userRepository.save(owner);

                Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                User user = new User("user", "user@gmail.com", encoder.encode("user"), userRole);
                userRepository.save(user);
            }
        };
    }

    public static byte[] imageFromURLToByteArray(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(conn.getInputStream(), baos);

        return baos.toByteArray();
    }
}
