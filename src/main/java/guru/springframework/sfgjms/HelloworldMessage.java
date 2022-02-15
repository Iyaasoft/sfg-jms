package guru.springframework.sfgjms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HelloworldMessage implements Serializable  {

    static final long serialVersionUID = -4031136570876222432L;
    UUID ID;
    String message;
}
