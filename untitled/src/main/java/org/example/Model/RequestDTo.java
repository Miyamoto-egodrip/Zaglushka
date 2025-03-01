package org.example.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDTo {
    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;
}
