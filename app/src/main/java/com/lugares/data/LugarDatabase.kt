package com.lugares.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lugares.model.Lugar
import java.math.MathContext


@Database(entities = [Lugar::class],version=1, exportSchema = false)
abstract class LugarDatabase: RoomDatabase() {

    abstract fun lugarDao() : LugarDao

    companion object{
        @Volatile
        private var INSTANCE: LugarDatabase? =null

        fun getDatabase(context: Context) : LugarDatabase{

            val local = INSTANCE
            if(local != null){

                return local
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LugarDatabase::class.java,
                    "lugar_database"
                ).build()
                INSTANCE = instance
                return instance

            }

        }

    }
}