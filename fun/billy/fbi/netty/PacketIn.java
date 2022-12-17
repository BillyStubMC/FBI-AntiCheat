package fun.billy.fbi.netty;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import net.minecraft.server.v1_8_R3.Packet;

import java.util.logging.Level;

@AllArgsConstructor
public class PacketIn
        extends ChannelInboundHandlerAdapter {
    private final PlayerData playerData;

    @SuppressWarnings("rawtypes")
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        super.channelRead(channelHandlerContext, o);
        try {
            this.playerData.handle((Packet) o, true);
        } catch (Throwable var) {
            AntiCheatPlugin.getPlugin().getLogger().log(Level.SEVERE, "Error: incoming packet ", var);
        }
    }
}

