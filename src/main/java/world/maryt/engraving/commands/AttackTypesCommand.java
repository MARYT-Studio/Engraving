package world.maryt.engraving.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

import static mods.flammpfeil.slashblade.ability.StylishRankManager.AttackTypes.types;

public class AttackTypesCommand extends CraftTweakerCommand {
    public AttackTypesCommand() {
        super("attackTypes");
    }

    @Override
    protected void init() {
        setDescription(
                getClickableCommandText(TextFormatting.DARK_GREEN + "/ct attackTypes", "/ct attackTypes", true),
                getNormalMessage(TextFormatting.DARK_AQUA + "Display a list of all SlashBlade attack types.")
        );
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand("SlashBlade Attack Types:");
        for (String key : types.keySet()) {
            CraftTweakerAPI.logCommand(String.format("%s - StylishRank Factor: %f", key, types.get(key)));
        }
        sender.sendMessage(getLinkToCraftTweakerLog("List of SlashBlade attack types generated", sender));
    }
}
