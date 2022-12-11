package exam.service.impl;

import exam.model.dto.ImportShopsDto;
import exam.model.dto.ShopDto;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "shops.xml");

    private ShopRepository shopRepository;
    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportShopsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportShopsDto importShops = (ImportShopsDto) unmarshaller.unmarshal(fileReader);

        return importShops.getShops().stream().map(this::validateShop).collect(Collectors.joining("\n"));
    }

    private String validateShop(ShopDto shopDto) {
        Set<ConstraintViolation<ShopDto>> validateExceptions = this.validator.validate(shopDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid shop";
        }

        Optional<Shop> byShop = this.shopRepository.findByName(shopDto.getName());

        if (byShop.isPresent()) {
            return "Invalid shop";
        }

        Shop shop = this.modelMapper.map(shopDto, Shop.class);
        Optional<Town> town = this.townRepository.findByName(shopDto.getTown().getName());

        shop.setTown(town.get());

        this.shopRepository.save(shop);

        return shop.toString();
    }
}
