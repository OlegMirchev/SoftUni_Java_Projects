package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ExportPicturesWithHigherSizeDto;
import softuni.exam.instagraphlite.models.dto.ImportPicturesDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

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
public class PictureServiceImpl implements PictureService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "pictures.json");

    private PictureRepository pictureRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importPictures() throws IOException {
        String picturesPath = readFromFileContent();

        ImportPicturesDto[] importPictures = this.gson.fromJson(picturesPath, ImportPicturesDto[].class);

        return Arrays.stream(importPictures).map(this::validatePicture).collect(Collectors.joining("\n"));
    }

    @Override
    public String exportPictures() {
        List<ExportPicturesWithHigherSizeDto> pictures = this.pictureRepository.findAllPicturesWithHigherSizeThan30000();

        StringBuilder output = new StringBuilder();

        for (ExportPicturesWithHigherSizeDto picture : pictures) {
            output.append(String.format("""
                    %.2f – %s
                    """, picture.getSize(), picture.getPath()));
        }

        return output.toString().trim();
    }

    private String validatePicture(ImportPicturesDto picturesDto) {
        Set<ConstraintViolation<ImportPicturesDto>> validateExceptions = this.validator.validate(picturesDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid Picture";
        }

        Optional<Picture> byPicture = this.pictureRepository.findByPath(picturesDto.getPath());

        if (byPicture.isPresent()) {
            return "Invalid Picture";
        }

        Picture picture = this.modelMapper.map(picturesDto, Picture.class);

        this.pictureRepository.save(picture);

        return picture.toString();
    }
}
