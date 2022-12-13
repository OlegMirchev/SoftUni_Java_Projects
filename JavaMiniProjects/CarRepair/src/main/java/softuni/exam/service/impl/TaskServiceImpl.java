package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportTasksDto;
import softuni.exam.models.dto.ImportTasksDto;
import softuni.exam.models.dto.TaskDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;

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
public class TaskServiceImpl implements TaskService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "tasks.xml");

    private TaskRepository taskRepository;
    private CarRepository carRepository;
    private MechanicRepository mechanicRepository;
    private PartRepository partRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CarRepository carRepository, MechanicRepository mechanicRepository, PartRepository partRepository) {
        this.taskRepository = taskRepository;
        this.carRepository = carRepository;
        this.mechanicRepository = mechanicRepository;
        this.partRepository = partRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportTasksDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportTasksDto importTasks = (ImportTasksDto) unmarshaller.unmarshal(fileReader);

        return importTasks.getTasks().stream().map(this::validateTask).collect(Collectors.joining("\n"));
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<ExportTasksDto> tasks = this.taskRepository.findAllBestTasksWithTypeCoupe();

        StringBuilder output = new StringBuilder();

        for (ExportTasksDto task : tasks) {
            output.append(String.format("""
                    "Car %s %s with %dkm
                         -Mechanic: %s %s - task №%d:
                         --Engine: %.1f
                         ---Price: %.2f$
                    """, task.getCarMake(), task.getCarModel(), task.getKilometers(), task.getFirstName(), task.getLastName(),
                     task.getId(), task.getEngine(), task.getPrice()));
        }

        return output.toString().trim();
    }

    private String validateTask(TaskDto taskDto) {
        Set<ConstraintViolation<TaskDto>> validateExceptions = this.validator.validate(taskDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid task";
        }

        Optional<Task> byTask = this.taskRepository.findByCarMechanicPart(taskDto.getCar().getId(), taskDto.getMechanic().getFirstName(),
                 taskDto.getPart().getId());

        if (byTask.isPresent()) {
            return "Invalid task";
        }

        Task task = this.modelMapper.map(taskDto, Task.class);
        Optional<Car> car = this.carRepository.findById(task.getCar().getId());
        Optional<Mechanic> mechanic = this.mechanicRepository.findByFirstName(taskDto.getMechanic().getFirstName());
        Optional<Part> part = this.partRepository.findById(taskDto.getPart().getId());

        if (mechanic.isEmpty()) {
            return "Invalid task";
        }

        task.setCar(car.get());
        task.setMechanic(mechanic.get());
        task.setPart(part.get());

        this.taskRepository.save(task);

        return task.toString();
    }
}
