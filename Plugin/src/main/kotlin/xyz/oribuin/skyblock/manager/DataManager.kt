package xyz.oribuin.skyblock.manager

import org.bukkit.Location
import org.bukkit.scheduler.BukkitTask
import xyz.oribuin.orilibrary.database.DatabaseConnector
import xyz.oribuin.orilibrary.database.MySQLConnector
import xyz.oribuin.orilibrary.database.SQLiteConnector
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.orilibrary.util.FileUtils
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.island.Member
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

            this.connector?.connect { connection ->
                val queries = arrayOf(
                    "CREATE TABLE IF NOT EXISTS ${tableName}_islands (id INT, owner VARCHAR(50), name VARCHAR(200), loc_x DOUBLE, loc_y DOUBLE, loc_z DOUBLE, size INT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE, spawn_yaw FLOAT, spawn_pitch FLOAT, locked BOOLEAN, members TEXT[], PRIMARY KEY(id))",
                )

                val statement = connection.createStatement()
                queries.forEach { x -> statement.addBatch(x) }
                statement.executeBatch()

            }

        }

    }

    /**
     * Cache All the islands & members
     */
    private fun cacheIslands() {
//        val list = mutableListOf<Island>()
//        val world = this.plugin.getManager(WorldManager::class.java).world ?: return
//
//        CompletableFuture.runAsync {
//            this.connector?.connect { connection ->
//                connection.prepareStatement("SELECT * FROM ${tableName}_islands").use { preparedStatement ->
//                    val result = preparedStatement.executeQuery()
//
//                    connection.createArrayOf("text")
//                    while (result.next()) {
//                        val islandLoc = Location(world, result.getDouble("loc_x"), result.getDouble("loc_y"), result.getDouble("loc_z"))
//                        val spawnLoc = Location(world, result.getDouble("spawn_x", result.getString("spawn_y"), result.get))
//                    }
//                }
//
//            }
//        }

    }

    override fun disable() {
        this.connector?.closeConnection()
    }

    fun async(callback: Consumer<BukkitTask>) {
        Thread { this.plugin.server.scheduler.runTaskAsynchronously(plugin, callback) }.start()
    }


}