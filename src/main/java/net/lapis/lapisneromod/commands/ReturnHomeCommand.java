package net.lapis.lapisneromod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.lapis.lapisneromod.LapisNeroMOD;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class ReturnHomeCommand {
    public ReturnHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").executes((command) -> {
            return Home(command);
        }));
    }

    private int Home(CommandContext<CommandSourceStack> context) throws CommandSyntaxException{
        Player player = context.getSource().getPlayerOrException();

        boolean hasHomepos = player.getPersistentData()
                .getIntArray(LapisNeroMOD.MOD_ID + player.getId() + player.getScoreboardName() + "homepos").length != 0;

        if (hasHomepos){
            int[] playerPos = player.getPersistentData().getIntArray(LapisNeroMOD.MOD_ID + player.getId() + player.getScoreboardName() + "homepos");
            player.moveTo( playerPos[0], playerPos[1], playerPos[2]);

            player.displayClientMessage(Component.literal("Return Home!"), true);
            return 1;
        }
        else{
            player.displayClientMessage(Component.literal("No Home Position!"), true);
            return -1;
        }
    }
}
