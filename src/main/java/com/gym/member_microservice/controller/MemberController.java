package com.gym.member_microservice.controller;

import com.gym.member_microservice.model.Member;
import com.gym.member_microservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
@Tag(name = "Members", description = "API for gym member management")
@SecurityRequirement(name = "bearer-key")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    @Operation(summary = "Register new member", description = "Registers a new member in the gym")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error in input data")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        try {
            Member registeredMember = memberService.registerMember(member);
            return ResponseEntity.ok(registeredMember);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all members", description = "Returns a list of all registered members")
    @ApiResponse(responseCode = "200", description = "List of members retrieved successfully")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COACH')")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Returns a specific member by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COACH') or hasRole('ROLE_MEMBER')")
    public ResponseEntity<Member> getMemberById(@Parameter(description = "Member ID") @PathVariable Long id) {
        Optional<Member> member = memberService.getMemberById(id);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get member by email", description = "Returns a specific member by its email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COACH') or hasRole('ROLE_MEMBER')")
    public ResponseEntity<Member> getMemberByEmail(
            @Parameter(description = "Member email") @PathVariable String email) {
        Optional<Member> member = memberService.getMemberByEmail(email);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member", description = "Updates the data of an existing member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
    public ResponseEntity<Member> updateMember(@Parameter(description = "Member ID") @PathVariable Long id,
            @RequestBody Member memberDetails) {
        try {
            Member updatedMember = memberService.updateMember(id, memberDetails);
            return ResponseEntity.ok(updatedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete member", description = "Deletes a member from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMember(@Parameter(description = "Member ID") @PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }
}
