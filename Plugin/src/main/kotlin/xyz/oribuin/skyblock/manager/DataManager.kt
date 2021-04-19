package xyz.oribuin.skyblock.manager

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.scheduler.BukkitTask
import xyz.oribuin.orilibrary.database.DatabaseConnector
import xyz.oribuin.orilibrary.database.MySQLConnector
import xyz.oribuin.orilibrary.database.SQLiteConnector
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.orilibrary.util.FileUtils
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.island.Member
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class DataManager(private val plugin: Skyblock) : Manager(plugin) {

    var islands = mutableListOf<Island>()
    var members = mutableListOf<Member>()

    var connector: DatabaseConnector? = null
    var tableName = this.plugin.config.getString("mysql.table") ?: "oriskyblock"

    override fun enable() {
        val config = this.plugin.config

        if (config.getBoolean("mysql.enabled")) {
            // Define all the MySQL Values.
            val hostName = config.getString("mysql.host") ?: return
            val port = config.getInt("mysql.port")
            val dbname = config.getString("mysql.dbname") ?: return
            val username = config.getString("mysql.username") ?: return
            val password = config.getString("mysql.password") ?: return
            val ssl = config.getBoolean("mysql.ssl")

            // Connect to MySQL.
            connector = MySQLConnector(this.plugin, hostName, port, dbname, username, password, ssl)
            this.plugin.logger.info("Using MySQL for Database ~ $hostName:$port")
        } else {

            // Create the database File
            FileUtils.createFile(this.plugin, "skyblock.db")

            // Connect to SQLite
            connector = SQLiteConnector(this.plugin, "skyblock.db")
            getPlugin().logger.info("Using SQLite for Database ~ skyblock.db")
        }

        this.loadIslands()
    }

    /**
     * Create all the tables for the SQL Database and cache all the islands & users
     */
    private fun loadIslands() {

        this.async {
            CompletableFuture.runAsync {
                this.connector?.connect { connection ->
                    val queries = arrayOf(
                        "CREATE TABLE IF NOT EXISTS ${tableName}_islands (id INT, owner VARCHAR(50), name VARCHAR(200), loc_x DOUBLE, loc_y DOUBLE, loc_z DOUBLE, size INT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE, spawn_yaw FLOAT, spawn_pitch FLOAT, locked BOOLEAN, PRIMARY KEY(id))",
                        "CREATE TABLE IF NOT EXISTS ${tableName}_members (user VARCHAR(50), island INT, PRIMARY KEY(user))"
                    )

                    val statement = connection.createStatement()
                    queries.forEach { x -> statement.addBatch(x) }
                    statement.executeBatch()

                }

            }.thenRunAsync {
                this.cacheMembers()
                this.cacheIslands()
            }
        }

    }

    /**
     * Saves an island into MySQL
     *
     * @param island The island.
     */
    fun saveIsland(island: Island) {
        val loc = island.location
        this.islands.removeIf { it.id == island.id }
        this.islands.add(island)

        async { _ ->
            val query = "REPLACE INTO ${tableName}_islands (id, owner, name, loc_x, loc_y, loc_z, size, spawn_x, spawn_y, spawn_z, spawn_yaw, spawn_pitch, locked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            this.connector?.connect { connection ->
                connection.prepareStatement(query).use {
                    it.setInt(1, island.id)
                    it.setString(2, island.owner.toString())
                    it.setString(3, island.name)
                    it.setDouble(4, loc.x)
                    it.setDouble(5, loc.y)
                    it.setDouble(6, loc.z)
                    it.setInt(7, island.size)
                    it.setDouble(8, loc.x)
                    it.setDouble(9, loc.y)
                    it.setDouble(10, loc.z)
                    it.setFloat(11, loc.yaw)
                    it.setFloat(12, loc.pitch)
                    it.setBoolean(13, island.locked)
                    it.executeUpdate()
                }
            }

            val members = mutableListOf<Member>()
            members.addAll(island.members)
            members.add(Member(island.owner, island.id))
            saveMembers(members)

        }

    }

    /**
     * Save and cache all the island members.
     *
     * @param members The members to save
     */
    private fun saveMembers(members: List<Member>) {
        async { _ ->

            this.connector?.connect { connection ->
                val statement = connection.createStatement()
                members.forEach {
                    statement.addBatch("REPLACE INTO ${tableName}_members (user, island) VALUES(\"${it.user}\", ${it.island})")
                    this.members.remove(it)
                }

                statement.executeBatch()
            }

            this.members.addAll(members)
        }


    }

    /**
     * Cache all the islands.
     */
    private fun cacheIslands() {
        val list = mutableListOf<Island>()
        val world = this.plugin.getManager(WorldManager::class.java).world
        this.islands.clear()

        CompletableFuture.runAsync {

            this.connector?.connect { connection ->

                connection.prepareStatement("SELECT * FROM ${tableName}_islands").use { preparedStatement ->
                    val result = preparedStatement.executeQuery()

                    while (result.next()) {
                        // Define island location
                        val islandLoc = Location(world, result.getDouble("loc_x"), result.getDouble("loc_y"), result.getDouble("loc_z"))
                        val spawnLoc = Location(world, result.getDouble("spawn_x"), result.getDouble("spawn_y"), result.getDouble("spawn_z"), result.getFloat("spawn_yaw"), result.getFloat("spawn_pitch"))

                        val island = Island(result.getInt("id"), UUID.fromString(result.getString("owner")), islandLoc, result.getString("name"))

                        // Define basic values for the island
                        island.size = result.getInt("size")
                        island.spawnPoint = spawnLoc
                        island.locked = result.getBoolean("locked")

                        // Add all Island Members
                        island.members = this.members.filter { member -> member.island == island.id }.toMutableList()

                        list.add(island)
                    }
                }

            }

        }.thenRunAsync { this.islands.addAll(list); this.plugin.logger.warning("Successfully cached (${islands.size}) islands.") }

    }

    /**
     * Cache all the members from the SQL DB
     */
    private fun cacheMembers() {
        this.members.clear()
        val members = mutableListOf<Member>()

        CompletableFuture.runAsync {
            this.connector?.connect { connection ->
                connection.prepareStatement("SELECT * FROM ${tableName}_members").use {
                    val result = it.executeQuery()

                    while (result.next()) members.add(Member(UUID.fromString(result.getString("user")), result.getInt("island")))
                }
            }
        }.thenRunAsync { this.members.addAll(members); this.plugin.logger.warning("Successfully cached (${members.size}) members.") }

    }

    /**
     * Get an offline player's island.
     *
     * @param player The offline player
     * @return A Nullable Island
     */
    fun getIsland(player: OfflinePlayer): Island? {
        val member = this.members.find { it.user == player.uniqueId } ?: return null
        return this.islands.find { it.id == member.island }
    }

    override fun disable() {
        this.connector?.closeConnection()
    }

    private fun async(callback: Consumer<BukkitTask>) {
        Thread { this.plugin.server.scheduler.runTaskAsynchronously(plugin, callback) }.start()
    }


}