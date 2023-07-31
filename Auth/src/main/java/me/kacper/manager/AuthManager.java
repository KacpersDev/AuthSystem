package me.kacper.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthManager {

    public List<UUID> loginCache = new ArrayList<>();
    public List<UUID> registerCache = new ArrayList<>();
}
