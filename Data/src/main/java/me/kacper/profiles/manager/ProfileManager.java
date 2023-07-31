package me.kacper.profiles.manager;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import me.kacper.Data;
import me.kacper.profiles.Profile;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileManager {

    private final Data data;
    public final HashMap<UUID, Profile> profileCache = new HashMap<>();

    public ProfileManager(Data data) {
        this.data = data;
    }

    public void load() {
        FindIterable<Document> findIterable = this.data.getMongo().getProfiles().find();
        try (MongoCursor<Document> cursor = findIterable.iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                profileCache.put(UUID.fromString(document.getString("uuid")), new Profile(document.getString("uuid"), document.getString("password")));
            }
        }
    }

    public void save() {
        for (Map.Entry<UUID, Profile> profiles : profileCache.entrySet()) {
            this.data.getMongo().getProfiles().replaceOne(Filters.eq("uuid", String.valueOf(profiles.getKey())), profiles.getValue().toDocument(), new UpdateOptions().upsert(true));
        }
    }
}
