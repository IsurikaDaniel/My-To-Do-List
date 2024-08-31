package modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String id;
    private String description;
    private LocalDate date;
    private int completed ;


    public User(String text, LocalDate value) {
        this.description = text;
        this.date=value;
    }


}
