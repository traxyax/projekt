package sn.ashia.projekt.projectrisk;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ashia.projekt.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project-risks")
@Tag(name = "project-risk")
public class ProjectRiskController {
    private final ProjectRiskService projectRiskService;

    @GetMapping("/project/{projectId}")
    @Operation(summary = "find project risks by project id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project risks found"
            )
    })
    public ResponseEntity<List<ProjectRiskDTO>> find(@PathVariable long projectId) {
        return ResponseEntity.ok(projectRiskService.find(projectId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a project risk by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project risk found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project risk not found",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectRiskDTO> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(projectRiskService.findByIdToDTO(id));
    }

    @PostMapping
    @Operation(summary = "save a project risk")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "project risk saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectRiskDTO> save(@RequestBody ProjectRiskDTO projectRiskDTO) throws EntityNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectRiskService.save(projectRiskDTO));
    }

    @PatchMapping
    @Operation(summary = "update a project risk")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "project risk updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project risk not found",
                    content = @Content
            )
    })
    public ResponseEntity<ProjectRiskDTO> update(@RequestBody ProjectRiskDTO projectRiskDTO) throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(projectRiskService.update(projectRiskDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a project risk by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "project risk deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "project risk not found",
                    content = @Content
            )
    })
    public ResponseEntity<Object> delete(@PathVariable long id) throws EntityNotFoundException {
        projectRiskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
