package sn.ashia.projekt.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "find users optionally by roles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "users found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            )
    })
    public ResponseEntity<List<UserDTO>> find(
            @RequestParam(required = false) Set<UserRole> roles
    ) {
        return ResponseEntity.ok(userService.find(roles));
    }

    @GetMapping("/{id}")
    @Operation(summary = "find an user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "save an user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "user saved"
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
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) throws ConflictException {
        userDTO = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping
    @Operation(summary = "update an user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "validation errors",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "conflict error",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) throws EntityNotFoundException, ConflictException {
        userDTO = userService.update(userDTO);
        return ResponseEntity.ok(userDTO);
    }
}
