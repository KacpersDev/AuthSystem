package me.kacper;

import lombok.Getter;
import me.kacper.mongo.Mongo;
import me.kacper.profiles.manager.ProfileManager;

@Getter
public class Data {

    private final Mongo mongo;
    private final ProfileManager profileManager;

    public Data(String uri, String db){
        this.mongo = new Mongo(uri, db);
        this.profileManager = new ProfileManager(this);
    }
}