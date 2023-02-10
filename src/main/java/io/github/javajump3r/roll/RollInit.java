package io.github.javajump3r.roll;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.netty.util.internal.StringUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RollInit implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("roll")
					.then(ClientCommandManager.argument("maxValue", IntegerArgumentType.integer()).executes(this::roll)));
		}));
	}
	private static String[][] numbers =
			{
					{
							"_■■■_",
							"■__■■",
							"■_■_■",
							"■■__■",
							"_■■■_",
					},
					{
							"_■",
							"■■",
							"_■",
							"_■",
							"_■",
					},
					{
							"_■■■_",
							"■___■",
							"____■",
							"_■■__",
							"■■■■■",
					},
					{
							"_■■■_",
							"■___■",
							"___■_",
							"■___■",
							"_■■■_",
					},
					{
							"■___■",
							"■___■",
							"■■■■■",
							"____■",
							"____■",
					},
					{
							"■■■■■",
							"■____",
							"_■■■_",
							"____■",
							"■■■■_",
					},
					{
							"■■■■■",
							"■____",
							"■■■■■",
							"■___■",
							"■■■■■",
					},
					{
							"■■■■■",
							"■___■",
							"___■_",
							"__■__",
							"__■__",
					},
					{
							"_■■■_",
							"■___■",
							"_■■■_",
							"■___■",
							"_■■■_",
					},
					{
							"_■■■_",
							"■___■",
							"_■■■■",
							"____■",
							"■■■■_",
					},

			};
	private int roll(CommandContext<FabricClientCommandSource> commandContext){
		int maxValue = commandContext.getArgument("maxValue",Integer.class);
		var num = new Random().nextInt(maxValue)+1;
		var rand = Integer.toString(num);
		var strings = new String[5];
		for(char c : rand.toCharArray()){
			int n = Integer.parseInt(((Character)c).toString());
			for(int i=0;i<5;i++){
				strings[i]+=numbers[n][i]+" ";
			}
		}
		MinecraftClient.getInstance().getNetworkHandler().sendChatMessage("");
		for(int i=0;i<5;i++){
			MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(strings[i].replace("null",""));
		}
		MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(String.format("%s: %d/%d", I18n.translate("jjroll.result"),num,maxValue));
		return 0;
	}
}
