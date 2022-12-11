package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dto.ExportLaptopsDto;
import exam.model.dto.ImportLaptopsDto;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "laptops.json");

    private LaptopRepository laptopRepository;
    private ShopRepository shopRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importLaptops() throws IOException {
        String laptopsPath = readLaptopsFileContent();

        ImportLaptopsDto[] importLaptops = this.gson.fromJson(laptopsPath, ImportLaptopsDto[].class);

        return Arrays.stream(importLaptops).map(this::validateLaptop).collect(Collectors.joining("\n"));
    }

    @Override
    public String exportBestLaptops() {
        List<ExportLaptopsDto> laptops = this.laptopRepository.findBestLaptops();

        StringBuilder output = new StringBuilder();

        for (ExportLaptopsDto laptop : laptops) {
            output.append(String.format("""
                    Laptop - %s
                     *Cpu speed - %.2f
                     **Ram - %d
                     ***Storage - %d
                     ****Price - %.2f
                     #Shop name - %s
                     ##Town - %s
                    """, laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage(), laptop.getPrice(),
                     laptop.getShopName(), laptop.getTownName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    private String validateLaptop(ImportLaptopsDto laptopsDto) {
        Set<ConstraintViolation<ImportLaptopsDto>> validateExceptions = this.validator.validate(laptopsDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid Laptop";
        }

        Optional<Laptop> byLaptop = this.laptopRepository.findShopName(laptopsDto.getShop().getName());

        if (byLaptop.isPresent()) {
            return "Invalid Laptop";
        }

        Laptop laptop = this.modelMapper.map(laptopsDto, Laptop.class);
        Optional<Shop> shop = this.shopRepository.findByName(laptop.getShop().getName());

        laptop.setShop(shop.get());

        this.laptopRepository.save(laptop);

        return laptop.toString();
    }
}
