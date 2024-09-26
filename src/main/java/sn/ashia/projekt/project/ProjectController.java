package sn.ashia.projekt.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ashia.projekt.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "find projects optionally by manager id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "projects found"
            )
    })
    public ResponseEntity<List<ProjectDTO>> find(
            @RequestParam(required = false, name = "managedBy") Long managerId,
            @ParameterObject Pageable pageable
            ) {
        if (managerId == null) {
            return ResponseEntity.ok(projectService.find(pageable));
        }
        return ResponseEntity.ok(projectService.find(managerId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "find an project by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project not found",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectDTO> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PostMapping
    @Operation(summary = "save an user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "project saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectDTO> save(@Valid @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.save(projectDTO));
    }

    @PutMapping
    @Operation(summary = "update an project")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project not found",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectDTO> update(@Valid @RequestBody ProjectDTO projectDTO) throws EntityNotFoundException {
        return ResponseEntity.ok(projectService.update(projectDTO));
    }
}
