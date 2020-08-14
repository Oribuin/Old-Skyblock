package xyz.oribuin.skyblock.utils

import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object FileUtils {
    /**
     * Creates a file on disk from a file located in the jar
     *
     * @param fileName The name of the file to create
     */
    @JvmStatic
    fun createFile(plugin: Plugin, fileName: String) {
        val file = File(plugin.dataFolder, fileName)

        if (!file.exists()) {
            try {
                plugin.getResource(fileName).use { inStream ->

                    if (file.parentFile.exists()) {
                        file.parentFile.mkdir()
                    }



                    if (inStream == null) {
                        file.createNewFile()
                        return
                    }

                    Files.copy(inStream, Paths.get(file.absolutePath))
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun createFile(plugin: Plugin, file: File) {
        if (!file.exists()) {
            try {
                plugin.getResource(file.name).use { inStream ->
                    if (inStream == null) {
                        file.createNewFile()
                        return
                    }

                    Files.copy(inStream, Paths.get(file.absolutePath))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun createFolder(plugin: Plugin, folderName: String) {
        val file = File(plugin.dataFolder, folderName)

        if (!file.exists()) {
            file.mkdir()
        }
    }

    /**
     * Creates a file on disk in menus folder from a file located in the jar
     *
     * @param plugin The plugin the file is being created in.
     * @param file The file name.
     */
    @JvmStatic
    fun createMenuFile(plugin: Plugin, file: File) {
        if (!file.exists()) {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdir()
            }

            try {
                plugin.getResource("menus" + File.separator + file.name).use { inputStream ->
                    if (inputStream == null) {
                        file.createNewFile()
                        return
                    }

                    Files.copy(inputStream, Paths.get(file.absolutePath))
                }

            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }
}