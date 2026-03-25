package com.agroconnect.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/* ─── Entities ─── */

@Entity(tableName = "cached_crops")
data class CachedCrop(
    @PrimaryKey val cropId: Int,
    val cropNameEn: String,
)

@Entity(tableName = "cached_mandis")
data class CachedMandi(
    @PrimaryKey val mandiId: Int,
    val mandiName: String,
    val stateCode: String,
    val districtName: String?,
    val latitude: Double,
    val longitude: Double,
)

@Entity(tableName = "cached_prices")
data class CachedPrice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cropId: Int,
    val mandiId: Int,
    val date: String,
    val pricePerQuintal: Double,
)

@Entity(tableName = "cached_advisories")
data class CachedAdvisory(
    @PrimaryKey val advisoryId: Int,
    val advisoryType: String,
    val titleEn: String,
    val contentEn: String,
    val urgency: String,
)

/* ─── DAOs ─── */

@Dao
interface CropDao {
    @Query("SELECT * FROM cached_crops ORDER BY cropNameEn")
    fun getAll(): Flow<List<CachedCrop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crops: List<CachedCrop>)

    @Query("DELETE FROM cached_crops")
    suspend fun deleteAll()
}

@Dao
interface MandiDao {
    @Query("SELECT * FROM cached_mandis ORDER BY mandiName")
    fun getAll(): Flow<List<CachedMandi>>

    @Query("SELECT * FROM cached_mandis WHERE stateCode = :state ORDER BY mandiName")
    fun getByState(state: String): Flow<List<CachedMandi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mandis: List<CachedMandi>)

    @Query("DELETE FROM cached_mandis")
    suspend fun deleteAll()
}

@Dao
interface PriceDao {
    @Query("SELECT * FROM cached_prices WHERE cropId = :cropId ORDER BY date DESC LIMIT :limit")
    fun getLatestPrices(cropId: Int, limit: Int = 30): Flow<List<CachedPrice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prices: List<CachedPrice>)

    @Query("DELETE FROM cached_prices")
    suspend fun deleteAll()
}

@Dao
interface AdvisoryDao {
    @Query("SELECT * FROM cached_advisories ORDER BY urgency")
    fun getAll(): Flow<List<CachedAdvisory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(advisories: List<CachedAdvisory>)

    @Query("DELETE FROM cached_advisories")
    suspend fun deleteAll()
}

/* ─── Database ─── */

@Database(
    entities = [CachedCrop::class, CachedMandi::class, CachedPrice::class, CachedAdvisory::class],
    version = 1,
    exportSchema = false,
)
abstract class AgroDatabase : RoomDatabase() {
    abstract fun cropDao(): CropDao
    abstract fun mandiDao(): MandiDao
    abstract fun priceDao(): PriceDao
    abstract fun advisoryDao(): AdvisoryDao

    companion object {
        @Volatile
        private var INSTANCE: AgroDatabase? = null

        fun getInstance(context: android.content.Context): AgroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgroDatabase::class.java,
                    "agro_connect_cache"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
