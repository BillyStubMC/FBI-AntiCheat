package fun.billy.fbi.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.check.type.*;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import fun.billy.fbi.util.Cuboid;
import fun.billy.fbi.util.CustomLocation;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.reflection.SafeReflection;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.MathUtils;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class PlayerData {
    private Player player;
    private EntityPlayer entityPlayer;
    private final Map<String, String> forgeMods = new HashMap<String, String>();
    private Queue<PlayerLocation> recentMoveList = new ConcurrentLinkedQueue<>(), teleportList = new ConcurrentLinkedQueue<>();
    private Queue<BiConsumer<Integer, Double>> pingQueue = new ConcurrentLinkedQueue<>();
    private Map<Integer, Deque<PlayerLocation>> recentMoveMap = new ConcurrentHashMap<>();
    private List<PacketCheck> packetChecks;
    private List<MovementCheck> movementChecks;
    private List<AimCheck> aimChecks;
    private List<ClientCheck> clientChecks;
    private List<String> transactionID = new ArrayList<>();
    private Map<Integer, Long> keepAliveMap = new ConcurrentHashMap<>();
    private PlayerLocation location, lastLocation, lastLastLocation;
    private CustomLocation lastMovePacket;
    private PlayerData lastAttacked;

    //double
    private double version, deltaY, lastDeltaY, lastVelY, lastVelY2, velY, velX, velZ;
    private double[] reach1;

    //int
    private int banticks, lastAttackedId, ping, lastPing, averagePing,
            packets = 0, lastAttackTicks = 600, kbticks = 0, teleportTicks = 0, velocityTicks = 0, verticalVelocityTicks = -20, horizontalVelocityTicks = 0, totalTicks = 0, steerTicks = 0;
    public int[] cps1;

    //long
    private long lastFlying, lastDelayed, lastFast,
            lastPosition = 0L, packet = 0;

    //boolean
    private boolean onGround, inventoryOpen, spawnedIn, alerts, debug, place, crashing,
            receivedKeepAlive = false, digging = false, abortedDigging = false, stoppedDigging = false, banned = false, enabled = true;
    private Boolean sprinting = null, sneaking = null;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public PlayerData(org.bukkit.entity.Player player, List<Check> checks) {
        this.player = player;
        this.entityPlayer = ((CraftPlayer) player).getHandle();
        this.packetChecks = ImmutableList.copyOf((Collection) checks.stream().filter(check -> check instanceof PacketCheck).map(check -> (PacketCheck) check).collect(Collectors.toList()));
        this.movementChecks = ImmutableList.copyOf((Collection) checks.stream().filter(check -> check instanceof MovementCheck).map(check -> (MovementCheck) check).collect(Collectors.toList()));
        this.aimChecks = ImmutableList.copyOf((Collection) checks.stream().filter(check -> check instanceof AimCheck).map(check -> (AimCheck) check).collect(Collectors.toList()));
        this.clientChecks = ImmutableList.copyOf((Collection) checks.stream().filter(check -> check instanceof ClientCheck).map(check -> (ClientCheck) check).collect(Collectors.toList()));
        this.location = new PlayerLocation(System.currentTimeMillis(), this.totalTicks, this.entityPlayer.locX, this.entityPlayer.locY, this.entityPlayer.locZ, this.entityPlayer.yaw, this.entityPlayer.pitch, this.entityPlayer.onGround);
        this.lastLocation = this.location.clone();
        this.lastLastLocation = this.location.clone();
        this.alerts = player.hasMetadata("FBI_ALERTS");
        this.debug = player.hasMetadata("FBI_DEBUG");
        this.cps1 = new int[5];
        this.reach1 = new double[5];
    }

    @EventHandler
    public void handle(Event event) {
        if (event instanceof PlayerRespawnEvent) this.inventoryOpen = false;
    }

    @SuppressWarnings({"rawtypes"})
    public void handle(Packet packet, boolean serverBound) {
        if (this.enabled) {
            if (serverBound) {
                long timestamp = System.currentTimeMillis();
                if (packet instanceof PacketPlayInFlying) {
                    int lastPing;
                    PlayerLocation teleport;
                    PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying) packet;
                    CustomLocation customLocation = new CustomLocation(packetPlayInFlying.a(), packetPlayInFlying.b(), packetPlayInFlying.c(), packetPlayInFlying.d(), packetPlayInFlying.e());
                    CustomLocation lastLocation5 = this.getLastMovePacket();
                    if (lastLocation5 != null) {
                        if (!packetPlayInFlying.g()) {
                            customLocation.setX(lastLocation5.getX());
                            customLocation.setY(lastLocation5.getY());
                            customLocation.setZ(lastLocation5.getZ());
                        }
                        if (!packetPlayInFlying.h()) {
                            customLocation.setYaw(lastLocation5.getYaw());
                            customLocation.setPitch(lastLocation5.getPitch());
                        }
                    }
                    this.setLastMovePacket(customLocation);
                    this.onGround = packetPlayInFlying.g();
                    this.location.setTimestamp(timestamp);
                    if (packetPlayInFlying.g()) {
                        this.location.setX(packetPlayInFlying.a());
                        this.location.setY(packetPlayInFlying.b());
                        this.location.setZ(packetPlayInFlying.c());
                    }
                    if (packetPlayInFlying.h()) {
                        this.location.setYaw(packetPlayInFlying.d());
                        this.location.setPitch(packetPlayInFlying.e());
                    }
                    this.location.setOnGround(packetPlayInFlying.f());
                    PlayerLocation clonedLocation = this.location.clone();
                    new BukkitRunnable() {

                        public void run() {
                            PlayerData.this.processPosition(clonedLocation, packetPlayInFlying.g() && PlayerData.this.packets < 20);
                        }
                    }.runTask(AntiCheatPlugin.getPlugin());
                    this.packets = this.player.getVehicle() == null && this.steerTicks > this.getPingTicks() ? (packetPlayInFlying.g() ? 0 : ++this.packets) : 0;
                    long diff = timestamp - this.lastFlying;
                    if (diff > 80L) {
                        this.lastDelayed = timestamp;
                    }
                    if (diff < 25L) {
                        this.lastFast = timestamp;
                    }
                    this.lastFlying = timestamp;
                    this.teleportTicks = this.isTeleporting() ? 0 : ++this.teleportTicks;
                    if (this.abortedDigging) {
                        this.abortedDigging = false;
                        this.digging = false;
                    }
                    if (this.stoppedDigging) {
                        this.stoppedDigging = false;
                        this.digging = false;
                    }
                    if ((teleport = this.teleportList.peek()) != null) {
                        lastPing = this.totalTicks - teleport.getTickTime();
                        if (packetPlayInFlying.g() && lastPing >= this.getMoveTicks() && teleport.sameLocation(this.location)) {
                            this.teleportList.poll();
                            this.lastVelY = 0.0;
                            this.lastVelY2 = 0.0;
                            this.velY = 0.0;
                            this.velX = 0.0;
                            this.velZ = 0.0;
                            this.lastLocation = this.location.clone();
                            this.lastLastLocation = this.location.clone();
                            this.spawnedIn = false;
                            return;
                        }
                        if (lastPing > (packetPlayInFlying.g() ? this.getPingTicks() * 2 : this.getPingTicks() * 4)) {
                            this.teleportList.poll();
                        }
                    }
                    if (this.lastVelY == 0.0 && this.velY != 0.0) {
                        if (packetPlayInFlying.f()) {
                            this.velY = 0.0;
                        } else {
                            this.velY -= 0.08;
                            this.velY *= 0.98;
                        }
                    }
                    if (packetPlayInFlying.f() && this.teleportTicks > this.getPingTicks()) {
                        this.spawnedIn = true;
                    }
                    ++this.totalTicks;
                    ++this.lastAttackTicks;
                    ++this.steerTicks;
                    ++this.velocityTicks;
                    ++this.verticalVelocityTicks;
                    ++this.horizontalVelocityTicks;
                    ++this.kbticks;
                    double lastY = deltaY;
                    double Y = location.getY() - lastLocation.getY();
                    lastDeltaY = lastY;
                    deltaY = Y;
                    if (!this.lastLocation.sameLocation(this.location)) {
                        this.movementChecks.forEach(movementCheck -> movementCheck.handle(this, this.lastLocation, this.location, timestamp));
                    }
                    if (!this.lastLocation.sameDirection(this.location)) {
                        this.aimChecks.forEach(aimCheck -> aimCheck.handle(this, this.lastLocation, this.location, timestamp));
                    }
                    this.lastLastLocation = this.lastLocation.clone();
                    this.lastLocation = this.location.clone();
                    if (!(this.getLastAttackTicks() > 1 || this.lastAttacked == null || this.hasLag() || this.hasFast() || this.teleportTicks <= this.getPingTicks() + 2 || this.lastAttacked.getTeleportTicks() <= this.lastAttacked.getPingTicks() + this.getPingTicks() + 2 || this.lastAttacked.hasLag(timestamp - (long) this.getPing()) || this.lastAttacked.hasFast(timestamp - (long) this.getPing()) || this.player.getGameMode() == GameMode.CREATIVE)) {
                        PlayerLocation location = this.location.clone();
                        PlayerLocation lastLocation = this.lastLastLocation.clone();
                        Deque<PlayerLocation> recentMovesQueue = this.recentMoveMap.get(this.lastAttackedId);
                        if (recentMovesQueue != null && !recentMovesQueue.isEmpty() && recentMovesQueue.size() > 10) {
                            List<PlayerLocation> recentMoves = recentMovesQueue.stream().map(PlayerLocation::clone).collect(Collectors.toList());
                            List<PlayerLocation> possibleMoves = recentMoves.stream().filter((move) -> (double) (timestamp - move.getTimestamp() - (long) ping - 75L) <= 25.0D).collect(Collectors.toList());
                            if (!possibleMoves.isEmpty()) {
                                PlayerLocation earliestLocation = possibleMoves.stream().min(Comparator.comparingLong(PlayerLocation::getTimestamp)).get();
                                int index = recentMoves.indexOf(earliestLocation);
                                if (index > 0) {
                                    possibleMoves.add(recentMoves.get(index - 1));
                                }

                                List<DistanceData> distanceList = possibleMoves.stream().map(PlayerLocation::hitbox).map((hitbox) -> hitbox.expand(0.0325D, 0.0D, 0.0325D)).map((hitbox) -> {
                                    double distanceNow = hitbox.distanceXZ(location.getX(), location.getZ());
                                    double distanceBefore = hitbox.distanceXZ(lastLocation.getX(), lastLocation.getZ());
                                    double x1 = location.getX() - hitbox.cX();
                                    double x2 = lastLocation.getX() - hitbox.cX();
                                    double z1 = location.getZ() - hitbox.cZ();
                                    double z2 = lastLocation.getZ() - hitbox.cZ();
                                    double y1 = location.getY() - hitbox.cY();
                                    double y2 = lastLocation.getY() - hitbox.cY();
                                    return new DistanceData(hitbox, Math.abs(x1) < Math.abs(x2) ? x1 : x2, Math.abs(z1) < Math.abs(z2) ? z1 : z2, Math.abs(y1) < Math.abs(y2) ? y1 : y2, Math.min(distanceBefore, distanceNow));
                                }).collect(Collectors.toList());
                                List<Double> reachList = distanceList.stream().map(DistanceData::getDist).collect(Collectors.toList());
                                PlayerLocation[] locationArray = possibleMoves.toArray(new PlayerLocation[0]);
                                DistanceData horizontalData = distanceList.stream().min(Comparator.comparingDouble(DistanceData::getDist)).get();
                                double horizontal = horizontalData.getDist();
                                double movement = MathUtils.highest(reachList) - horizontal;
                                double angle = Math.toRadians(Math.min(MathUtils.getMinimumAngle(location, locationArray), MathUtils.getMinimumAngle(lastLocation, locationArray)));
                                double extra = Math.abs(Math.sin(angle)) * horizontal;
                                double vertical = Math.abs(Math.sin(Math.toRadians(Math.abs(location.getPitch()))) * horizontal);
                                double reach = MathUtils.hypot(horizontal, vertical, extra) - 0.01D;

                                ClientData clientData = new ClientData(this, location, horizontalData, movement, horizontal, extra, vertical, reach);
                                Iterator var28 = this.clientChecks.iterator();

                                if (var28.hasNext()) {
                                    do {
                                        ClientCheck reachCheck = (ClientCheck) var28.next();
                                        reachCheck.handle(this, clientData, System.currentTimeMillis());
                                    } while (var28.hasNext());
                                }
                            }

                        }
                    }
                } else if (packet instanceof PacketPlayInUseEntity) {
                    PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity) packet;
                    if (packetPlayInUseEntity.a() == EnumEntityUseAction.ATTACK) {
                        if (this.horizontalVelocityTicks > this.getPingTicks() / 2 - 2) {
                            this.velX *= 0.6;
                            this.velZ *= 0.6;
                        }
                        this.lastAttackedId = SafeReflection.getAttackedEntity(packetPlayInUseEntity);
                        net.minecraft.server.v1_8_R3.Entity entity = packetPlayInUseEntity.a(this.entityPlayer.getWorld());
                        if (entity != null) {
                            if (entity instanceof EntityPlayer) {
                                EntityPlayer entityPlayer = (EntityPlayer) entity;
                                this.lastAttacked = entityPlayer.playerConnection != null ? AntiCheatPlayer.getInstance().getPlayers().get(entityPlayer.getBukkitEntity().getPlayer().getUniqueId()) : null;
                            } else {
                                this.lastAttacked = null;
                            }
                        } else {
                            this.lastAttacked = null;
                        }
                        this.lastAttackTicks = 0;
                    }
                }
                if (packet instanceof PacketPlayInTransaction) {
                    PacketPlayInTransaction packetPlayInTransaction = (PacketPlayInTransaction) packet;
                    if (this.transactionID.remove(String.valueOf(packetPlayInTransaction.b()))) {
                        this.kbticks = 0;
                    }
                }
                if (!(packet instanceof PacketPlayInKeepAlive)) {
                    if (packet instanceof PacketPlayInEntityAction) {
                        PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction) packet;
                        if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING) {
                            this.sprinting = true;
                        } else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING) {
                            this.sprinting = false;
                        } else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING) {
                            this.sneaking = true;
                        } else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING) {
                            this.sneaking = false;
                        }
                    } else if (packet instanceof PacketPlayInClientCommand) {
                        PacketPlayInClientCommand packetPlayInClientCommand = (PacketPlayInClientCommand) packet;
                        if (packetPlayInClientCommand.a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                            this.inventoryOpen = true;
                        }
                    } else if (packet instanceof PacketPlayInCloseWindow) {
                        this.inventoryOpen = false;
                    } else if (packet instanceof PacketPlayInBlockDig) {
                        PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig) packet;
                        if (this.player.getGameMode() == GameMode.CREATIVE) {
                            this.digging = false;
                        } else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                            this.digging = true;
                            this.abortedDigging = false;
                            this.stoppedDigging = false;
                        } else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                            this.abortedDigging = true;
                        } else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                            this.stoppedDigging = true;
                        }
                    } else if (packet instanceof PacketPlayInBlockPlace) {
                        this.place = true;
                    } else if (packet instanceof PacketPlayInSteerVehicle) {
                        this.steerTicks = 0;
                    }
                } else {
                    PacketPlayInKeepAlive packetPlayInKeepAlive = (PacketPlayInKeepAlive) packet;
                    int protocol = packetPlayInKeepAlive.a();
                    this.receivedKeepAlive = true;
                    Long sent = this.keepAliveMap.remove(protocol);
                    if (sent != null) {
                        this.lastPing = this.ping;
                        this.ping = (int) (timestamp - sent);
                        this.averagePing = (this.averagePing * 3 + this.ping) / 4;
                    }
                }
                this.packetChecks.forEach(check -> check.handle(this, packet, timestamp));
            } else {
                long timestamp = System.currentTimeMillis();
                if (packet instanceof PacketPlayOutTransaction) {
                    short id = SafeReflection.getTransactionId((PacketPlayOutTransaction) packet);
                    this.transactionID.add(String.valueOf(id));
                } else if (packet instanceof PacketPlayOutPosition) {
                    timestamp = System.currentTimeMillis();
                    PacketPlayOutPosition packetPlayOutPosition = (PacketPlayOutPosition) packet;
                    PlayerLocation clonedLocation = SafeReflection.getLocation(timestamp, this.totalTicks, packetPlayOutPosition);
                    this.teleportList.add(clonedLocation);
                    this.teleportTicks = 0;
                    this.lastPosition = timestamp;
                } else if (packet instanceof PacketPlayOutKeepAlive) {
                    PlayerConnection playerConnection = this.entityPlayer.playerConnection;
                    SafeReflection.setNextKeepAliveTime(playerConnection, SafeReflection.getNextKeepAliveTime(playerConnection) + 36);
                    PacketPlayOutKeepAlive packetPlayOutKeepAlive = (PacketPlayOutKeepAlive) packet;
                    int keepAliveNumber = SafeReflection.getKeepAliveId(packetPlayOutKeepAlive);
                    this.keepAliveMap.put(keepAliveNumber, timestamp);
                } else if (packet instanceof PacketPlayOutEntityVelocity) {
                    PacketPlayOutEntityVelocity packetPlayOutEntityVelocity = (PacketPlayOutEntityVelocity) packet;
                    VelocityData velocityPacket = SafeReflection.getVelocity(packetPlayOutEntityVelocity);
                    if (velocityPacket.getEntityId() == this.player.getEntityId()) {
                        this.velY = (double) velocityPacket.getY() / 8000.0;
                        this.velX = (double) velocityPacket.getX() / 8000.0;
                        this.velZ = (double) velocityPacket.getZ() / 8000.0;
                        this.horizontalVelocityTicks = 0;
                        int currentTicks = this.totalTicks;
                        Cuboid cuboid = new Cuboid(this.location).move(0.0, 1.5, 0.0).expand(0.5, 1.0, 0.5);
                        org.bukkit.World world = this.player.getWorld();
                        Runnable runnable = () -> {
                            int ticksOffset = currentTicks - this.totalTicks;
                            boolean blockAbove = false;
                            if (this.velY > 0.0) {
                                for (Block block : cuboid.getBlocks(world)) {
                                    if (block.isEmpty()) continue;
                                    blockAbove = true;
                                    break;
                                }
                            }
                            if (!blockAbove && this.lastVelY == 0.0 && this.ping > 0) {
                                this.lastVelY = this.velY;
                                this.lastVelY2 = this.velY;
                                this.verticalVelocityTicks = ticksOffset;
                            } else {
                                this.lastVelY = 0.0;
                                this.lastVelY2 = 0.0;
                            }
                        };
                        new BukkitRunnable() {

                            public void run() {
                                runnable.run();
                            }
                        }.runTask(AntiCheatPlugin.getPlugin());
                        this.velocityTicks = Math.min(this.velocityTicks, 0) - (int) Math.ceil(Math.pow(MathUtils.hypotSquared(this.velX, this.velY, this.velZ) * 2.0, 1.75) * 4.0);

                        getEntityPlayer().playerConnection.sendPacket(new PacketPlayOutTransaction(0, (short) getRandom(-32768, 32768), false));
                    }
                } else if (packet instanceof PacketPlayOutExplosion) {
                    this.velocityTicks = 0;
                } else if (packet instanceof PacketPlayOutOpenWindow) {
                    this.digging = false;
                } else if (packet instanceof PacketPlayOutEntityTeleport) {
                    PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = (PacketPlayOutEntityTeleport) packet;
                    int entityId = SafeReflection.getEntityId(packetPlayOutEntityTeleport);
                    Deque<PlayerLocation> recentMoveQueue = this.recentMoveMap.get(entityId);
                    if (recentMoveQueue == null) {
                        recentMoveQueue = new ConcurrentLinkedDeque<>();
                        this.recentMoveMap.put(entityId, recentMoveQueue);
                    }
                    recentMoveQueue.add(SafeReflection.getLocation(System.currentTimeMillis(), this.totalTicks, packetPlayOutEntityTeleport));
                    if (recentMoveQueue.size() > 20) {
                        recentMoveQueue.poll();
                    }
                } else if (!(packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMove) && !(packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook)) {
                    if (packet instanceof PacketPlayOutEntityDestroy) {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = (PacketPlayOutEntityDestroy) packet;
                        this.recentMoveMap.keySet().removeAll(Arrays.stream(SafeReflection.getEntities(packetPlayOutEntityDestroy)).boxed().collect(Collectors.toList()));
                    }
                } else {
                    PacketPlayOutEntity packetPlayOutEntity = (PacketPlayOutEntity) packet;

                    int entityId = SafeReflection.getEntityId(packetPlayOutEntity);
                    Deque<PlayerLocation> recentMoveQueue = this.recentMoveMap.get(entityId);
                    if (recentMoveQueue != null && !recentMoveQueue.isEmpty()) {
                        Vector movement = SafeReflection.getMovement(packetPlayOutEntity);
                        PlayerLocation location = recentMoveQueue.peekLast().clone().add(movement.getX(), movement.getY(), movement.getZ());
                        location.setTickTime(this.totalTicks);
                        location.setTimestamp(System.currentTimeMillis());
                        recentMoveQueue.add(location);
                        if (recentMoveQueue.size() > 20) {
                            recentMoveQueue.poll();
                        }
                    }
                }
            }
        }
    }

    private void processPosition(PlayerLocation location, boolean hasMove) {
        if (this.enabled) {
            long timestamp = System.currentTimeMillis();
            if (this.recentMoveList.size() > 20) {
                this.recentMoveList.poll();
            }
            location.setTimestamp(timestamp);
            this.recentMoveList.add(location);
            if (!hasMove) {
                for (PlayerData playerData : AntiCheatPlayer.getInstance().getPlayers().values()) {
                    Deque<PlayerLocation> recentMoveQueue = playerData.getRecentMoveMap().get(this.entityPlayer.getId());
                    if (recentMoveQueue == null || recentMoveQueue.isEmpty() || (location = recentMoveQueue.peekLast()) == null)
                        continue;
                    location = location.clone();
                    location.setTickTime(playerData.getTotalTicks());
                    location.setTimestamp(timestamp);
                    recentMoveQueue.add(location);
                    if (recentMoveQueue.size() <= 20) continue;
                    recentMoveQueue.poll();
                }
            }
        }
    }

    public void fuckOff(PlayerData playerData, String net) {
        if (!playerData.isCrashing()) {
            long currentTimeMillis = System.currentTimeMillis();
            PlayerConnection connection = ((CraftPlayer) this.getPlayer()).getHandle().playerConnection;
            if (connection == null) {
                return;
            }
            NetworkManager manager = connection.networkManager;
            if (manager == null) {
                return;
            }
            Channel ch = manager.channel;
            if (ch == null) {
                return;
            }
            ch.close();
            AntiCheatPlugin.getPlugin().getLogger().info("------------------");
            AntiCheatPlugin.getPlugin().getLogger().info("FBI Anti-Crash");
            AntiCheatPlugin.getPlugin().getLogger().info("-> " + this.getPlayer().getName());
            AntiCheatPlugin.getPlugin().getLogger().info("Force kicked in " + (System.currentTimeMillis() - currentTimeMillis) + "ms.");
            AntiCheatPlugin.getPlugin().getLogger().info("-> " + net);
            AntiCheatPlugin.getPlugin().getLogger().info("FBI Anti-Crash");
            AntiCheatPlugin.getPlugin().getLogger().info("------------------");
            AntiCheatPlayer.getInstance().getPlayers().values().stream().filter(PlayerData::isAlerts).map(PlayerData::getPlayer).forEach(player -> this.sendCommandMsg(player, Settings.getInstance().getNocrashingAlerts().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")).replace("{player}", this.getPlayer().getName()), this.getPlayer(), currentTimeMillis, net));
            playerData.setCrashing(true);
        }
    }

    private void sendCommandMsg(Player p, String msg, Player player, long currentTimeMillis, String net) {
        TextComponent message = new TextComponent(msg);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + Settings.getInstance().getNocrashingHover().replace('&', '§').replace("{player}", player.getName()).replace("{ms}", String.valueOf(System.currentTimeMillis() - currentTimeMillis)).replace("{reason}", net)));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Settings.getInstance().getNocrashingHover().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")).replace("{player}", player.getName()).replace("{ms}", String.valueOf(System.currentTimeMillis() - currentTimeMillis)).replace("{reason}", net)).create()));
        p.spigot().sendMessage(message);
    }

    public void cancelMove(org.bukkit.entity.Player player, PlayerLocation location, World world, Integer number) {
        int x = Location.locToBlock(location.getX() - 0.3);
        int z = Location.locToBlock(location.getZ() - 0.3);
        int y = Location.locToBlock(location.getY());
        int x2 = Location.locToBlock(location.getX() + 0.3);
        int z2 = Location.locToBlock(location.getZ() + 0.3);
        for (int i = 0; i < 1; ++i) {
            if (BukkitUtils.checkFlag(player.getWorld().getBlockAt(x, y - 1, z).getType(), 1)) {
                break;
            }
            if (BukkitUtils.checkFlag(player.getWorld().getBlockAt(x2, y - 1, z2).getType(), 1)) {
                break;
            }
            --y;
        }
        player.teleport(new Location(world, location.getX(), y, location.getZ(), location.getYaw(), location.getPitch()));
        player.sendMessage("[" + ChatColor.DARK_RED + ChatColor.BOLD + "DEV" + ChatColor.GRAY + "] " + ChatColor.GRAY + "You just got rollback. (ID: " + number + ")");
    }

    public double getMovementSpeed() {
        AttributeModifiable attribute = (AttributeModifiable) entityPlayer.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);

        double baseMovementSpeed = attribute.b();

        AtomicReference<Double> value = new AtomicReference<>(baseMovementSpeed);

        IntStream.range(0, 3).forEach(i -> attribute.a(i)
                .forEach(modifier -> {
                    switch (i) {
                        case 0:
                            value.updateAndGet(v -> v + modifier.d());
                            break;

                        case 1:
                            value.updateAndGet(v -> v + modifier.d() * baseMovementSpeed);
                            break;

                        case 2:
                            // We want to handle sprinting by ourselves considering it's shit in bukkit
                            value.updateAndGet(v -> v + (v * modifier.d()));

                            break;
                    }
                }));
        return value.get();
    }

    Random random = new Random();

    public int getRandom(int lower, int upper) {
        return random.nextInt((upper - lower) + 1) + lower;
    }

    public int getMoveTicks() {
        return (int) Math.floor((double) Math.min(this.ping, this.averagePing) / 125.0);
    }

    public int getPingTicks() {
        return (this.receivedKeepAlive ? (int) Math.ceil((double) this.getPing() / 50.0) : 20) + 1;
    }

    public int getMaxPingTicks() {
        return (this.receivedKeepAlive ? (int) Math.ceil((double) Math.max(this.ping, this.averagePing) / 50.0) : 20) + 1;
    }

    public PlayerLocation getLocation(int ticks) {
        int size = this.recentMoveList.size() - 1;
        return size > ticks ? Iterables.get(this.recentMoveList, size - ticks) : null;
    }

    public Map<String, String> getForgeMods() {
        return this.forgeMods;
    }

    public boolean hasLag(long timestamp) {
        return this.lastFlying != 0L && this.lastDelayed != 0L && timestamp - this.lastDelayed < 150L || System.currentTimeMillis() - this.lastFlying > 90;
    }

    public boolean hasLag() {
        return this.hasLag(this.lastFlying);
    }

    public boolean hasFast(long timestamp) {
        return this.lastFlying != 0L && this.lastFast != 0L && timestamp - this.lastFast < 110L;
    }

    public long lastTimeHit;

    public long getLastTimeHit() {
        return this.lastTimeHit;
    }

    public void setLastTimeHit(long lastTimeHit) {
        this.lastTimeHit = lastTimeHit;
    }

    public void addLastTimeHit() {
        this.lastTimeHit++;
    }

    public boolean hasFast() {
        return this.hasFast(this.lastFlying);
    }

    public boolean isTeleporting() {
        return !this.teleportList.isEmpty();
    }

}