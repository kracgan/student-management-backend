package com.fsd.student.controller;

import com.fsd.student.dto.DepartmentDTO;
import com.fsd.student.entity.Department;
import com.fsd.student.repository.DepartmentRepository;
import com.fsd.student.response.ResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ResponseBean<List<DepartmentDTO>>> getAll() {
        List<DepartmentDTO> dtos = departmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBean.success(dtos, "Departments fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBean<DepartmentDTO>> getById(@PathVariable String id) {
        return departmentRepository.findById(id)
                .map(dept -> ResponseEntity.ok(ResponseBean.success(convertToDTO(dept), "Department found")))
                .orElseGet(() -> ResponseEntity.status(404).body(ResponseBean.error("Department not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseBean<DepartmentDTO>> create(@RequestBody DepartmentDTO dto) {
        Department saved = departmentRepository.save(convertToEntity(dto));
        return ResponseEntity.ok(ResponseBean.success(convertToDTO(saved), "Department created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<DepartmentDTO>> update(@PathVariable String id, @RequestBody DepartmentDTO dto) {
        return departmentRepository.findById(id)
                .map(existing -> {
                    dto.setDeptId(id);
                    Department updated = departmentRepository.save(convertToEntity(dto));
                    return ResponseEntity.ok(ResponseBean.success(convertToDTO(updated), "Department updated successfully"));
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

    private DepartmentDTO convertToDTO(Department dept) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDeptId(dept.getDeptId());
        dto.setDeptName(dept.getDeptName());
        dto.setDeptHead(dept.getDeptHead());
        return dto;
    }

    private Department convertToEntity(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setDeptId(dto.getDeptId());
        dept.setDeptName(dto.getDeptName());
        dept.setDeptHead(dto.getDeptHead());
        return dept;
    }
}
