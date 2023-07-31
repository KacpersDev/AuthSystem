package me.kacper.profiles;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class Profile {

    private String uuid;
    private String password;

    public Profile(String uuid, String password) {
        this.uuid = uuid;
        this.password = password;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("uuid", this.uuid);
        document.put("password", this.password);
        return document;
    }
}
