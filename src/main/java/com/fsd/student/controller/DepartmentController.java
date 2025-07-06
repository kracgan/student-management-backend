package com.fsd.student.controller;

import com.fsd.student.entity.Department;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<Department>>> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(ResponseBean.success(departments, "Departments fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<Department>> getById(@PathVariable String id) {
        return departmentRepository.findById(id)
                .map(dept -> ResponseEntity.ok(ResponseBean.success(dept, "Department found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Department not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<Department>> create(@RequestBody Department dept) {
        Department saved = departmentRepository.save(dept);
        return ResponseEntity.ok(ResponseBean.success(saved, "Department created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<Department>> update(@PathVariable String id, @RequestBody Department dept) {
        return departmentRepository.findById(id)
                .map(existing -> {
                    dept.setDeptId(id);
                    Department updated = departmentRepository.save(dept);
                    return ResponseEntity.ok(ResponseBean.success(updated, "Department updated successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Department not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<String>> delete(@PathVariable String id) {
        return departmentRepository.findById(id)
                .map(existing -> {
                    departmentRepository.deleteById(id);
                    return ResponseEntity.ok(ResponseBean.success(id, "Department deleted successfully"));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Department not found")));
    }
}
