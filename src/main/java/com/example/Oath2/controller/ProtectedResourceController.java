package com.example.Oath2.controller;

@RestController
@RequestMapping("/api/protected")
public class ProtectedResourceController {

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(JwtAuthenticationToken token) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", token.getName());
        userInfo.put("scopes", token.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList()));
        userInfo.put("claims", token.getTokenAttributes());
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/data")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public ResponseEntity<Map<String, String>> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "This is protected data that requires 'read' scope");
        data.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/data")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<Map<String, String>> createData(@RequestBody Map<String, String> input) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Data created successfully with write scope");
        response.put("input", input.toString());
        response.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(response);
    }
}

