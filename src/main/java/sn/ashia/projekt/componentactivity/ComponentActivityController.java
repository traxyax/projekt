package sn.ashia.projekt.componentactivity;

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
@RequestMapping("/api/component-activities")
@Tag(name = "component-activity")
public class ComponentActivityController {
    private final ComponentActivityService componentActivityService;

    @GetMapping("/project-components/{projectComponentId}")
    @Operation(summary = "find component activities by component id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "component activities found"
            )
    })
    public ResponseEntity<List<ComponentActivityDTO>> find(@PathVariable long projectComponentId) {
        return ResponseEntity.ok(componentActivityService.find(projectComponentId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "find a component activity by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "component activity found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "component activity not found",
                    content = @Content
            )
    })
    public ResponseEntity<ComponentActivityDTO> findById(@PathVariable long id) throws EntityNotFoundException {
        return ResponseEntity.ok(componentActivityService.findByIdToDTO(id));
    }

    @PostMapping
    @Operation(summary = "save a component activity")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "component activity saved"
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
    public ResponseEntity<ComponentActivityDTO> save(@RequestBody ComponentActivityDTO componentActivityDTO) throws EntityNotFoundException, ConflictException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(componentActivityService.save(componentActivityDTO));
    }

    @PatchMapping
    @Operation(summary = "update a component activity")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "component activity updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "component activity not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "conflict error",
                    content = @Content
            )
    })
    public ResponseEntity<ComponentActivityDTO> update(@RequestBody ComponentActivityDTO componentActivityDTO) throws EntityNotFoundException, IllegalAccessException, ConflictException {
        return ResponseEntity.ok(componentActivityService.update(componentActivityDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a component activity by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "component activity deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "component activity not found",
                    content = @Content
            )
    })
    public ResponseEntity<Object> delete(@PathVariable long id) throws EntityNotFoundException {
        componentActivityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
