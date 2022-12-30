package br.com.marcia.adapters;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import br.com.marcia.adapters.outbound.database.repository.CarRepository;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {

        // inserir dados iniciais para teste
        CarEntity car1 = new CarEntity();
        car1.setChassi("9BWZZZ377VT004251");
        car1.setColor("white");
        car1.setDeleted(false);
        car1.setManufacturer("Automobili Lamborghini S.P.A.");
        car1.setName("Lamborghini Urus");
        car1.setPlaca("OPA0148");
        car1.setStatus(VehicleStatusEnum.ACTIVATED);
        car1.setYear(2021);
        carRepository.save(car1);

        CarEntity car2 = new CarEntity();
        car2.setChassi("8AWZZZ377VT004247");
        car2.setColor("black");
        car2.setDeleted(false);
        car2.setManufacturer("Automobili Lamborghini S.P.A.");
        car2.setName("Lamborghini Urus");
        car2.setPlaca("JAV0275");
        car2.setStatus(VehicleStatusEnum.ACTIVATED);
        car2.setYear(2022);
        carRepository.save(car2);

        CarEntity car3 = new CarEntity();
        car3.setChassi("3BYZZZ637VT220047");
        car3.setColor("red");
        car3.setDeleted(false);
        car3.setManufacturer("Hyundai");
        car3.setName("HB20");
        car3.setPlaca("IAT3901");
        car3.setStatus(VehicleStatusEnum.ACTIVATED);
        car3.setYear(2012);
        carRepository.save(car3);

        CarEntity car4 = new CarEntity();
        car4.setChassi("2ABCZZ937VT220043");
        car4.setColor("black");
        car4.setDeleted(false);
        car4.setManufacturer("Hyundai");
        car4.setName("HB20S");
        car4.setPlaca("HQY2611");
        car4.setStatus(VehicleStatusEnum.ACTIVATED);
        car4.setYear(2013);
        carRepository.save(car4);

        CarEntity car5 = new CarEntity();
        car5.setChassi("3CBATT937VT220021");
        car5.setColor("black");
        car5.setDeleted(false);
        car5.setManufacturer("Hyundai");
        car5.setName("HB20X");
        car5.setPlaca("HQY2412");
        car5.setStatus(VehicleStatusEnum.ACTIVATED);
        car5.setYear(2013);
        carRepository.save(car5);

        CarEntity car6 = new CarEntity();
        car6.setChassi("9TYATT937VT220021");
        car6.setColor("white");
        car6.setDeleted(false);
        car6.setManufacturer("Volkswagen");
        car6.setName("Lamborghini");
        car6.setPlaca("AHG1026");
        car6.setStatus(VehicleStatusEnum.ACTIVATED);
        car6.setYear(1972);
        carRepository.save(car6);

        CarEntity car7 = new CarEntity();
        car7.setChassi("6AAATT937VT220021");
        car7.setColor("black");
        car7.setDeleted(false);
        car7.setManufacturer("Volkswagen");
        car7.setName("Lamborghini Urraco");
        car7.setPlaca("QHG1226");
        car7.setStatus(VehicleStatusEnum.ACTIVATED);
        car7.setYear(2000);
        carRepository.save(car7);

        CarEntity car8 = new CarEntity();
        car8.setChassi("5HTYTT937VT220021");
        car8.setColor("red");
        car8.setDeleted(false);
        car8.setManufacturer("Volkswagen");
        car8.setName("Lamborghini Miura");
        car8.setPlaca("QHG1326");
        car8.setStatus(VehicleStatusEnum.ACTIVATED);
        car8.setYear(1973);
        carRepository.save(car8);
    }
}