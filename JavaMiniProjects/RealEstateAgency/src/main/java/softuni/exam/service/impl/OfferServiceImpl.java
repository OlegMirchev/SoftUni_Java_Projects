package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportOffersDto;
import softuni.exam.models.dto.ImportOffersDto;
import softuni.exam.models.dto.OfferDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "offers.xml");

    private OfferRepository offerRepository;
    private AgentRepository agentRepository;
    private ApartmentRepository apartmentRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportOffersDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportOffersDto unmarshal = (ImportOffersDto) unmarshaller.unmarshal(fileReader);

        return unmarshal.getOffers().stream().map(this::validateOffer).collect(Collectors.joining("\n"));
    }

    @Override
    public String exportOffers() {
        List<ExportOffersDto> offers = this.offerRepository.findBestOffersFromDateBase();

        StringBuilder output = new StringBuilder();

        for (ExportOffersDto offer : offers) {
            output.append(String.format("""
                    Agent %s with offer №%d:
                       		-Apartment area: %.2f
                       		--Town: %s
                       		---Price: %.2f$
                    """, offer.getFullName(), offer.getId(), offer.getArea(), offer.getTownName(), offer.getPrice()));
        }

        return output.toString().trim();
    }

    private String validateOffer(OfferDto offerDto) {
        Set<ConstraintViolation<OfferDto>> validateExceptions = this.validator.validate(offerDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid offer";
        }

        Optional<Offer> byOffer = this.offerRepository.findOfferWithPriceAndAgentAndApartmentAndDate(
                offerDto.getPrice(), offerDto.getAgent().getName(), offerDto.getApartment().getId(), offerDto.getPublishedOn());

        Offer offer = this.modelMapper.map(offerDto, Offer.class);
        Optional<Agent> agent = this.agentRepository.findByFirstName(offerDto.getAgent().getName());

        if (byOffer.isPresent() || agent.isEmpty()) {
            return "Invalid offer";
        }

        Optional<Apartment> apartment = this.apartmentRepository.findById(offerDto.getApartment().getId());

        offer.setAgent(agent.get());
        offer.setApartment(apartment.get());

        this.offerRepository.save(offer);

        return offer.toString();
    }
}
