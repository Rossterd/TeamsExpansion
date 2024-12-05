package uk.co.paulcodes.teamsexpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamsExpansion extends PlaceholderExpansion {

    private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "TeamsExpansion";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "team";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PaulCodesUK";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1-SNAPSHOT";
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        List<String> list = new ArrayList<>();
        list.add(plc("color"));
        list.add(plc("prefix"));
        list.add(plc("suffix"));
        list.add(plc("name"));
        list.add(plc("count"));
        return super.getPlaceholders();
    }

    private static String getPlayerTeam(String player) {
        if (scoreboard.getEntryTeam(player) != null) {
            return scoreboard.getEntryTeam(player).getName();
        } else {
            return "";
        }
    }

    private static ChatColor getTeamColor(String team) {
        if (scoreboard.getTeam(team) != null) {
            return scoreboard.getTeam(team).getColor();
        } else {
            return null;
        }
    }

    private static String getTeamPrefix(String team) {
        if (scoreboard.getTeam(team) != null) {
            return scoreboard.getTeam(team).getPrefix();
        } else {
            return "";
        }
    }

    private static String getTeamSuffix(String team) {
        if (scoreboard.getTeam(team) != null) {
            return scoreboard.getTeam(team).getSuffix();
        } else {
            return "";
        }
    }

    private static String getDisplayName(String team) {
        if (scoreboard.getTeam(team) != null) {
            return scoreboard.getTeam(team).getDisplayName();
        } else {
            return "";
        }
    }

    private static int getTeamCount(String team) {
        if (scoreboard.getTeam(team) != null) {
            return scoreboard.getTeam(team).getSize();
        } else {
            return 0;
        }
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        if (getPlayerTeam(player.getName()).isEmpty()) {
            if (identifier.contains("count") && identifier.contains("team")) {
                String teamName = identifier.split("_")[2];
                if (teamName.isEmpty()) {
                    return "0";
                } else {
                    return getTeamCount(teamName) + "";
                }
            } else {
                return player.getName();
            }
        } else {
            if (identifier.contains("color")) {
                return getTeamColor(getPlayerTeam(player.getName())) + "";
            } else if (identifier.contains("prefix")) {
                return getTeamPrefix(getPlayerTeam(player.getName())) + "";
            } else if (identifier.contains("suffix")) {
                return getTeamSuffix(getPlayerTeam(player.getName())) + "";
            } else if (identifier.contains("name")) {
                return getPlayerTeam(player.getName());
            } else if (identifier.contains("count")) {
                return getTeamCount(getPlayerTeam(player.getName())) + "";
            }
        }
        return null;
    }

    private String plc(String str) {
        return "%" + getIdentifier() + "_" + str + "%";
    }
}
