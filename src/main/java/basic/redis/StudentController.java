package basic.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  private final StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @PostMapping("/students")
  public void createStudent(@RequestBody StudentRequest request) {
    Student student = new Student(request.id(), request.name());
    studentRepository.save(student);
  }

  @GetMapping("/students/{id}")
  public String getStudent(@PathVariable Long id) {
    Student retrievedStudent = studentRepository.findById(id).orElseThrow();
    return retrievedStudent.toString();
  }
}