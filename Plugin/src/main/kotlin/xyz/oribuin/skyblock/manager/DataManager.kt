package xyz.oribuin.skyblock.manager

import org.bukkit.scheduler.BukkitTask
import xyz.oribuin.orilibrary.database.DatabaseConnector
import xyz.oribuin.orilibrary.database.MySQLConnector
import xyz.oribuin.orilibrary.database.SQLiteConnector
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.orilibrary.util.FileUtils
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.island.Member
import java.util.function.Consumer

class DataManager(private val plugin: Skyblock) : Manager(plugin) {

    var islands = mutableListOf<Island>()
    var members = mutableMapOf<Island, MutableList<Member>>()

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

    private fun loadIslands() {
        this.async {

            var island: Island? = null

            this.connector?.connect { connection ->
                arrayOf(
                    "CREATE TABLE IF NOT EXISTS ${tableName}_islands (id INT, owner VARCHAR(50), name VARCHAR(200), loc_x DOUBLE, loc_y DOUBLE, loc_z DOUBLE, size INT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE, spawn_yaw FLOAT, spawn_pitch FLOAT, locked BOOLEAN, PRIMARY KEY(id))",
                    "CREATE TABLE IF NOT EXISTS ${tableName}_members (user VARCHAR(50), island: "
                )

            }
        }

    }

    override fun disable() {
        this.connector?.closeConnection()
    }

    fun async(callback: Consumer<BukkitTask>) {
        Thread { this.plugin.server.scheduler.runTaskAsynchronously(plugin, callback) }.start()
    }


}