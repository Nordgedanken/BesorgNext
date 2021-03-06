package nordgedanken.blog.besorgnext.utils

import android.util.Log
import java.io.InputStream


/**
 * Created by MTRNord on 05.01.2019.
 */

data class Dataset(var productName: String, var mainCategory: String, var imageUrl: String)

class CSVFile(private var inputStream: InputStream) {
    private val TAG = CSVFile::class.java.canonicalName
    fun read(): List<Dataset> {
        val resultList = mutableListOf<Dataset>()
        try {
            val reader = inputStream.bufferedReader()
            reader.forEachLine {
                val row = it.split(",")
                val dataset = Dataset(
                    row[0],
                    row[1],
                    row[2]
                )
                resultList.add(dataset)
            }
            reader.close()
        } catch (e:Exception){
            Log.d(TAG, e.toString())
        }
        return resultList
    }
}