package nordgedanken.blog.besorgnext.nextcloud

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.owncloud.android.lib.common.OwnCloudClient
import com.owncloud.android.lib.resources.files.*
import com.owncloud.android.lib.resources.files.model.RemoteFile
import nordgedanken.blog.besorgnext.nextcloud.json.JsonData
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*


/**
 * Created by MTRNord on 12.01.2019.
 */
object Data {
    private val TAG = Data::class.java.canonicalName
    var mClient: OwnCloudClient? = null

    val gson = GsonBuilder().serializeNulls().create()

    var filesDir: File? = null
    private val localSaveFile by lazy{ File(filesDir!!, "data.json") }
    val data: MutableLiveData<JsonData> = MutableLiveData()
    var dataNM: JsonData? = null // Needed as the LiveData might not be there yet. The livedata and this should be synced

    private fun checkIfFileExists() {
        if(!localSaveFile.exists()) {
            Log.d(TAG, "DOES NOT EXIST")
            localSaveFile.createNewFile()
            saveDataToDiskInternal(JsonData())
        }
    }

    private fun saveDataToDiskInternal(data: JsonData) {
        dataNM = data
        FileWriter(localSaveFile).use { writer ->
            gson.toJson(data, writer)
        }
    }

    private fun saveDataToDisk() {
        checkIfFileExists()
        saveDataToDiskInternal(dataNM!!)
    }

    private fun getDataFromDisk() {
        checkIfFileExists()
        val bufferedReader = BufferedReader(FileReader(localSaveFile))
        val jsondata = gson.fromJson(bufferedReader, JsonData::class.java)
        dataNM = jsondata
        data.postValue(dataNM)

    }

    private fun uploadToNextcloud() {
        val dirPath = "besorg_next"
        val filePath = "besorg_next/data.json"
        val existenceOperation = ExistenceCheckRemoteOperation(dirPath, false)
        val existsResult = existenceOperation.execute(mClient)
        if (!existsResult.isSuccess) {
            val folderCreationOperation = CreateFolderRemoteOperation(dirPath, true)
            folderCreationOperation.execute(mClient)
        }
        checkIfFileExists()
        val uploadOperation = UploadFileRemoteOperation(localSaveFile.absolutePath, filePath, "application/json", Date().time.toString())
        uploadOperation.execute(mClient)
    }

    private fun downloadFromNextcloud(): Boolean{
        val dirPath = "besorg_next"
        val filePath = "besorg_next/data.json"
        val dirExistenceOperation = ExistenceCheckRemoteOperation(dirPath, false)
        val dirExistsResult = dirExistenceOperation.execute(mClient)
        if (!dirExistsResult.isSuccess) {
            return false
        }
        val fileExistenceOperation = ExistenceCheckRemoteOperation(filePath, false)
        val fileExistsResult = fileExistenceOperation.execute(mClient)
        if (!fileExistsResult.isSuccess) {
            return false
        }
        val downloadFileOperation = DownloadFileRemoteOperation(filePath, localSaveFile.absolutePath)
        downloadFileOperation.execute(mClient)
        getDataFromDisk()
        return true
    }

    private fun checkDataOnNextcloud(): Pair<Boolean, Date?> {
        val dirPath = "besorg_next"
        val filePath = "besorg_next/data.json"
        val dirExistenceOperation = ExistenceCheckRemoteOperation(dirPath, false)
        val dirExistsResult = dirExistenceOperation.execute(mClient)
        if (!dirExistsResult.isSuccess) {
            return Pair(false, null)
        }
        val fileExistenceOperation = ExistenceCheckRemoteOperation(filePath, false)
        val fileExistsResult = fileExistenceOperation.execute(mClient)
        if (!fileExistsResult.isSuccess) {
            return Pair(false, null)
        }
        val readFileOperation = ReadFileRemoteOperation(filePath)
        val readFileResult = readFileOperation.execute(mClient)
        if (readFileResult.isSuccess) {
            val file = readFileResult.data[0] as RemoteFile
            return Pair(true, Date(file.modifiedTimestamp))
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