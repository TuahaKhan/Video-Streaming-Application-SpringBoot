package com.stream.app.spring.stream.backend.payload;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomMessage {
    private String message;
    private boolean success = false;

}
