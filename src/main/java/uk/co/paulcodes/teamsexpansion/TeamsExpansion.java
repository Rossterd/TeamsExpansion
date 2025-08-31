package uk.co.paulcodes.teamsexpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TeamsExpansion extends PlaceholderExpansion {

    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

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
        return "0.2-SNAPSHOT";
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return Arrays.asList(
                "%team_color%",
                "%team_count%",
                "%team_displayname%",
                "%team_name%",
                "%team_prefix%",
                "%team_suffix%",
                "%team_color_<team>%",
                "%team_count_<team>%",
                "%team_displayname_<team>%",
                "%team_name_<team>%",
                "%team_prefix_<team>%",
                "%team_suffix_<team>%"
        );
    }

    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        String[] args = identifier.toLowerCase().split("_");

        Team team = null;
        if(args.length == 1) {
            team = scoreboard.getEntryTeam(player.getName());
        } else if(args.length == 2) {
            team = scoreboard.getTeam(args[1]);
        }

        return handle(team, args[0]);
    }

    // Return empty string if valid placeholder, but team or specific value does not exist.
    // Return null if placeholder is invalid. This makes the placeholder return itself.
    String handle(Team team, String placeholder) {
        try {
            switch (placeholder) {
                case "color":
                    if (team == null) return "";
                    // Somewhat stuck to using deprecated method as this returns a legacy color character.
                    // A modern solution would be returning a MiniMessage tag, but that would probably break a lot
                    // of plugins.
                    return team.getColor().toString();
                case "prefix":
                    if (team == null) return "";
                    return team.prefix().toString();
                case "suffix":
                    if (team == null) return "";
                    return team.suffix().toString();
                case "name":
                    if (team == null) return "";
                    return team.getName();
                case "displayname":
                    if (team == null) return "";
                    return team.displayName().toString();
                case "count":
                    if (team == null) return "";
                    return String.valueOf(team.getSize());
                default:
                    return null;
            }
        } catch(IllegalStateException e) {
            // Value not set for team
            return "";
        }
    }
}
