package nordgedanken.blog.besorgnext.nextcloud

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import nordgedanken.blog.besorgnext.nextcloud.json.JsonData
import java.io.*
import java.util.*


/**
 * Created by MTRNord on 12.01.2019.
 */
object Data {
    var filesDir: File? = null
    private val localSaveFile = File(filesDir!!, "data.json")
    val data: MutableLiveData<JsonData> = MutableLiveData()

    private fun saveDataToDisk() {
        FileWriter(localSaveFile).use { writer ->
            val gson = GsonBuilder().create()
            gson.toJson(data, writer)
        }
    }

    private fun getDataFromDisk() {
        val bufferedReader = BufferedReader(FileReader(localSaveFile))
        val gson = Gson()
        data.value = gson.fromJson(bufferedReader, JsonData::class.java)
    }

    private fun uploadToNextcloud() {
        val sardine = WebDav.sardine
        val dirPathBuilder = WebDav.davBaseAddress?.newBuilder()
        dirPathBuilder?.addPathSegment("besorg_next")
        val filePathBuilder = dirPathBuilder?.build()?.newBuilder()
        filePathBuilder?.addPathSegment("data.json")
        if (!sardine.exists(dirPathBuilder?.build()?.toString())) {
            sardine.createDirectory(dirPathBuilder?.build()?.toString())
        }
        sardine.put(filePathBuilder?.build().toString(), localSaveFile, "application/json")
    }

    private fun downloadFromNextcloud(): Boolean{
        val sardine = WebDav.sardine
        val dirPathBuilder = WebDav.davBaseAddress?.newBuilder()
        dirPathBuilder?.addPathSegment("besorg_next")
        val filePathBuilder = dirPathBuilder?.build()?.newBuilder()
        filePathBuilder?.addPathSegment("data.json")
        if (!sardine.exists(dirPathBuilder?.build()?.toString())) {
            return false
        }
        if (!sardine.exists(filePathBuilder?.build()?.toString())) {
            return false
        }
        val input = sardine.get(filePathBuilder?.build().toString())
        val reader = InputStreamReader(input)
        val gson = Gson()
        data.value = gson.fromJson(reader, JsonData::class.java)
        return true
    }

    private fun checkDataOnNextcloud(): Pair<Boolean, Date?> {
        val sardine = WebDav.sardine
        val dirPathBuilder = WebDav.davBaseAddress?.newBuilder()
        dirPathBuilder?.addPathSegment("besorg_next")
        val filePathBuilder = dirPathBuilder?.build()?.newBuilder()
        filePathBuilder?.addPathSegment("data.json")
        if (!sardine.exists(dirPathBuilder?.build()?.toString())) {
            return Pair(false, null)
        }
        if (!sardine.exists(filePathBuilder?.build()?.toString())) {
            return Pair(false, null)
        }
        val infos = sardine.list(filePathBuilder?.build().toString(), 0)
        if (infos.size >= 1) {
            return Pair(true, infos[0].modified)
        }
        return Pair(false, null)
    }

    fun syncNextcloud() {
        // First check if we got data to download
        // Check last modified date on nextcloud
        val nextcloudDate = checkDataOnNextcloud()

        // Check last modified date on disk
        val localDate = Date(localSaveFile.lastModified())

        /**
         * If we get a Date from Nextcloud check if it is later than local.
         * Else use local Data in Both checks
         */
        if (nextcloudDate.first) {
            if(nextcloudDate.second?.after(localDate)!!) {
                downloadFromNextcloud()
                saveDataToDisk()
            } else {
                getDataFromDisk()
                uploadToNextcloud()
            }
        } else {
            getDataFromDisk()
            uploadToNextcloud()
        }
    }
}