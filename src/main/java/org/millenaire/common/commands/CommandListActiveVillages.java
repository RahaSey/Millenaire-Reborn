package org.millenaire.common.commands;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.millenaire.common.config.MillConfigValues;
import org.millenaire.common.forge.Mill;
import org.millenaire.common.network.ServerSender;
import org.millenaire.common.utilities.MillLog;
import org.millenaire.common.utilities.WorldUtilities;
import org.millenaire.common.village.Building;
import org.millenaire.common.world.MillWorldData;

public class CommandListActiveVillages implements ICommand {
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    return true;
  }
  
  public int compareTo(ICommand o) {
    return getCommandName().compareTo(o.getCommandName());
  }
  
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    World world = sender.func_130014_f_();
    if (!world.isRemote) {
      MillWorldData worldData = Mill.getMillWorld(world);
      for (int i = 0; i < worldData.villagesList.pos.size(); i++) {
        Building townHall = worldData.getBuilding(worldData.villagesList.pos.get(i));
        if (townHall != null && townHall.isActive) {
          long villagersTime = 0L;
          long buildingsTime = 0L;
          for (Long sample : worldData.villagesList.villagersTime.get(i))
            villagersTime += sample.longValue(); 
          for (Long sample : worldData.villagesList.buildingsTime.get(i))
            buildingsTime += sample.longValue(); 
          NumberFormat nf = NumberFormat.getInstance();
          String buildingsTimeStr = nf.format(buildingsTime / 1000L);
          String villagersTimeStr = nf.format(villagersTime / 1000L);
          List<Entity> entities = WorldUtilities.getEntitiesWithinAABB(world, EntityPlayer.class, townHall.getPos(), MillConfigValues.KeepActiveRadius, 1000);
          String players = "";
          for (Entity playerEntity : entities) {
            EntityPlayer player = (EntityPlayer)playerEntity;
            if (players.length() > 0)
              players = players + ", "; 
            players = players + player.func_70005_c_();
          } 
          if (!(sender instanceof EntityPlayer)) {
            MillLog.major(this, "Village " + townHall
                .getVillageQualifiedName() + " is active. It knows " + townHall.getKnownVillagers().size() + " villagers (" + townHall
                .getAllVillagerRecords().size() + " in the archives). Within the last 20 ticks, it took " + buildingsTimeStr + " ns to handle buildings and " + villagersTimeStr + " ns to handle villagers. Kept alive by: " + players);
          } else {
            EntityPlayer senderPlayer = (EntityPlayer)sender;
            ServerSender.sendTranslatedSentence(senderPlayer, 'f', "command.listactivevillages_list", new String[] { townHall.getVillageQualifiedName(), "" + townHall
                  .getKnownVillagers().size(), "" + townHall.getAllVillagerRecords().size(), buildingsTimeStr, villagersTimeStr, players });
          } 
        } 
      } 
    } 
  }
  
  public List<String> getCommandAliases() {
    return Collections.emptyList();
  }
  
  public String getCommandName() {
    return "millListActiveVillages";
  }
  
  public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
    return Collections.emptyList();
  }
  
  public String getCommandUsage(ICommandSender sender) {
    return "commands." + getCommandName().toLowerCase() + ".usage";
  }
  
  public boolean isUsernameIndex(String[] args, int index) {
    return false;
  }
}
