package sn.ashia.projekt.projectcomponent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project-components")
@Tag(name = "project-component")
public class ProjectComponentController {
    private final ProjectComponentService projectComponentService;

    @GetMapping("/project/{projectId}")
    @Operation(summary = "find project components by project id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project components found"
            )
    })
    public ResponseEntity<List<ProjectComponentDTO>> find(@PathVariable long projectId) {
        return ResponseEntity.ok(projectComponentService.find(projectId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a project component by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project component found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project component not found",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectComponentDTO> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(projectComponentService.findByIdToDTO(id));
    }

    @PostMapping
    @Operation(summary = "save a project component")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "project component saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "conflict error",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectComponentDTO> save(@RequestBody ProjectComponentDTO projectComponentDTO) throws EntityNotFoundException, ConflictException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectComponentService.save(projectComponentDTO));
    }

    @PatchMapping
    @Operation(summary = "update a project component")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project component updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project component not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "conflict error",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectComponentDTO> update(@RequestBody ProjectComponentDTO projectComponentDTO) throws EntityNotFoundException, IllegalAccessException, ConflictException {
        return ResponseEntity.ok(projectComponentService.update(projectComponentDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a project component by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "project component deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project component not found",
                    content = @Content
            )
    })
    public ResponseEntity<Object> delete(@PathVariable long id) throws EntityNotFoundException {
        projectComponentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
