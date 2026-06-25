package io.github.akashiikun.creaddon;

import io.github.akashiikun.creaddon.content.liquidfuel.core.LiquidBurnerFuelJsonLoader;
import io.github.akashiikun.creaddon.content.liquidfuel.eventhandlers.ModEventHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreaddonMod implements ModInitializer {
	public static final String MOD_ID = "creaddon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier asId(String id) {
		return Identifier.fromNamespaceAndPath(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Loading... Wait what? Are you creating in 26.1.2? Crazy");
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(
				LiquidBurnerFuelJsonLoader.IDENTIFIER,
				LiquidBurnerFuelJsonLoader.INSTANCE
		);
		ModEventHandler.registerApiLookups();
	}
}