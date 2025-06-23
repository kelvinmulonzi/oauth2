package com.example.Oath2.dto;

// Create a simple DTO class
public class ErrorResponse {
    private String message;
    private String error;
    private long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.error = "Bad Request";
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}