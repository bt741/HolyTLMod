package be.breroeluean;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HolyTLMod implements ModInitializer {
    public static final String MOD_ID = "HolyTLMod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Send your coordinates in chat",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "HolyTLMod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                if (client.player != null) {
                    sendCoordinates(client);
                }
            }
        });
    }

    private void sendCoordinates(MinecraftClient client) {
        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();
        String message = String.format("[x:%d, y:%d, z:%d]",
                Math.round(x), Math.round(y), Math.round(z));
        client.player.networkHandler.sendChatMessage(message);
    }
}