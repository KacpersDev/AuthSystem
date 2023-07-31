package me.kacper.command;

import me.kacper.Auth;
import me.kacper.profiles.Profile;
import me.kacper.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private final Auth auth;

    public RegisterCommand(Auth auth) {
        this.auth = auth;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 0) {
            usage(player);
            return false;
        } else {
            String password = args[0];
            if (args.length == 1) {
                usage(player);
                return false;
            } else {
                String matchPassword = args[1];

                if (!password.equalsIgnoreCase(matchPassword)) {
                    player.sendMessage(Color.translate(this.auth.getConfig().getString("register-failed")));
                    return false;
                }
                this.auth.getAuthManager().registerCache.remove(player.getUniqueId());
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.showPlayer(player);
                    player.showPlayer(p);
                }
                this.auth.getData().getProfileManager().profileCache.put(player.getUniqueId(), new Profile(player.getUniqueId().toString(), password));
                player.sendMessage(Color.translate(this.auth.getConfig().getString("messages.registered")));
            }
        }

        return true;
    }

    private void usage(Player player) {
        player.sendMessage(Color.translate(this.auth.getConfig().getString("messages.register-usage")));
    }
}
