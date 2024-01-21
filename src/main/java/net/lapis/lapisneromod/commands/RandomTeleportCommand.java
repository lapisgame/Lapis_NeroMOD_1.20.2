package net.lapis.lapisneromod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

import static java.lang.Math.sqrt;

public class RandomTeleportCommand {
    public RandomTeleportCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rtp").executes((command) -> {
            return RandomTeleport(command);
        }));
    }

    private int RandomTeleport(CommandContext<CommandSourceStack> context) throws CommandSyntaxException{
        Player player = context.getSource().getPlayerOrException();

        int x, y, z;

        Random random = new Random();
        // Примерные пределы для телепортации (можно изменить по необходимости)
        int maxDistance = 10000;
        int minDistance = 1000;

        ServerLevel serverLevel  = context.getSource().getLevel();

        x = random.nextInt(maxDistance - minDistance) + minDistance;
        z = random.nextInt(maxDistance - minDistance) + minDistance;

        y = findSaveBlock(x, z, context);

        if (random.nextBoolean()) x *= -1; // Случайный выбор положительного или отрицательного направления
        if (random.nextBoolean()) z *= -1;

        BlockPos pos = new BlockPos(x,y,z);

        long distance = Math.round(Math.sqrt(Math.pow((player.getX() - x), 2) + Math.pow((player.getZ() - z), 2)));

        MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.SLOW_FALLING, 5 * 20, 0, false, false);
        player.addEffect(effectInstance);

        player.teleportTo(x + 0.5, y + 5, z + 0.5);

        player.displayClientMessage(Component.literal("Teleportation Complete    Distance Teleportation " + distance), true);

        return 1;
    }

    private int findSaveBlock(int x, int z, CommandContext<CommandSourceStack> context) throws CommandSyntaxException{
        int y = 200;

        ServerLevel serverLevel = context.getSource().getLevel();
        Player player = context.getSource().getPlayerOrException();

        BlockPos pos = new BlockPos(x,y,z);

        while (serverLevel.getBlockState(pos).isAir()){
            y--;
            pos = new BlockPos(x,y,z);
        }

        return y+1;
    }

}
