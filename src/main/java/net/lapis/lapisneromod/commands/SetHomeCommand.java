package net.lapis.lapisneromod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.lapis.lapisneromod.LapisNeroMOD;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SetHomeCommand {
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sethome").executes((command) -> {
            return setHome(command);
        }));
    }

    private int setHome(CommandContext<CommandSourceStack> context) throws CommandSyntaxException{
        Player player = context.getSource().getPlayerOrException();
        BlockPos playerPos = player.blockPosition();

        String pos = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        player.getPersistentData().putIntArray(LapisNeroMOD.MOD_ID + player.getId() + "homepos",
                new int[]{playerPos.getX(), playerPos.getY(), playerPos.getZ()});

        player.displayClientMessage(Component.literal("Set home at " + pos), true);

        return 1;
    }
}
