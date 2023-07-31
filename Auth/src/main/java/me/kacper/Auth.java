package me.kacper;

import lombok.Getter;
import me.kacper.command.LoginCommand;
import me.kacper.command.RegisterCommand;
import me.kacper.listener.AuthListener;
import me.kacper.manager.AuthManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public class Auth extends JavaPlugin {

    private Data data;
    private AuthManager authManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.data = new Data(getConfig().getString("mongo.uri"), getConfig().getString("mongo.db-name"));
        this.data.getProfileManager().load();

        this.authManager = new AuthManager();

        this.listener(Bukkit.getPluginManager());
        this.command();
    }

    private void listener(PluginManager pluginManager){
        pluginManager.registerEvents(new AuthListener(this),this);
    }

    private void command(){
        Objects.requireNonNull(this.getCommand("login")).setExecutor(new LoginCommand(this));
        Objects.requireNonNull(this.getCommand("register")).setExecutor(new RegisterCommand(this));
    }

    @Override
    public void onDisable() {
        this.data.getProfileManager().save();
    }
}