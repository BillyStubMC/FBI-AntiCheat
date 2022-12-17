package fun.billy.fbi.netty;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.util.reflection.SafeReflection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

import java.util.logging.Level;

public class PacketOut
        extends ChannelOutboundHandlerAdapter {
    private final PlayerData playerData;

    @SuppressWarnings("rawtypes")
    public void write(ChannelHandlerContext channelHandlerContext, Object o, ChannelPromise channelPromise) throws Exception {
        if (o instanceof PacketPlayOutEntityTeleport) {
            SafeReflection.setOnGround((PacketPlayOutEntityTeleport) o, false);
        } else if (o instanceof PacketPlayOutEntity) {
            SafeReflection.setOnGround((PacketPlayOutEntity) o, false);
        }
        super.write(channelHandlerContext, o, channelPromise);
        try {
            this.playerData.handle((Packet) o, false);
        } catch (Throwable var) {
            AntiCheatPlugin.getPlugin().getLogger().log(Level.SEVERE, "Error: outgoing packet ", var);
        }
    }

    public PacketOut(PlayerData playerData) {
        this.playerData = playerData;
    }
}

