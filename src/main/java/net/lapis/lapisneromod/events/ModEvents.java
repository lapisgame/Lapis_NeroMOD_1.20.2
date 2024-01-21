package net.lapis.lapisneromod.events;

import net.lapis.lapisneromod.LapisNeroMOD;
import net.lapis.lapisneromod.commands.RandomTeleportCommand;
import net.lapis.lapisneromod.commands.SetHomeCommand;
import net.lapis.lapisneromod.commands.ReturnHomeCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = LapisNeroMOD.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event){
        new RandomTeleportCommand(event.getDispatcher());
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
