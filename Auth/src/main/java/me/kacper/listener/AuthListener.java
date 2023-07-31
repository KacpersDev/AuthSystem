package me.kacper.listener;

import me.kacper.Auth;
import me.kacper.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class AuthListener implements Listener {

    private final Auth auth;

    public AuthListener(Auth auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!this.auth.getData().getProfileManager().profileCache.containsKey(event.getPlayer().getUniqueId())) {
            this.auth.getAuthManager().registerCache.add(event.getPlayer().getUniqueId());
            event.getPlayer().sendTitle(Color.translate(this.auth.getConfig().getString("messages.title-header")),
                    Color.translate(this.auth.getConfig().getString("messages.register")));
        } else {
            this.auth.getAuthManager().loginCache.add(event.getPlayer().getUniqueId());
            event.getPlayer().sendTitle(Color.translate(this.auth.getConfig().getString("messages.title-header")),
                    Color.translate(this.auth.getConfig().getString("messages.login")));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            event.getPlayer().hidePlayer(player);
            player.hidePlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.auth.getAuthManager().loginCache.contains(event.getPlayer().getUniqueId())) this.auth.getAuthManager().loginCache.remove(event.getPlayer().getUniqueId());
        if (this.auth.getAuthManager().registerCache.contains(event.getPlayer().getUniqueId())) this.auth.getAuthManager().registerCache.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (this.auth.getAuthManager().loginCache.contains(event.getPlayer().getUniqueId()) || this.auth.getAuthManager().registerCache
                .contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (this.auth.getAuthManager().loginCache.contains(event.getPlayer().getUniqueId()) || this.auth.getAuthManager().registerCache
                .contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (this.auth.getAuthManager().loginCache.contains(event.getPlayer().getUniqueId()) || this.auth.getAuthManager().registerCache
                .contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
            event.setCancelled(true);
        }
    }
}
