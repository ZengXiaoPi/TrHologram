package me.arasple.mc.trhologram.nms;

import io.izzel.taboolib.Version;
import io.izzel.taboolib.module.lite.SimpleReflection;
import io.izzel.taboolib.module.packet.TPacketHandler;
import me.arasple.mc.trhologram.utils.MapBuilder;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Arasple
 * @date 2020/2/1 22:40
 */
public class HoloPacketsImp extends HoloPackets {

    static {
        SimpleReflection.saveField(PacketPlayOutSpawnEntity.class);
        SimpleReflection.saveField(PacketPlayOutEntityTeleport.class);
        SimpleReflection.saveField(PacketPlayOutEntityMetadata.class);
    }

    @Override
    public void spawnArmorStand(Player player, int entityId, UUID uuid, Location location) {
        TPacketHandler.sendPacket(player, setPacket(PacketPlayOutSpawnEntity.class, new PacketPlayOutSpawnEntity(), new MapBuilder()
                .put("a", entityId)
                .put("b", uuid)
                .put("c", location.getX())
                .put("d", location.getY())
                .put("e", location.getZ())
                .put("f", 0)
                .put("g", 0)
                .put("h", 0)
                .put("i", 0)
                .put("j", 0)
                .put("k", Version.isAfter(Version.v1_14) ? EntityTypes.ARMOR_STAND : 78)
                .put("l", 0)
                .build())
        );
        initArmorStandAsHologram(player, entityId);
    }

    @Override
    public void destroyArmorStand(Player player, int entityId) {
        TPacketHandler.sendPacket(player, new PacketPlayOutEntityDestroy(entityId));
    }

    @Override
    public void initArmorStandAsHologram(Player player, int entityId) {
        sendEntityMetadata(player, entityId,
                getMetaArmorStandProperties(true, false, true, true),
                getMetaEntityProperties(false, true, true, true, true, false, true),
                getMetaEntityCustomNameVisible(true),
                getMetaEntitySilenced(true),
                getMetaEntityGravity(false)
        );
    }

    @Override
    public void updateArmorStandDisplayName(Player player, int entityId, String name) {
        sendEntityMetadata(player, entityId, getMetaEntityCustomName(name));
    }

    @Override
    public void updateArmorStandLocation(Player player, int entityId, Location location) {
        TPacketHandler.sendPacket(player, setPacket(PacketPlayOutEntityTeleport.class, new PacketPlayOutEntityTeleport(), new MapBuilder()
                .put("a", entityId)
                .put("b", location.getX())
                .put("c", location.getY())
                .put("d", location.getZ())
                .put("e", 0)
                .put("f", 0)
                .put("g", false)
                .build())
        );
    }

    @Override
    public void updateArmorStandEquipmentItem(Player player, int entityId, EquipmentSlot slot, ItemStack itemStack) {
        TPacketHandler.sendPacket(player, new PacketPlayOutEntityEquipment(entityId, EnumItemSlot.valueOf(slot.name()), CraftItemStack.asNMSCopy(itemStack)));
    }

    @Override
    public void sendEntityMetadata(Player player, int entityId, Object... objects) {
        List<DataWatcher.Item<?>> items = new ArrayList<>();
        for (Object object : objects) {
            items.add((DataWatcher.Item<?>) object);
        }
        System.out.println("Sending data to " + player.getName() + " of " + items.size() + " of entity " + entityId + " ...");
        TPacketHandler.sendPacket(player, setPacket(PacketPlayOutEntityMetadata.class, new PacketPlayOutEntityMetadata(), new MapBuilder()
                .put("a", entityId)
                .put("b", items)
                .build())
        );
    }

    @Override
    public Object getMetaEntityProperties(boolean onFire, boolean crouched, boolean sprinting, boolean swimming, boolean invisible, boolean glowing, boolean flyingElytra) {
        byte bits = 0;
        bits += (onFire ? 1 : 0);
        bits += (crouched ? 2 : 0);
        bits += (sprinting ? 8 : 0);
        bits += (swimming ? 10 : 0);
        bits += (invisible ? 20 : 0);
        bits += (glowing ? 40 : 0);
        bits += (flyingElytra ? 80 : 0);

        return new DataWatcher.Item<>(new DataWatcherObject<>(0, DataWatcherRegistry.a), bits);
    }

    @Override
    public Object getMetaEntityGravity(boolean gravity) {
        return new DataWatcher.Item<>(new DataWatcherObject<>(5, DataWatcherRegistry.i), gravity);
    }

    @Override
    public Object getMetaEntitySilenced(boolean silenced) {
        return new DataWatcher.Item<>(new DataWatcherObject<>(4, DataWatcherRegistry.i), silenced);
    }

    @Override
    public Object getMetaEntityCustomNameVisible(boolean visible) {
        return new DataWatcher.Item<>(new DataWatcherObject<>(3, DataWatcherRegistry.i), visible);
    }

    @Override
    public Object getMetaEntityCustomName(String name) {
        return new DataWatcher.Item<>(new DataWatcherObject<>(2, DataWatcherRegistry.d), name);
    }

    @Override
    public Object getMetaArmorStandProperties(boolean isSmall, boolean hasArms, boolean noBasePlate, boolean marker) {
        byte bits = 0;
        bits += (isSmall ? 1 : 0);
        bits += (hasArms ? 2 : 0);
        bits += (noBasePlate ? 8 : 0);
        bits += (marker ? 10 : 0);
        return new DataWatcher.Item<>(new DataWatcherObject<>(13, DataWatcherRegistry.a), bits);
    }

    private Object setPacket(Class<?> nms, Object packet, Map<String, Object> sets) {
        sets.forEach((key, value) -> SimpleReflection.setFieldValue(nms, packet, key, value));
        return packet;
    }

}
