# PlayPerms ~ Permission System

## What is PlayPerms?

PlayPerms is a Permission System developed to prove my skills to the PlayLegend Devs. It's based on BungeeCord and
Spigot but will only work if you use a BungeeCord based System.

## Commands

| Command                                                                   | Permission                        | Example                                                    | Notes                                                                                |
|---------------------------------------------------------------------------|-----------------------------------|------------------------------------------------------------|--------------------------------------------------------------------------------------|
| /playperms createGroup groupName chatPrefix tabPrefix weight defaultGroup | playperms.groups.create           | /playperms createGroup Admin &cAdmin &cAdmin 1 false       | The Bigger the weight, the lower the group in tab (Weight between 11-99 is the best) |
| /playperms deleteGroup groupName                                          | playperms.groups.delete           | /playperms deleteGroup Admin                               |                                                                                      |
| /playperms getPlayerGroup                                                 |                                   | /playperms getPlayerGroup                                  |                                                                                      |
| /playperms getPlayerGroup playerName                                      | playperms.player.get              | /playperms getPlayerGroup GetThatAlcohol                   |                                                                                      |
| /playperms setPlayerGroup playerName groupName length...                  | playperms.player.setGroup         | /playperms setPlayerGroup GetThatAlcohol Admin 30d 10m 30s | TimeUnits: Y, M, w, d, h, m, s                                                       |
| /playperms updateGroup name groupName value                               | playperms.groups.update           | /playperms updateGroup name Admin Owner                    |                                                                                      |
| /playperms updateGroup chatPrefix groupName value                         | playperms.groups.update           | /playperms updateGroup chatPrefix &cOwner                  |                                                                                      |
| /playperms updateGroup tabPrefix groupName value                          | playperms.groups.update           | /playperms updateGroup tabPrefix &cOwner                   |                                                                                      |
| /playperms updateGroup weight groupName value                             | playperms.groups.update           | /playperms updateGroup weight 10                           |                                                                                      |
| /playperms updateGroup defaultGroup groupName value                       | playperms.groups.update           | /playperms updateGroup defaultGroup true                   |                                                                                      |
| /playperms addGroupPermission groupName permissionToAdd                   | playperms.groups.addPermission    | /playperms addGroupPermission Admin *                      |                                                                                      |
| /playperms removeGroupPermission groupName permissionToRemove             | playperms.groups.removePermission | /playperms removeGroupPermission Admin *                   |                                                                                      |
| /setSign playerName                                                       | playperms.sign.add                | /setSign GetThatAlcohol                                    | Place your crosshair at the block you want to set the sign                           |
| /removeSign                                                               | playperms.sign.remove             | /removeSign                                                | Place your crosshair at the sign you want to remove                                  |
| create playerName                                                         |                                   | create GetThatAlcohol                                      | Command only executable from the bungee console                                      |

**Please only execute "create playerName" after first connection to the server. This will create the admin rank and
grant it "*" permissions. Please leave the server before executing the command!**

## Plugin Setup

1. Put the BungeeCord plugin into the BungeeCord plugins folder
2. Put the Spigot plugin into the Spigot plugins folder
3. Start BungeeCord and Spigot server
4. Configs will be created (Errors will occur because no database connection can be established)
5. Edit the configs depending on your wishes and add the database credentials
6. Restart the BungeeCord and the Spigot server
7. On the first join a "default" Rank will be created
8. **Leave the Server** and type "create playerName" in the bungeecord console
9. Rejoin and enjoy your newly granted admin rank

## Favorite Songs during development

YouTube Links

- [HAARPER - WE ONLY GO NORTH](https://www.youtube.com/watch?v=e3RBptPKltU) (FAV)
- [LOAT! - xanny bar](https://www.youtube.com/watch?v=enzAqfTOE1Y)
- [Lil God Dan - MARCELINE](https://www.youtube.com/watch?v=Z5zmRakcTss)
- [LOAT! - take me out](https://www.youtube.com/watch?v=YShi1OA2KiQ)
- [1nonly - Pretty Girl](https://www.youtube.com/watch?v=V_1ITgl2Mr4)

## Known issues

- Somehow in BungeeCord Console every message of Hibernate is marked as "Severe" Error. These are not errors BungeeCord
  logging just lies to you
- First player that joins the BungeeCord server somehow takes ~150ms. This might be because
  hibernate pools are not warmed up at this point. After first join everything takes the normal time.
