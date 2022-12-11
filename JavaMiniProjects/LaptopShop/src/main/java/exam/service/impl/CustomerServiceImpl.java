package exam.service.impl;

import com.google.gson.*;
import exam.model.dto.ImportCustomersDto;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "customers.json");

    private CustomerRepository customerRepository;
    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        })
                .create();
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importCustomers() throws IOException {
        String customerPath = readCustomersFileContent();

        ImportCustomersDto[] importCustomers = this.gson.fromJson(customerPath, ImportCustomersDto[].class);

        return Arrays.stream(importCustomers).map(this::validateCustomerDto).collect(Collectors.joining("\n"));
    }

    private String validateCustomerDto(ImportCustomersDto customersDto) {
        Set<ConstraintViolation<ImportCustomersDto>> validateExceptions = this.validator.validate(customersDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid Customer";
        }

        Optional<Customer> byCustomer = this.customerRepository.findByFirstNameAndLastName(customersDto.getFirstName(), customersDto.getLastName());

        if (byCustomer.isPresent()) {
            return "Invalid Customer";
        }

        Customer customer = this.modelMapper.map(customersDto, Customer.class);
        Optional<Town> town = this.townRepository.findByName(customersDto.getTown().getName());

        customer.setTown(town.get());

        this.customerRepository.save(customer);

        return customer.toString();
    }
}
